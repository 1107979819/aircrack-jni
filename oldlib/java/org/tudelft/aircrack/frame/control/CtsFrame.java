package org.tudelft.aircrack.frame.control;

import org.tudelft.aircrack.frame.SubType;
import org.tudelft.aircrack.frame.visitor.FrameVisitor;

public class CtsFrame extends RaFrame
{
	
	public CtsFrame()
	{
		frameControl.setSubType(SubType.CTS);
	}
	
	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}

}
