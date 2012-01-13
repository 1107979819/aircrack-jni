package org.tudelft.aircrack.frame;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.Order;
import org.codehaus.preon.buffer.ByteOrder;

public class FrameControl
{

	@BoundNumber(size="4", byteOrder = ByteOrder.BigEndian)
	@Order(1) public int subType;

	@BoundNumber(size="2", byteOrder = ByteOrder.BigEndian)
	@Order(2) public FrameType type = FrameType.Reserved;

	@BoundNumber(size="2", byteOrder = ByteOrder.BigEndian)
	@Order(3) public int protocolVersion = 0;
	
	@Bound
	@Order(4) public boolean toDs;
	
	@Bound
	@Order(5) public boolean fromDs;
	
	@Bound
	@Order(6) public boolean moreFrag;
	
	@Bound
	@Order(7) public boolean retry;
	
	@Bound
	@Order(8) public boolean powerMgt;
	
	@Bound
	@Order(9) public boolean moreData;
	
	@Bound
	@Order(10) public boolean WEP;
	
	@Bound
	@Order(11) public boolean order;
	
	@Override
	public String toString()
	{
		String flags = "";
		
		if (toDs) flags += "toDs ";
		if (fromDs) flags += "fromDs ";
		if (moreFrag) flags += "moreFrag ";
		if (retry) flags += "retry ";
		if (powerMgt) flags += "powerMgt ";
		if (moreData) flags += "moreData ";
		if (WEP) flags += "WEP ";
		if (order) flags += "order ";
		
		return String.format(
				"FrameControl[protocol=%d, type=%d/%s, subType=%d/%s, flags=%s]",
				protocolVersion,
				type.ordinal(), getType().toString(),
				subType, getSubType().toString(),
				flags.trim()
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
	
	public void setSubType(SubType subType)
	{
		this.subType = subType.getSubType();
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
