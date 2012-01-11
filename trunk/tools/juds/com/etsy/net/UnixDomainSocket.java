package com.etsy.net;

// UnixDomainSocket.java
// Inspired by J-BUDS version 1.0
// See COPYRIGHT file for license details
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;


/**
 * 
 * This class provides a means of using Unix domain socket client/server
 * connections in Java
 * 
 * @author Klaus Trainer
 */
public abstract class UnixDomainSocket {

    private static File jarFile;
    static {
        // Load the Unix domain socket C library
        getJarPath();
        try {
            loadNativeLib();
        } catch (IOException ioe) {
            throwLink(ioe);
        }
    }

    private static void getJarPath() {
        try {
            jarFile = new File(JUDS.jarURL.toURI());
        } catch(URISyntaxException e) {
            throwLink(e);
        }
    }

    private static void loadNativeLib() throws IOException {
        File lib = getNativeLibTarget();
        if(!lib.exists() || jarNewer(lib)) {
            try {
                extractNativeLib(lib);
                if(!lib.exists()) {
                    throwLink("The native library was not extracted");
                }
            } catch(IOException e) {
                throwLink(e);
            } catch(URISyntaxException e) {
                throwLink(e);
            }
        }

        String path = "";
        try {
            path = lib.getCanonicalPath();
        } catch(IOException e) {
            throwLink(e);
        }
        System.load(path); 
    }

    private static Boolean jarNewer(File lib) {
        return lib.lastModified() < jarFile.lastModified();
    }
    
    private static void throwLink(Throwable e) {
        throwLink(e.toString());
    }
    private static void throwLink(String s) {
        throw new UnsatisfiedLinkError(s);
    }

    private static File getNativeLibTarget() throws IOException {
        String p = platform();
        String ext = "darwin".equals(p) ? "dylib" : "so";

        File tmpdir = File.createTempFile("juds-temp", Long.toString(System.nanoTime()));

        if(!(tmpdir.delete())) {
            throw new IOException("Could not delete temp file: " + tmpdir.getAbsolutePath());
        }

        if(!(tmpdir.mkdir())) {
            throw new IOException("Could not create temp directory: " + tmpdir.getAbsolutePath());
        }

        tmpdir.deleteOnExit();

        String path = String.format(tmpdir.getAbsolutePath() + "/libunixdomainsocket-%s-%s.%s", p, arch(), ext);
        File lib = new File(path);
        lib.deleteOnExit();
        return lib;
    }

