package org.tudelft.parse80211.types;

public class BufferBacked
{
	
	protected ByteBuffer buffer;
	protected int offset;
	
	public BufferBacked(ByteBuffer buffer, int offset)
	{
		this.buffer = buffer;
		this.offset = offset;
	}

	public BufferBacked(ByteBuffer data)
	{
		this(data, 0);
	}
	
	public ByteBuffer getBuffer()
	{
		return buffer;
	}

}
