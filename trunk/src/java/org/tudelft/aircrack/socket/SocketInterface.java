package org.tudelft.aircrack.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.codehaus.preon.Codec;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.Interface;
import org.tudelft.aircrack.InterfaceException;
import org.tudelft.aircrack.ReceiveInfo;
import org.tudelft.aircrack.TransmitInfo;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;

public abstract class SocketInterface extends Interface
{

	protected final String interfaceName, socketAddress;
	
	protected InputStream input;
	protected OutputStream output;

	private final byte replyBuffer[] = new byte[5000];
	
	private final byte messageBuffer[] = new byte[5000];
	private final BufferOutputStream bufferStream = new BufferOutputStream(messageBuffer);
	
	private Codec<SocketMessage> messageCodec;
	
	public SocketInterface(String interfaceName, String socketAddress)
	{
		this.interfaceName = interfaceName;
		this.socketAddress = socketAddress;
		
		this.messageCodec = Codecs.create(SocketMessage.class);
	}
	
	protected abstract void openSocket() throws IOException;
	protected abstract void closeSocket() throws IOException;
	
	protected SocketMessage sendMessage(SocketMessage message)
	{
		try
		{
			
			bufferStream.reset();
			Codecs.encode(message, messageCodec, bufferStream);
			
			// Send the encoded message over the socket
			output.write(messageBuffer, 0, bufferStream.getPosition());
			output.flush();

			// Read reply
			input.read(replyBuffer);
			SocketMessage result = Codecs.decode(messageCodec, replyBuffer);
			
			// Check the result code
			if (result.argument==-1)
				throw new InterfaceException(result.getPayloadAsString());
			else
				return result;
			
		} catch (IOException e)
		{
			throw new InterfaceException(e);
		}
		catch (DecodingException e)
		{
			throw new InterfaceException(e);
		}
	}


	@Override
	public void open()
	{
		try
		{
			openSocket();
			
			// Send an 'open' message
			sendMessage(new SocketMessage(MethodID.Open, interfaceName));
			
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close()
	{
		try
		{
			// Send a 'close' message
			sendMessage(new SocketMessage(MethodID.Close));

			closeSocket();
			
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized Frame receive() throws DecodingException
	{
		SocketMessage result = sendMessage(new SocketMessage(MethodID.Read));

		// If bytesRead is returned as 0, there was a problem reading from the interface.
		// Return null in this case.
		if (result.argument == 0)
			return null;
		else
		{
			// Decode the frame
			return Frame.decode(result.receiveInfo, result.payload);
		}
	}

	@Override
	public int read(byte[] buffer, ReceiveInfo receiveInfo)
	{
		SocketMessage result = sendMessage(new SocketMessage(MethodID.Read));
		
		// Copy payload
		System.arraycopy(result.payload, 0, buffer, 0, result.argument);
		
		return result.argument;
	}

	@Override
	public int write(byte[] buffer, TransmitInfo transmitInfo)
	{
		return 0;
	}

	@Override
	public void setChannel(int channel)
	{
		sendMessage(new SocketMessage(MethodID.SetChannel, channel));
	}

	@Override
	public int getChannel()
	{
		return sendMessage(new SocketMessage(MethodID.GetChannel)).argument; 
	}

	@Override
	public void setFrequency(int freq)
	{
		sendMessage(new SocketMessage(MethodID.SetFrequency, freq));
	}

	@Override
	public int getFrequency()
	{
		return sendMessage(new SocketMessage(MethodID.GetFrequency)).argument; 
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
