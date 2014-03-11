package org.tudelft.aircrack.frame;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.Order;

public class SequenceControl
{
	
	@BoundNumber(size="4")
	@Order(1) public int fragmentNumber;
	
	@BoundNumber(size="12")
	@Order(2) public int sequenceNumber;
	
	@Override
	public String toString()
	{
		return String.format("[fragmentNumber=%d, sequenceNumber=%d]", fragmentNumber, sequenceNumber);
	}

}
