package org.tudelft.aircrack.frame.control;

import org.codehaus.preon.annotation.Bound;
import org.tudelft.aircrack.frame.control.field.BlockAckControl;
import org.tudelft.aircrack.frame.control.field.BlockAckStartingSequence;

public class BlockAckOrRequestFrame extends RaTaFrame
{

	@Bound
	private BlockAckControl blockAckControl;

	@Bound
	private BlockAckStartingSequence blockAckStartingSequence;

	public BlockAckControl getBlockAckControl()
	{
		return blockAckControl;
	}
	
	public void setBlockAckControl(BlockAckControl blockAckControl)
	{
		this.blockAckControl = blockAckControl;
	}

	public BlockAckStartingSequence getBlockAckStartingSequence()
	{
		return blockAckStartingSequence;
	}
	
	public void setBlockAckStartingSequence(
			BlockAckStartingSequence blockAckStartingSequence)
	{
		this.blockAckStartingSequence = blockAckStartingSequence;
	}

}

