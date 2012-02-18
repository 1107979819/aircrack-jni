package org.tudelft.aircrack.frame.control;

import org.tudelft.aircrack.frame.visitor.FrameVisitor;

public class CfEndCfAckFrame extends CfEndFrame
{
	
	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}

}
