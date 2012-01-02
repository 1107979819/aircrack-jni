package org.tudelft.aircrack;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;

public class Interface
{

	private final static byte[] buffer = new byte[4096];
	
	static
	{
		System.loadLibrary("aircrack-ng-jni");
	}
	
	// Interface name, i.e. 'mon0'.
	private final String name;
	
	// Pointer to the wif struct that describes the interface once opened.
	private long wif = 0;
	
	public Interface(String name)
	{
		this.name = name;
	}

	native static private long _open(String interfaceName);
	native static private void _close(long wif);
	native static private int _read(long wif, byte[] buffer, ReceiveInfo info);
	native static private int _write(long wif, byte[] buffer, TransmitInfo info);
	
	native static private int _setChannel(long wif, int channel);
	native static private int _getChannel(long wif);

	native static private int _setFrequency(long wif, int freq);
	native static private int _getFrequency(long wif);

	native static private int _setMac(long wif, byte[] mac);
	native static private int _getMac(long wif, byte[] mac);

	native static private int _setRate(long wif, int rate);
	native static private int _getRate(long wif);
	
	native static private int _getMonitor(long wif);
	
	native static private int _setMtu(long wif, int mtu);
	native static private int _getMtu(long wif);
	
	public void open()
	{
		this.wif = _open(name);
		
		if (this.wif==0)
			throw new WifiException("Unable to open interface: " + name);
		
	}
	
	public void close()
	{
		if (this.wif==0)
			throw new WifiException("Interface is not open.");
		else
			_close(wif);
	}
	
	public synchronized Frame receive() throws DecodingException
	{
		ReceiveInfo receiveInfo = new ReceiveInfo();
		int bytesRead = read(buffer, receiveInfo);
		
		if (bytesRead==0)
			return null;		
		else
		{
			byte[] buf = new byte[bytesRead];
			System.arraycopy(buffer, 0, buf, 0, bytesRead);
			return Frame.decode(receiveInfo, buf);
		}
	}
	
	public int read(byte[] buffer)
	{
		ReceiveInfo receiveInfo = new ReceiveInfo();
		
		return _read(wif, buffer, receiveInfo);
	}

	public int read(byte[] buffer, ReceiveInfo receiveInfo)
	{
		if (receiveInfo==null)
			throw new NullPointerException("Receive info object may not be null");
		
		return _read(wif, buffer, receiveInfo);
	}
	
	public synchronized int write(byte[] buffer)
	{
		TransmitInfo transmitInfo = new TransmitInfo();

		return _write(wif, buffer, transmitInfo);
	}

	public synchronized int write(byte[] buffer, TransmitInfo transmitInfo) 
	{
		if (transmitInfo == null)
			throw new NullPointerException("Transmit info object may not be null");

		return _write(wif, buffer, transmitInfo);
	}

	public void setChannel(int channel)
	{
		_setChannel(wif, channel);
	}
	
	public int getChannel()
	{
		return _getChannel(wif);
	}

	public void setFrequency(int freq)
	{
		// TODO check return value? Fails on some cards?
		_setFrequency(wif, freq);
	}
	
	public int getFrequency()
	{
		return _getFrequency(wif);
	}

	public void setMac(Address address)
	{
		// TODO check return value? Fails on some cards?
		_setMac(wif, address.getAddress());
	}
	
	public Address getMac()
	{
		byte[] ret = new byte[6];
		_getMac(wif, ret);
		return new Address(ret);
	}

	public void setRate(int rate)
	{
		// TODO check return value? Fails on some cards?
		_setRate(rate, rate);
	}
	
	public int getRate()
	{
		return _getRate(wif);
	}
	
	public int getMonitor()
	{
		return _getMonitor(wif);
	}
	
	public void setMtu(int mtu)
	{
		_setMtu(mtu, mtu);
	}
	
	public int getMtu()
	{
		return _getMtu(wif);
	}

	public static void main(String args[])
	{
		Interface iface = new Interface("mon0");
		iface.open();

		// capture packets
		for (int i=0; i<100; i++)
		{
			byte[] buffer = new byte[4096];
			
			ReceiveInfo receiveInfo = new ReceiveInfo();
			int bytesRead = iface.read(buffer, receiveInfo);
			
			try
			{
				Frame frame = Frame.decode(receiveInfo, buffer);				
				System.out.println(frame);
				
			} catch (DecodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// inject packets
//		for (int i=0; i<100; i++) 
//		{
//			String str = "The spagetti monster ate " + i + " plates of pasta";
//		
//			TransmitInfo transmitInfo = new TransmitInfo();
//			
//			System.out.println(iface.write(str.getBytes(), transmitInfo));
//		}
		
		iface.close();
	}	
}
