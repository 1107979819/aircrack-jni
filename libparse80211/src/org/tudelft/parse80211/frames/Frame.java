package org.tudelft.parse80211.frames;

import org.tudelft.parse80211.types.ByteBuffer;

public class Frame extends ByteBuffer
{

	public Frame(byte[] data)
	{
		super(data);
	}

	public int getProtocolVersion()
	{
		return (data[0] >> 0) & 0x03;
	}

	public void setProtocolVersion(int value)
	{
		data[0] = (byte)value;
	}
	public int getType()
	{
		return (data[0] >> 2) & 0x03;
	}

	public void setType(int value)
	{
		data[0] = (byte)value;
	}
	public int getSubType()
	{
		return (data[0] >> 4) & 0x0f;
	}

	public void setSubType(int value)
	{
		data[0] = (byte)value;
	}
	public int getFrameType()
	{
		return (data[0] >> 2) & 0x3f;
	}

	public void setFrameType(int value)
	{
		data[0] = (byte)value;
	}
	public boolean getToDs()
	{
		return (data[1] & 0x01) != 0;
	}

	public void setToDs(boolean value)
	{
		if (value)
			data[1] |= 0x01;
		else
			data[1] &= ~0x01;
	}
	public boolean getFromDs()
	{
		return (data[1] & 0x02) != 0;
	}

	public void setFromDs(boolean value)
	{
		if (value)
			data[1] |= 0x02;
		else
			data[1] &= ~0x02;
	}
	public boolean getMoreFrag()
	{
		return (data[1] & 0x04) != 0;
	}

	public void setMoreFrag(boolean value)
	{
		if (value)
			data[1] |= 0x04;
		else
			data[1] &= ~0x04;
	}
	public boolean getRetry()
	{
		return (data[1] & 0x08) != 0;
	}

	public void setRetry(boolean value)
	{
		if (value)
			data[1] |= 0x08;
		else
			data[1] &= ~0x08;
	}
	public boolean getPwrMgt()
	{
		return (data[1] & 0x10) != 0;
	}

	public void setPwrMgt(boolean value)
	{
		if (value)
			data[1] |= 0x10;
		else
			data[1] &= ~0x10;
	}
	public boolean getMoreData()
	{
		return (data[1] & 0x20) != 0;
	}

	public void setMoreData(boolean value)
	{
		if (value)
			data[1] |= 0x20;
		else
			data[1] &= ~0x20;
	}
	public boolean getProtectedFrame()
	{
		return (data[1] & 0x40) != 0;
	}

	public void setProtectedFrame(boolean value)
	{
		if (value)
			data[1] |= 0x40;
		else
			data[1] &= ~0x40;
	}
	public boolean getOrder()
	{
		return (data[1] & 0x80) != 0;
	}

	public void setOrder(boolean value)
	{
		if (value)
			data[1] |= 0x80;
		else
			data[1] &= ~0x80;
	}

}