    private static void extractNativeLib(File target) 
        throws IOException, URISyntaxException {

        JarFile jar = new JarFile(jarFile);
        ZipEntry z = jar.getEntry(target.getName());
        if(z == null) {
            throwLink("Could not find library: "+target.getName());
        }

        InputStream in = jar.getInputStream(z);
        try {
            OutputStream out = new BufferedOutputStream(
                new FileOutputStream(target));
            try {
                byte[] buf = new byte[2048];
                for(;;) {
                    int n = in.read(buf);
                    if(n < 0) {
                        break;
                    }
                    out.write(buf, 0, n);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    private static String arch() {
        String a = System.getProperty("os.arch");
        if("amd64".equals(a) || "x86_64".equals(a)) {
            return "x86_64";
        }
        return a;
    }

    private static String platform() {
        String p = System.getProperty("os.name").toLowerCase();
        if("mac os x".equals(p)) {
            return "darwin";
        }
        return p;
    }

    protected UnixDomainSocketInputStream in;

    protected UnixDomainSocketOutputStream out;

    protected int nativeSocketFileHandle;

    // Name of the socket file
    protected String socketFile;

    protected int socketType;

    private int timeout;

    // Native methods implemented in the Unix domain socket C library
        protected native static int nativeCreate(String socketFile, int socketType);
        
        protected native static int nativeListen(String socketFile, int socketType, int backlog);

        protected native static int nativeAccept(int nativeSocketFileHandle, int socketType);

    protected native static int nativeOpen(String socketFile, int socketType);

    protected native static int nativeRead(int nativeSocketFileHandle,
            byte[] b, int off, int len);

    protected native static int nativeWrite(int nativeSocketFileHandle,
            byte[] b, int off, int len);

    protected native static int nativeClose(int nativeSocketFileHandle);

    protected native static int nativeCloseInput(int nativeSocketFileHandle);

    protected native static int nativeCloseOutput(int nativeSocketFileHandle);

    protected native static int nativeUnlink(String socketFile);

    protected UnixDomainSocket()
    {
    // default constructor
    }

    protected UnixDomainSocket(int pSocketFileHandle, int pSocketType)
    throws IOException
    {
    nativeSocketFileHandle = pSocketFileHandle;    
    socketType = pSocketType;
    socketFile = null;

        // Initialize the socket input and output streams
        in = new UnixDomainSocketInputStream();
        if (socketType == JUDS.SOCK_STREAM)
            out = new UnixDomainSocketOutputStream();

    }
    /**
     * Returns an input stream for this socket.
     * 
     * @return An input stream for reading bytes from this socket
     */
    public InputStream getInputStream() {
        return (InputStream) in;
    }

    /**
     * Returns an output stream for this socket.
     * 
     * @return An output stream for writing bytes to this socket
     */
    public OutputStream getOutputStream() {
        return (OutputStream) out;
    }

    /**
     * Sets the read timeout for the socket. If a read call blocks for the
     * specified amount of time it will be cancelled, and a
     * java.io.InterruptedIOException will be thrown. A <code>timeout</code>
     * of zero is interpreted as an infinite timeout.
     * 
     * @param timeout
     *            The specified timeout, in milliseconds.
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    /**
     * Closes the socket
     */
    public void close() {
        nativeClose(nativeSocketFileHandle);
    }

    /**
     * Unlink socket file
     */
    public void unlink() {
        if (socketFile != null)
        {
            nativeUnlink(socketFile);
        }
    }

    protected class UnixDomainSocketInputStream extends InputStream {
        @Override
        public int read() throws IOException {
            byte[] b = new byte[1];
            int count;
            // If a timeout is set, then use a read thread
            if (timeout > 0) {
                // Create a thread to read the byte
                UnixDomainSocketReadThread thread = new UnixDomainSocketReadThread(
                        b, 0, 1);
                thread.setDaemon(true);
                thread.start();
                try {
                    // Wait up until the specified timeout for the thread to
                    // complete
                    thread.join(timeout);
                } catch (InterruptedException e) {
                }
                // If the thread is still alive the read() call has
                // blocked longer than the specified timeout
                if (thread.isAlive()) {
                    throw new InterruptedIOException(
                            "Unix domain socket read() call timed out");
                } else {
                    count = thread.getData();
                }
            } else {
                count = nativeRead(nativeSocketFileHandle, b, 0, 1);
                if (count == -1)
                    throw new IOException();
            }
            return count > 0 ? (int) b[0] & 0xff : -1;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (b == null) {
                throw new NullPointerException();
            } else if (off < 0 || len < 0 || len > b.length - off) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return 0;
            }

            int count;
            // If a timeout is set, then use a read thread
            if (timeout > 0) {
                // Create a thread to read the byte
                UnixDomainSocketReadThread thread = new UnixDomainSocketReadThread(
                        b, off, len);
                thread.setDaemon(true);
                thread.start();
                try {
                    // Wait up until the specified timeout for the thread to
                    // complete
                    thread.join(timeout);
                } catch (InterruptedException e) {
                }
                // If the thread is still alive the read() call has
                // blocked longer than the specified timeout
                if (thread.isAlive()) {
                    throw new InterruptedIOException(
                            "Unix domain socket read() call timed out");
                } else {
                    count = thread.getData();
                }
            } else {
                count = nativeRead(nativeSocketFileHandle, b, off, len);
                if (count == -1)
                    throw new IOException();
            }
            return count;
        }

        // Closes the socket input stream
        public void close() throws IOException {
            nativeCloseInput(nativeSocketFileHandle);
        }
    }

    protected class UnixDomainSocketOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            byte[] data = new byte[1];
            data[0] = (byte) b;
            if (nativeWrite(nativeSocketFileHandle, data, 0, 1) != 1)
                throw new IOException("Unable to write to Unix domain socket");
        }

        @Override
        public void write(byte b[], int off, int len) throws IOException {
            if (b == null) {
                throw new NullPointerException();
            } else if ((off < 0) || (off > b.length) || (len < 0)
                    || ((off + len) > b.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return;
            }
            if (nativeWrite(nativeSocketFileHandle, b, off, len) != len)
                throw new IOException("Unable to write to Unix domain socket");
        }

        // Closes the socket output stream
        public void close() throws IOException {
            nativeCloseOutput(nativeSocketFileHandle);
        }
    }

    protected class UnixDomainSocketReadThread extends Thread {
        private int count, off, len;

        private byte[] b;

        public UnixDomainSocketReadThread(byte[] b, int off, int len) {
            this.b = b;
            this.off = off;
            this.len = len;
        }

        public void run() {
            count = nativeRead(nativeSocketFileHandle, b, off, len);
        }

        public int getData() {
            return count;
        }
    }
}
