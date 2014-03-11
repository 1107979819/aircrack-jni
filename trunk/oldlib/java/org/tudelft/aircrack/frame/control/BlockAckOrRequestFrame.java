package org.tudelft.aircrack.frame.control;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.Order;
import org.tudelft.aircrack.frame.control.field.BlockAckControl;
import org.tudelft.aircrack.frame.control.field.BlockAckStartingSequence;
import org.tudelft.aircrack.frame.visitor.FrameVisitor;

public class BlockAckOrRequestFrame extends RaTaFrame
{

	@Bound
	@Order(1) private BlockAckControl blockAckControl;

	@Bound
	@Order(2) private BlockAckStartingSequence blockAckStartingSequence;

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

	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}

}

