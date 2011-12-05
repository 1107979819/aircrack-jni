package org.tudelft.aircrack.frame;

import nl.flotsam.preon.annotation.BoundNumber;

public class SequenceControl
{
	
	@BoundNumber(size="4")
	int fragmentNumber;
	
	@BoundNumber(size="12")
	int sequenceNumber;
	
	@Override
	public String toString()
	{
		return String.format("[fragmentNumber=%d, sequenceNumber=%d]", fragmentNumber, sequenceNumber);
	}

}
