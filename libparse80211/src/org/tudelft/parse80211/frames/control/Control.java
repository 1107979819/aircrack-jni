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
		return (data[2] & 0xff) + ((data[3] & 0xff)<<8);
	}

	public void setDuration(int value)
	{
		data[2] = (byte)(value & 0xff);
		data[3] = (byte)((value>>8) & 0xff);
	}

}
