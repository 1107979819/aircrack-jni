package org.tudelft.aircrack.frame.control;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.Order;
import org.tudelft.aircrack.frame.Address;

public class RaTaFrame extends RaFrame
{

	@Bound
	@Order(2) public Address TA;
	
	public Address getTA()
	{
		return TA;
	}
	
	public void setTA(Address tA)
	{
		TA = tA;
	}

	@Override
	public String toString()
	{
		return super.toString() + " RA: " + RA + " TA:" + TA;
	}
	
}
