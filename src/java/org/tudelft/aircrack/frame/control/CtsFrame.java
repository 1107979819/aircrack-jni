package org.tudelft.aircrack.frame.control;

import org.tudelft.aircrack.frame.SubType;

public class CtsFrame extends RaFrame
{
	
	public CtsFrame()
	{
		frameControl.setSubType(SubType.CTS);
	}
	
}
