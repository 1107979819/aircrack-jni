package org.tudelft.parse80211.frames.control;

import org.tudelft.parse80211.frames.Frame;

public class Control extends Frame
{


	public Control(byte[] data)
	{
		super(data);

	}

	public int getDuration()
	{
		return (data[offset+2] & 0xff) + ((data[offset+3] & 0xff)<<8);
	}

	public void setDuration(int value)
	{
		data[offset+2] = (byte)(value & 0xff);
		data[offset+3] = (byte)((value>>8) & 0xff);
	}

}
