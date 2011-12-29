package org.tudelft.aircrack.trace;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.buffer.ByteOrder;
import org.tudelft.aircrack.ReceiveInfo;

public class TraceElement
{
	
	@BoundList(size="5")
	private byte[] magic;
	
	@BoundNumber(byteOrder=ByteOrder.LittleEndian, size="32")
	private long timestamp;
	
	@Bound
	ReceiveInfo receiveInfo;

	@BoundNumber(size="32")
    int channel;

	@BoundNumber(size="32")
    int payloadLength;
	
	public byte[] getMagic()
	{
		return magic;
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	public int getChannel()
	{
		return channel;
	}
	
	public int getPayloadLength()
	{
		return payloadLength;
	}
	
	public ReceiveInfo getReceiveInfo()
	{
		return receiveInfo;
	}

}
