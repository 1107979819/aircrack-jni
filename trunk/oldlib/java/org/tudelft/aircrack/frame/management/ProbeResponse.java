package org.tudelft.aircrack.frame.management;

import org.tudelft.aircrack.frame.visitor.FrameVisitor;

public class ProbeResponse extends BeaconOrProbeResponse
{

	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}
	
}
