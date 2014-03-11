package org.tudelft.aircrack.frame.control.field;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.Order;

public class BlockAckStartingSequence
{
	
	@BoundNumber(size="4")
	@Order(1) private int fragmentNumber;

	@BoundNumber(size="12")
	@Order(2) private int startingSequenceNumber;
	
	public int getFragmentNumber()
	{
		return fragmentNumber;
	}
	
	public int getStartingSequenceNumber()
	{
		return startingSequenceNumber;
	}
	
	public void setFragmentNumber(int fragmentNumber)
	{
		this.fragmentNumber = fragmentNumber;
	}
	
	public void setStartingSequenceNumber(int startingSequenceNumber)
	{
		this.startingSequenceNumber = startingSequenceNumber;
	}

}
