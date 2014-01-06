package org.tudelft.parse80211.frames.management;

import org.tudelft.parse80211.types.TimeStamp;
import org.tudelft.parse80211.frames.management.Management;

public class BeaconOrProbeResponse extends Management
{

	private TimeStamp timestamp;

	public BeaconOrProbeResponse(byte[] data)
	{
		super(data);

		timestamp = new TimeStamp(data, 24);
	}

	public TimeStamp getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(TimeStamp value)
	{
		
	}
	public int getBeaconInterval()
	{
		return (data[offset+32] & 0xff) + ((data[offset+33] & 0xff)<<8);
	}

	public void setBeaconInterval(int value)
	{
		data[offset+32] = (byte)(value & 0xff);
		data[offset+33] = (byte)((value>>8) & 0xff);
	}

}
