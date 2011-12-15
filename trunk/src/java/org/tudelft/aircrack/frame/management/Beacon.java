package org.tudelft.aircrack.frame.management;

import nl.flotsam.preon.annotation.Bound;
import nl.flotsam.preon.annotation.BoundNumber;

import org.tudelft.aircrack.frame.Timestamp;

/**
 * 
 * @author Niels Brouwers
 *
 */
// TODO fix comments
// TODO implement the part of the frame beyond the SSID
public class Beacon extends ManagementFrame
{

	@Bound
	private Timestamp timestamp;
	
	@BoundNumber(size="16")
	private int beaconInterval;

	// TODO check format
	@BoundNumber(size="16")
	private int capability;
	
	@Bound
	private InformationElement ssid;
	
	public Timestamp getTimestamp()
	{
		return timestamp;
	}
	
	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}
	
	/**
	 * The Beacon Interval field represents the number of time units (TUs) between target beacon transmission
	 * times (TBTTs).
	 * 
	 * @return number of time units (TUs) between target beacon transmission times (TBTTs)
	 */
	public int getBeaconInterval()
	{
		return beaconInterval;
	}
	
	/**
	 * The Beacon Interval field represents the number of time units (TUs) between target beacon transmission
	 * times (TBTTs).
	 * 
	 * @param beaconInterval number of time units (TUs) between target beacon transmission times (TBTTs)
	 */
	public void setBeaconInterval(int beaconInterval)
	{
		this.beaconInterval = beaconInterval;
	}
	
	public String getSsid()
	{
		return ssid.getInformationAsString();
	}
	
	public void setSsid(String ssid)
	{
		this.ssid = new InformationElement(ElementId.SSID, ssid);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " SSID: [" + ssid.getInformationAsString() + "]";		
	}

}
