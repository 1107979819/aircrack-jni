package org.tudelft.aircrack.frame.control;

import org.codehaus.preon.annotation.Bound;
import org.tudelft.aircrack.frame.Address;

public class RaTaFrame extends RaFrame
{

	@Bound
	protected Address TA;

	public Address getTA()
	{
		return TA;
	}

	@Override
	public String toString()
	{
		return super.toString() + " RA: " + RA + " TA:" + TA;
	}
	
}
