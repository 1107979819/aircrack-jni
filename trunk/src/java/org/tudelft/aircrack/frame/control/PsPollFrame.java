package org.tudelft.aircrack.frame.control;

import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.visitor.FrameVisitor;


public class PsPollFrame extends RaTaFrame
{
	
	public Address getBssId()
	{
		return super.getRA();
	}

	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}

}
