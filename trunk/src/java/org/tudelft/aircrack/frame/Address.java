package org.tudelft.aircrack.frame;

import org.codehaus.preon.annotation.BoundList;

public class Address implements Comparable<Address>
{

	public final static Address Broadcast = new Address(new byte[] { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff }); 
	public final static Address Zero = new Address(new byte[] { 0, 0, 0, 0, 0, 0 }); 
	
	@BoundList(size="6")
	public byte[] address;
	
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
	public int compareTo(Address o)
	{
		for (int i=5; i>=0; i--)
		{
			if (address[i]<o.address[i]) return -1;
			if (address[i]>o.address[i]) return 1;
		}
		return 0;
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
