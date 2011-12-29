package org.tudelft.aircrack.frame.control;

import org.codehaus.preon.annotation.BoundNumber;
import org.tudelft.aircrack.frame.Frame;

public class ControlFrame extends Frame
{

	@BoundNumber(size="16")
	protected int durationOrAid;
	
	public int getDuration()
	{
		return durationOrAid;
	}
	
	public int getAid()
	{
		return this.durationOrAid;
	}
	
	public void setDuration(int duration)
	{
		this.durationOrAid = duration;
	}
	
	public void setAid(int aid)
	{
		this.durationOrAid = aid;
	}
	
	@Override
	public String toString()
	{
		return super.toString();
	}

}
