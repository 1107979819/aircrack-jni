package org.tudelft.parse80211.types;

public class ByteBuffer
{
	
	public byte[] data;
	public int size;
	
	public ByteBuffer(byte[] data)
	{
		this.data = data;
		this.size = data.length;
	}

	private final static int printWidth = 16;

	public static void printLine(StringBuffer buffer, byte[] buf, int start, int count)
	{
		
		// Print hex 
		for (int i=0; i<printWidth; i++)
		{
			
			// Avoid out-of-bounds exceptions
			if (start+i>=count)
				buffer.append("  ");
			else
				// Print hex byte
				buffer.append(String.format("%02x ", buf[start+i] & 0xff));
		}
		
		// Print divider
		buffer.append(" | ");
		
		// Print chars
		for (int i=0; i<printWidth; i++)
		{
			
			// Avoid out-of-bounds exceptions
			if (start+i>=count) break; 
			
			// Print char
			char c = (buf[start+i] > 32 && buf[start+i] < 255) ? (char)buf[start+i] : ' ';
			
			buffer.append(String.format("%c", c));
		}
		
	}
	
	public String toHex(int count)
	{
		StringBuffer buffer = new StringBuffer();
		
		for (int i=0; i<size; i+=16)
		{
			printLine(buffer, data, i, count);
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
	public String toHex()
	{
		return toHex(size);
	}
	
}
