package org.tudelft.aircrack.frame;

public class Util
{
	
	private final static int printWidth = 16;

	public static void printLine(byte[] buf, int start)
	{
		
		// Print hex 
		for (int i=0; i<printWidth; i++)
		{
			
			// Avoid out-of-bounds exceptions
			if (i>=buf.length) break;
			
			// Print hex byte
			System.out.printf("%02x ", buf[i] & 0xff);
			
		}
		
		// Print divider
		System.out.print(" | ");
		
		// Print chars
		for (int i=0; i<printWidth; i++)
		{
			
			// Avoid out-of-bounds exceptions
			if (i>=buf.length) break;
			
			// Print char
			char c = (buf[i] > 32 && buf[i] < 255) ? (char)buf[i] : ' ';
			
			System.out.printf("%c", c);			
		}
		
	}
	
	public static void printByteBuffer(byte[] buf)
	{
		
		for (int i=0; i<buf.length; i+=16)
		{
			printLine(buf, i);
			System.out.println();
		}

	}

}
