package org.tudelft.aircrack.frame.control.field;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.Order;

public class BlockAckControl
{
	
	@BoundNumber(size="12")
	@Order(1) private int reserved;
	
	@BoundNumber(size="4")
	@Order(2) private int tid;
	
	public int getTid()
	{
		return tid;
	}
	
	public void setTid(int tid)
	{
		this.tid = tid;
	}

}
