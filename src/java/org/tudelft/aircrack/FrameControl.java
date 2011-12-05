package org.tudelft.aircrack;

public class FrameControl
{
	
	private int version, subType;
	private FrameType frameType;
	
	public static FrameControl decode(int packed)
	{
		FrameControl ret = new FrameControl();

		// The two most significant bits represent the protocol version
		ret.version = BitUtil.getBits(packed, 14, 2);

		// Frame type, subtype
		int type = BitUtil.getBits(packed, 12, 2);
		ret.subType = BitUtil.getBits(packed, 8, 4);
		
		ret.frameType = FrameType.values()[type];
		
		return ret;
	}
	
	public FrameType getFrameType()
	{
		return frameType;
	}
	
	@Override
	public String toString()
	{
		return frameType.toString();
	}

}
