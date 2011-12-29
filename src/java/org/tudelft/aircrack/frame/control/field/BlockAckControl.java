package org.tudelft.aircrack.frame.control.field;

import org.codehaus.preon.annotation.BoundNumber;

public class BlockAckControl
{
	
	@BoundNumber(size="12")
	private int reserved;
	
	@BoundNumber(size="4")
	private int tid;
	
	public int getTid()
	{
		return tid;
	}
	
	public void setTid(int tid)
	{
		this.tid = tid;
	}

}
