package org.tudelft.parse80211.frames.control;

import org.tudelft.parse80211.types.Address;
import org.tudelft.parse80211.frames.control.Control;

public class RtsCts extends Control
{

	private Address RA;

	public RtsCts(byte[] data)
	{
		super(data);

		RA = new Address(data, 4);
	}

	public Address getRA()
	{
		return RA;
	}

	public void setRA(Address value)
	{
		
	}

}
