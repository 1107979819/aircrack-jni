package org.tudelft.aircrack.frame.control;

import org.codehaus.preon.annotation.BoundNumber;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.FrameType;
import org.tudelft.aircrack.frame.visitor.FrameVisitor;

public class ControlFrame extends Frame
{

	@BoundNumber(size="16")
	public int durationOrAid;
	
	public ControlFrame()
	{
		frameControl.setType(FrameType.Control);
	}
	
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

	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}

}
