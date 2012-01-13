package org.tudelft.aircrack.socket;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.If;
import org.codehaus.preon.annotation.Order;
import org.codehaus.preon.el.ImportStatic;
import org.tudelft.aircrack.ReceiveInfo;

@ImportStatic(MethodID.class)
public class SocketMessage
{
	
	@BoundNumber(size="8")
	@Order(1) public MethodID methodId;

	@BoundNumber(size="32")
	@Order(2) public int argument = 0; 

	@BoundNumber(size="16")
	@Order(3) public int payloadLength = 0; 
	
	@Bound
	@If("methodId == MethodID.Read && payloadLength>0")
	@Order(4) public ReceiveInfo receiveInfo;
	
	@BoundList(size="payloadLength")
	@Order(5) public byte[] payload;
	
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

	public SocketMessage(MethodID methodId, String payload)
	{
		this(methodId);
		
		this.payloadLength = payload.length();
		this.payload = payload.getBytes();
	}

	public SocketMessage(MethodID methodId, int argument)
	{
		this(methodId);
		this.argument = argument;
	}
	
	public String getPayloadAsString()
	{
		return new String(payload);
	}

}
