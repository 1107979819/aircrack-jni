package org.tudelft.aircrack.frame.management;

import org.tudelft.aircrack.frame.SubType;

public class Beacon extends BeaconOrProbeResponse
{
	
	public Beacon()
	{
		frameControl.setSubType(SubType.Beacon);
	}
	
}
