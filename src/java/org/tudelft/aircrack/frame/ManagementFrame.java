package org.tudelft.aircrack.frame;

import nl.flotsam.preon.annotation.Bound;
import nl.flotsam.preon.annotation.BoundNumber;

public class ManagementFrame extends Frame
{

	@BoundNumber(size="16")
	int duration;
	
	@Bound
	Address adress1;

	@Bound
	Address SA;
	
	@Bound
	Address BSSID;
	
	@Bound
	SequenceControl sequenceControl;

}
