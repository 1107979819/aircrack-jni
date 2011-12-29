package org.tudelft.aircrack.frame.control;

import org.codehaus.preon.annotation.Bound;
import org.tudelft.aircrack.frame.Address;

public class RaFrame extends ControlFrame
{

	@Bound
	protected Address RA;

	public Address getRA()
	{
		return RA;
	}

	@Override
	public String toString()
	{
		return super.toString() + " RA: " + RA;
	}
	
}
