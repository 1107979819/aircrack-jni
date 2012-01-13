package org.tudelft.aircrack.socket;

import java.io.IOException;

import org.tudelft.aircrack.InterfaceException;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;

public class AndroidSocketInterface extends SocketInterface
{
	
	public AndroidSocketInterface(String interfaceName, String socketAddress)
	{
		super(interfaceName, socketAddress);
	}

	private LocalSocket socket;

	@Override
	protected void openSocket()
	{
		try
		{
			socket = new LocalSocket();
			socket.connect(new LocalSocketAddress(this.socketAddress));
			
			input = socket.getInputStream();
			output = socket.getOutputStream();
			
		} catch (IOException e)
		{
			throw new InterfaceException(e);
		}
	}

	@Override
	protected void closeSocket()
	{
		try
		{
			socket.close();
		} catch (IOException e)
		{
			throw new InterfaceException(e);
		}
	}

}
