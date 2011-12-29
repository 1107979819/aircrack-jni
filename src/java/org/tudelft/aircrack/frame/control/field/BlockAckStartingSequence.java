package org.tudelft.aircrack.frame.control.field;

import org.codehaus.preon.annotation.BoundNumber;

public class BlockAckStartingSequence
{
	
	@BoundNumber(size="4")
	private int fragmentNumber;

	@BoundNumber(size="12")
	private int startingSequenceNumber;
	
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
