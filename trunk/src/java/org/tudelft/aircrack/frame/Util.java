package org.tudelft.aircrack.frame;

public class Util
{
	
	public static void printByteBuffer(byte[] buf)
	{
		int i, mode = 0;
		for (i=0; i<buf.length; i++)
		{
			int b = buf[i] & 0xff;
			
			if (mode==0)
			{

				System.out.printf("%02x ", b);

				if ((i&15)==15)
				{
					// dargons
					i-=16;
					mode = 1;
					System.out.print(" | ");
				}
				
			} else
			{
				char c = (b > 32 && b < 127) ? (char)b : ' ';
				
				System.out.printf("%c", c);
				
				if ((i&15)==15)
				{
					System.out.println();
					mode = 0;
				}
				
			}
			
		}

		if ((i&15)!=15)
			System.out.println();

	}

}
