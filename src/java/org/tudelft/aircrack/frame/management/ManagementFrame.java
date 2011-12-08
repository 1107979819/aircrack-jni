package org.tudelft.aircrack.frame.management;

import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.SequenceControl;

import nl.flotsam.preon.annotation.Bound;
import nl.flotsam.preon.annotation.BoundNumber;

public class ManagementFrame extends Frame
{

	@BoundNumber(size="16")
	int duration;
	
	@Bound
	Address destination;

	@Bound
	Address source;
	
	@Bound
	Address BSSID; 
	
	@Bound
	SequenceControl sequenceControl;

}
