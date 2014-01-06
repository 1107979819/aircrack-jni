package org.tudelft.parse80211.types;

public class ByteBuffer
{
	
	protected byte[] data;
	protected int offset;
	
	public ByteBuffer(byte[] data, int offset)
	{
		this.data = data;
		this.offset = offset;
	}

	public ByteBuffer(byte[] data)
	{
		this(data, 0);
	}
	
	public byte[] getData()
	{
		return data;
	}

}
