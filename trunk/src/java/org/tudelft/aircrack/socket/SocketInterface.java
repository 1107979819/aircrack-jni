package org.tudelft.aircrack.socket;

import java.io.IOException;

import org.codehaus.preon.Codec;
import org.codehaus.preon.Codecs;
import org.tudelft.aircrack.Interface;
import org.tudelft.aircrack.ReceiveInfo;
import org.tudelft.aircrack.TransmitInfo;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.data.field.FrameBodyCodecFactory;

import com.etsy.net.JUDS;
import com.etsy.net.UnixDomainSocketClient;

public class SocketInterface extends Interface
{

	private String socketAddress;
	private UnixDomainSocketClient socket;
	
	private final static Codec<SocketMessage> messageCodec = Codecs.create(SocketMessage.class, new FrameBodyCodecFactory());
	
	public SocketInterface(String socketAddress)
	{
		this.socketAddress = socketAddress;
		
	}
	
	public static void main(String[] args)
	{
		
		String socketAddress = "@echo";
		
		SocketInterface iface = new SocketInterface(socketAddress);
		iface.open();
			
		iface.sendMessage(new SocketMessage(MethodID.SetChannel, 11));
		
		iface.close();
	}
	
	protected void sendMessage(SocketMessage message)
	{
		try
		{
			
			byte[] raw = Codecs.encode(message, messageCodec);
			
			// Send the encoded message over the socket
			socket.getOutputStream().write(raw);
			
			
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}


	@Override
	public void open()
	{
		try
		{
			socket = new UnixDomainSocketClient(socketAddress, JUDS.SOCK_STREAM);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close()
	{
		socket.close();
	}

	@Override
	public int read(byte[] buffer, ReceiveInfo receiveInfo)
	{
		SocketMessage message = new SocketMessage(MethodID.Read, buffer);
		
		return 0;
	}

	@Override
	public int write(byte[] buffer, TransmitInfo transmitInfo)
	{
		return 0;
	}

	@Override
	public void setChannel(int channel)
	{
	}

	@Override
	public int getChannel()
	{
		return 0;
	}

	@Override
	public void setFrequency(int freq)
	{
	}

	@Override
	public int getFrequency()
	{
		return 0;
	}

	@Override
	public void setMac(Address address)
	{
	}

	@Override
	public Address getMac()
	{
		return null;
	}

	@Override
	public void setRate(int rate)
	{
	}

	@Override
	public int getRate()
	{
		return 0;
	}

	@Override
	public int getMonitor()
	{
		return 0;
	}

	@Override
	public void setMtu(int mtu)
	{
	}

	@Override
	public int getMtu()
	{
		return 0;
	}

}
