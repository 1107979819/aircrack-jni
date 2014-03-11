package org.tudelft.aircrack.frame.control;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.Order;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.visitor.FrameVisitor;

public class RaFrame extends ControlFrame
{

	@Bound
	@Order(1) public Address RA;

	public Address getRA()
	{
		return RA;
	}
	
	public void setRA(Address rA)
	{
		RA = rA;
	}

	@Override
	public String toString()
	{
		return super.toString() + " RA: " + RA;
	}
	
	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}

}
