package org.tudelft.parse80211.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Address extends ByteBuffer implements Comparable<Address>
{

	public final static Address Broadcast = new Address(new byte[] { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff }); 
	public final static Address Zero = new Address(new byte[] { 0, 0, 0, 0, 0, 0 });
	
	private final static Pattern addressPattern = Pattern.compile("([0-9a-fA-F]{2}):([0-9a-fA-F]{2}):([0-9a-fA-F]{2}):([0-9a-fA-F]{2}):([0-9a-fA-F]{2}):([0-9a-fA-F]{2})");
	
	public Address(byte[] address)
	{
		super(address);
	}
	
	public Address(byte[] address, int offset)
	{
		super(address, offset);
	}
	
	/*
	public Address(byte[] address)
	{
		if (address.length!=6) throw new IllegalArgumentException("MAC address has to be six bytes long");
		
		this.address = new byte[address.length];
		for (int i=0; i<address.length; i++)
			this.address[i] = address[i];
	}
	*/
	
	public Address(String address)
	{
		super(new byte[6]);
		Matcher matcher = addressPattern.matcher(address);
		
		if (matcher.matches())
		{
			for (int i=0; i<matcher.groupCount(); i++)
				this.data[i] = (byte)Integer.parseInt(matcher.group(i+1), 16);
		} else
			throw new IllegalArgumentException("Not a properly formatted MAC address: " + address);
	}
	
	@Override
	public int compareTo(Address o)
	{
		for (int i=5; i>=0; i--)
		{
			if (data[offset+i]<o.data[o.offset+i]) return -1;
			if (data[offset+i]>o.data[o.offset+i]) return 1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Address)
			return compareTo((Address)obj) == 0;
		else
			return false;
	}
	
	@Override
	public String toString()
	{
		return String.format("%02x:%02x:%02x:%02x:%02x:%02x",
				data[offset+0] & 0xff,
				data[offset+1] & 0xff,
				data[offset+2] & 0xff, 
				data[offset+3] & 0xff,
				data[offset+4] & 0xff,
				data[offset+5] & 0xff 
				);
	}

}
