package org.tudelft.aircrack.frame.control;

import org.tudelft.aircrack.frame.visitor.FrameVisitor;


public class AckFrame extends RaFrame 
{
	
	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}

}
