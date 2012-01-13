package org.tudelft.aircrack.socket;

import java.io.IOException;

import com.etsy.net.JUDS;
import com.etsy.net.UnixDomainSocketClient;

public class LinuxSocketInterface extends SocketInterface
{

	private UnixDomainSocketClient socket;
	
	public LinuxSocketInterface(String interfaceName, String socketAddress)
	{
		super(interfaceName, socketAddress);
	}

	@Override
	protected void openSocket() throws IOException
	{
		// Open a socket to the native aircrack interface.			
		socket = new UnixDomainSocketClient(socketAddress, JUDS.SOCK_STREAM);
		input = socket.getInputStream();
		output = socket.getOutputStream();
	}

	@Override
	protected void closeSocket() throws IOException
	{
		input = null;
		output = null;
		socket.close();
	}

}
