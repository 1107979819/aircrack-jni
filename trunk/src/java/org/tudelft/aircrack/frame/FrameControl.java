package org.tudelft.aircrack.frame;

import nl.flotsam.preon.annotation.Bound;
import nl.flotsam.preon.annotation.BoundNumber;

public class FrameControl
{

	@BoundNumber(size="2")
	int protocolVersion;
	
	@BoundNumber(size="2")
	int type;
	
	@BoundNumber(size="4")
	int subType;

	@Bound
	boolean toDs;
	
	@Bound
	boolean fromDs;
	
	@Bound
	boolean moreFrag;
	
	@Bound
	boolean retry;
	
	@Bound
	boolean powerMgt;
	
	@Bound
	boolean moreData;
	
	@Bound
	boolean WEP;
	
	@Bound
	boolean order;

	@Override
	public String toString()
	{
		return String.format(
				"FrameControl[protocol=%d, type=%d/%s, subType=%d/%s]",
				protocolVersion,
				type, getType().toString(),
				subType, getSubType().toString()
				);
	}
	
	public PacketType getType()
	{
		return PacketType.values()[type];
	}
	
	public SubType getSubType()
	{
		return SubType.find(getType(), subType);
	}

}
