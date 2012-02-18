package org.tudelft.aircrack.frame.management;

import org.tudelft.aircrack.frame.SubType;
import org.tudelft.aircrack.frame.visitor.FrameVisitor;

public class Beacon extends BeaconOrProbeResponse
{
	
	public Beacon()
	{
		frameControl.setSubType(SubType.Beacon);
	}
	
	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}
	
}
