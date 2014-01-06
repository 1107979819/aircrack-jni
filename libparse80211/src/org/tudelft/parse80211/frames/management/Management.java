package org.tudelft.parse80211.frames.management;

import org.tudelft.parse80211.types.Address;
import org.tudelft.parse80211.frames.Frame;

public class Management extends Frame
{

	private Address DA;
	private Address SA;
	private Address BSSID;

	public Management(byte[] data)
	{
		super(data);

		DA = new Address(data, 4);
		SA = new Address(data, 10);
		BSSID = new Address(data, 16);
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
	public Address getDA()
	{
		return DA;
	}

	public void setDA(Address value)
	{
		
	}
	public Address getSA()
	{
		return SA;
	}

	public void setSA(Address value)
	{
		
	}
	public Address getBSSID()
	{
		return BSSID;
	}

	public void setBSSID(Address value)
	{
		
	}
	public int getSequenceControl()
	{
		return (data[offset+22] & 0xff) + ((data[offset+23] & 0xff)<<8);
	}

	public void setSequenceControl(int value)
	{
		data[offset+22] = (byte)(value & 0xff);
		data[offset+23] = (byte)((value>>8) & 0xff);
	}

}
