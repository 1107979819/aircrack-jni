package org.tudelft.aircrack.frame.control;

import org.tudelft.aircrack.frame.SubType;
import org.tudelft.aircrack.frame.visitor.FrameVisitor;


public class RtsFrame extends RaTaFrame
{
	
	public RtsFrame()
	{
		frameControl.setSubType(SubType.RTS);
	}

	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}
}
