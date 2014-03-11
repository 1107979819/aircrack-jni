package org.tudelft.aircrack.frame.control;

import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.visitor.FrameVisitor;


public class CfEndFrame extends RaTaFrame
{

	public Address getBssId()
	{
		return super.getTA();
	}
	
	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}

}
