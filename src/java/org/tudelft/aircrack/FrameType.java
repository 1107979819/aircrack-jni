package org.tudelft.aircrack;

public enum FrameType
{
	
	MANAGEMENT,
	CONTROL,
	DATA,
	IMPOSSIBRU;
	
	public static FrameType getByType(int type)
	{
		return FrameType.values()[type];
	}

}
