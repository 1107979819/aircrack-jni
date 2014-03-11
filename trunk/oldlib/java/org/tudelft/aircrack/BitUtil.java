package org.tudelft.aircrack;

public class BitUtil
{

	public static int getBits(int packed, int start, int count)
	{
		return (packed >> start) & ((1 << count) - 1);
	}

}
