package org.tudelft.aircrack.frame;

import org.codehaus.preon.annotation.BoundNumber;

public class SequenceControl
{
	
	@BoundNumber(size="4")
	public int fragmentNumber;
	
	@BoundNumber(size="12")
	public int sequenceNumber;
	
	@Override
	public String toString()
	{
		return String.format("[fragmentNumber=%d, sequenceNumber=%d]", fragmentNumber, sequenceNumber);
	}

}
