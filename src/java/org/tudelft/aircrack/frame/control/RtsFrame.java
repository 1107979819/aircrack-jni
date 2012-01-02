package org.tudelft.aircrack.frame.control;

import org.tudelft.aircrack.frame.SubType;


public class RtsFrame extends RaTaFrame
{
	
	public RtsFrame()
	{
		frameControl.setSubType(SubType.RTS);
	}

}
