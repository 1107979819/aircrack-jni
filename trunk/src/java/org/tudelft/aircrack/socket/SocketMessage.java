package org.tudelft.aircrack.socket;

import org.codehaus.preon.annotation.BoundNumber;
import org.tudelft.aircrack.frame.data.field.FrameBody;

public class SocketMessage
{
	
	@BoundNumber(size="8")
	public MethodID methodId; 

	@BoundNumber(size="32")
	public int argument = 0; 

	@BoundNumber(size="16")
	public int payloadLength = 0; 
	
	@FrameBody
	public byte[] payload;
	
	public SocketMessage()
	{
	}
	
	public SocketMessage(MethodID methodId)
	{
		this.methodId = methodId;
	}

	public SocketMessage(MethodID methodId, byte[] payload)
	{
		this(methodId);
		this.payloadLength = payload.length;
		this.payload = payload;
	}

	public SocketMessage(MethodID methodId, int argument)
	{
		this(methodId);
		this.argument = argument;
	}

}
