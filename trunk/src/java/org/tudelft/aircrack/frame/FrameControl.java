package org.tudelft.aircrack.frame;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.buffer.ByteOrder;

public class FrameControl
{

	@BoundNumber(size="4", byteOrder = ByteOrder.BigEndian)
	public int subType;

	@BoundNumber(size="2", byteOrder = ByteOrder.BigEndian)
	public FrameType type = FrameType.Reserved;

	@BoundNumber(size="2", byteOrder = ByteOrder.BigEndian)
	public int protocolVersion = 0;
	
	@Bound
	public boolean toDs;
	
	@Bound
	public boolean fromDs;
	
	@Bound
	public boolean moreFrag;
	
	@Bound
	public boolean retry;
	
	@Bound
	public boolean powerMgt;
	
	@Bound
	public boolean moreData;
	
	@Bound
	public boolean WEP;
	
	@Bound
	public boolean order;
	
	public FrameControl()
	{
//		type = FrameType.Reserved;
	}

	@Override
	public String toString()
	{
		return String.format(
				"FrameControl[protocol=%d, type=%d/%s, subType=%d/%s]",
				protocolVersion,
				type.ordinal(), getType().toString(),
				subType, getSubType().toString()
				);
	}
	
	public FrameType getType()
	{
		return type;
	}
	
	public SubType getSubType()
	{
		return SubType.find(getType(), subType);
	}
	
	public boolean getFromDs()
	{
		return fromDs;
	}
	
	public boolean getMoreData()
	{
		return moreData;
	}
	
	public boolean getMoreFrag()
	{
		return moreFrag;
	}
	
	public boolean getOrder()
	{
		return order;
	}
	
	public boolean getPowerMgt()
	{
		return powerMgt;
	}
	
	public boolean getRetry()
	{
		return retry;
	}
	
	public boolean getToDs()
	{
		return toDs;
	}
	
	public boolean getWEP()
	{
		return WEP;
	}
	
	public void setFromDs(boolean fromDs)
	{
		this.fromDs = fromDs;
	}
	
	public void setMoreData(boolean moreData)
	{
		this.moreData = moreData;
	}
	
	public void setMoreFrag(boolean moreFrag)
	{
		this.moreFrag = moreFrag;
	}
	
	public void setOrder(boolean order)
	{
		this.order = order;
	}
	
	public void setPowerMgt(boolean powerMgt)
	{
		this.powerMgt = powerMgt;
	}
	
	public void setProtocolVersion(int protocolVersion)
	{
		this.protocolVersion = protocolVersion;
	}
	
	public void setRetry(boolean retry)
	{
		this.retry = retry;
	}
	
	public void setSubType(int subType)
	{
		this.subType = subType;
	}
	
	public void setToDs(boolean toDs)
	{
		this.toDs = toDs;
	}
	
	public void setType(FrameType type)
	{
		this.type = type;
	}
	
	public void setWEP(boolean wEP)
	{
		WEP = wEP;
	}
	
}
