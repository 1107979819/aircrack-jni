package org.tudelft.aircrack.frame;

import nl.flotsam.preon.annotation.BoundList;

public class Address
{
	
	@BoundList(size="6")
	private byte[] address;
	
	public Address()
	{
		this.address = new byte[6];
	}
	
	public Address(byte[] address)
	{
		if (address.length!=6) throw new IllegalArgumentException("MAC address has to be six bytes long");
		
		this.address = new byte[address.length];
		for (int i=0; i<address.length; i++)
			this.address[i] = address[i];
	}
	
	public byte[] getAddress()
	{
		return address;
	}
	
	@Override
	public String toString()
	{
		return String.format("%02x:%02x:%02x:%02x:%02x:%02x",
				address[0] & 0xff,
				address[1] & 0xff,
				address[2] & 0xff, 
				address[3] & 0xff,
				address[4] & 0xff,
				address[5] & 0xff 
				);
	}

}
