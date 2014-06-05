package org.tudelft.parse80211.types;

import java.io.UnsupportedEncodingException;

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
	
	public int getOffset()
	{
		return offset;
	}
	
	public void set(BufferBacked value)
	{
		
		write(value.getBuffer().data);
	}
	
	public void write(byte[] data)
	{
		// TODO check array bounds
		System.arraycopy(data, 0, this.buffer.data, offset, data.length);
	}

	public void write(int offset, byte[] src)
	{
		// TODO check array bounds
		System.arraycopy(src, 0, this.buffer.data, this.offset + offset, src.length);
	}

	public void write(int offset, int data)
	{
		// TODO check array bounds
		buffer.data[this.offset + offset] = (byte)(data & 0xff);
	}
	
	public void writeNullTerminatedString(String str)
	{
		// TODO check array bounds
		// TODO charset
		byte[] stringBytes;
		
		try
		{
			stringBytes = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			// I'll go ahead and assume you have UTF-8
			throw new RuntimeException(e);
		}
		
		write(stringBytes);
		write(stringBytes.length, 0);
	}

}
