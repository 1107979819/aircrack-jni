package org.tudelft.aircrack.frame.management;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.SequenceControl;

public class ManagementFrame extends Frame
{

	@BoundNumber(size="16")
	public int duration;
	
	@Bound
	public Address address1;

	@Bound
	public Address SA;
	
	@Bound
	public Address BSSID;
	
	@Bound
	public SequenceControl sequenceControl;
	
	public ManagementFrame()
	{
		address1 =  BSSID = Address.Broadcast;
		SA = Address.Zero;
		sequenceControl = new SequenceControl();
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public Address getAddress1()
	{
		return address1;
	}
	
	public Address getSA()
	{
		return SA;
	}
	
	public Address getBSSID()
	{
		return BSSID;
	}
	
	public void setDuration(int duration)
	{
		this.duration = duration;
	}
	
	public void setAddress1(Address address1)
	{
		this.address1 = address1;
	}
	
	public void setSA(Address sA)
	{
		SA = sA;
	}
	
	public void setBSSID(Address bSSID)
	{
		BSSID = bSSID;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " duration: " + duration + " adr1:" + address1 + " SA:" + SA + " BSSID:" + BSSID;
	}

}
