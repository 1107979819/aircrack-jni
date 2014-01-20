package org.tudelft.parse80211.types;

/**
 * 
 * Variable-sized list of information elements found in Beacon and Probe Response frames. 
 * 
 * @author Niels Brouwers
 *
 */
public class InformationList extends BufferBacked
{

	public InformationList(ByteBuffer buffer, int offset)
	{
		super(buffer, offset);
	}
	
	public InformationElement get(int index)
	{
		int off = offset;
		
		for (int i=0; i<index; i++)
			off += (buffer.data[off+1] & 0xff) + 2;
		
		return new InformationElement(buffer, off);
	}

	public int count()
	{
		int count = 0;

		for (int off=offset; off<buffer.size; off+=(buffer.data[off+1] & 0xff)+2)
			count++;
		
		return count;
	}
	
	public String getSSID()
	{
		String ret = "";
		for (int off=offset; off<buffer.size; off+=(buffer.data[off+1] & 0xff)+2)
			if ((buffer.data[off] & 0xff)==InformationElementId.SSID.getCode())
			{
				for (int i=0; i<(buffer.data[off+1] & 0xff); i++)
					ret+= String.format("%c", buffer.data[off+2+i]);
			}
		
		return ret;
	}

}
