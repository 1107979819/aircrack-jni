package org.tudelft.aircrack.frame.control;

import org.tudelft.aircrack.frame.Address;


public class CfEndFrame extends RaTaFrame
{

	public Address getBssId()
	{
		return super.getTA();
	}
	
}
