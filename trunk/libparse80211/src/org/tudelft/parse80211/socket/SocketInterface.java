package org.tudelft.parse80211.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.tudelft.parse80211.frames.Decoder;
import org.tudelft.parse80211.frames.Encoder;
import org.tudelft.parse80211.frames.Frame;
import org.tudelft.parse80211.socket.types.ReceiveInfo;
import org.tudelft.parse80211.socket.types.SocketMessage;
import org.tudelft.parse80211.types.Address;
import org.tudelft.parse80211.types.ByteBuffer;

public abstract class SocketInterface extends Interface
{

	private static int maxPacketLength = 8192;
	private static int socketMessageLength = 7;
	private static int receiveInfoLength = 32;
	
	private static int bufferLength = socketMessageLength + receiveInfoLength + maxPacketLength;
	
	private SocketMessage txMessage = new SocketMessage(new ByteBuffer(bufferLength));
	private SocketMessage rxMessage = new SocketMessage(new ByteBuffer(bufferLength));
	
	private Decoder decoder = new Decoder(rxMessage.getBuffer(), socketMessageLength + receiveInfoLength);
	private Encoder encoder = new Encoder(txMessage.getBuffer(), socketMessageLength);
	
	protected final String interfaceName, socketAddress;
	
	protected InputStream input;
	protected OutputStream output;

	public SocketInterface(String interfaceName, String socketAddress)
	{
		this.interfaceName = interfaceName;
		this.socketAddress = socketAddress;
	}
	
	protected abstract void openSocket() throws IOException;
	protected abstract void closeSocket() throws IOException;
	
	public ReceiveInfo getReceiveInfo()
	{
		return rxMessage.getReceiveInfo();
	}
	
	public Encoder getEncoder()
	{
		return encoder;
	}
	
	protected void transmitMessage(MethodID methodId, int argument)
	{
		txMessage.setMethodId(methodId.ordinal());
		txMessage.setArgument(argument);

		// Send raw message to the deamon
//		System.out.println(txMessage.getBuffer().toHex());
		
		try
		{
			
			// Send the encoded message over the socket
			output.write(txMessage.getBuffer().data, 0, txMessage.getPayloadLength() + socketMessageLength);
			output.flush();
			
			// Read reply
			int rxBytes = input.read(rxMessage.getBuffer().data);
			
//			System.out.println("Bytes received: " + rxBytes);
//			System.out.println("Payload: " + rxMessage.getPayloadLength());
//			System.out.println(rxMessage.getBuffer().toHex(rxBytes));
//			System.out.println(rxMessage.getMethodId());

			// Check the result code
			if (rxMessage.getArgument()==-1)
				throw new InterfaceException(getPayloadAsString(rxMessage));
			
		} catch (IOException e)
		{
			// TODO handle this properly
			throw new RuntimeException(e);
		}
		
	}
	
	protected void sendMessage(MethodID methodId, int argument)
	{
		txMessage.setPayloadLength(0);
		transmitMessage(methodId, argument);
	}

	protected void sendMessage(MethodID methodId, int argument, String payload)
	{
		byte[] stringBytes = payload.getBytes();
		
		txMessage.getPayload().write(stringBytes);
		txMessage.setPayloadLength(stringBytes.length);
		
		transmitMessage(methodId, argument);
	}
	
	protected void sendMessage(MethodID methodId, String payload)
	{
		sendMessage(methodId, 0, payload);
	}

	protected void sendMessage(MethodID methodId)
	{
		transmitMessage(methodId, 0);
	}
	
	protected String getPayloadAsString(SocketMessage msg)
	{
		return new String(msg.getPayload().getBuffer().data, socketMessageLength, msg.getPayloadLength());
	}

	@Override
	public void open()
	{
		try
		{
			openSocket();
			
			// Send an 'open' message
			sendMessage(MethodID.Open, interfaceName);
			
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
			sendMessage(MethodID.Close);

			closeSocket();
			
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized Frame receive()
	{
		// Request a frame
		sendMessage(MethodID.Read);

		// If bytesRead is returned as 0, there is no frame to be read
		if (rxMessage.getArgument() == 0)
			return null;
		else
		{
			// Decode the frame
			return decoder.decode(rxMessage.getPayloadLength());
		}
	}

	/*
	@Override
	public int read(byte[] buffer, ReceiveInfo receiveInfo)
	{
		SocketMessage result = sendMessage(new SocketMessage(MethodID.Read));
		
		// Copy payload
		System.arraycopy(result.payload, 0, buffer, 0, result.argument);
		
		return result.argument;
	}
	*/

//	@Override
//	public int write(byte[] buffer, int length, TransmitInfo transmitInfo)
//	{
//		sendMessage(new SocketMessage(MethodID.Write, buffer));
//		return 0;
//	}
	
	public void send(Frame frame)
	{
		txMessage.setPayloadLength(txMessage.getBuffer().size - socketMessageLength);
		transmitMessage(MethodID.Write, 0);

	}
	
	@Override
	public void setChannel(int channel)
	{
		sendMessage(MethodID.SetChannel, channel);
	}

	@Override
	public int getChannel()
	{
		sendMessage(MethodID.GetChannel);
		
		return rxMessage.getArgument();
	}

	@Override
	public void setFrequency(int freq)
	{
		sendMessage(MethodID.SetFrequency, freq);
	}

	@Override
	public int getFrequency()
	{
		sendMessage(MethodID.GetFrequency);
		
		return rxMessage.getArgument();
	}

	@Override
	public void setMac(Address address)
	{
	}

	@Override
	public Address getMac()
	{
		sendMessage(MethodID.GetMac);
		
		return new Address(rxMessage.getPayload());
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
