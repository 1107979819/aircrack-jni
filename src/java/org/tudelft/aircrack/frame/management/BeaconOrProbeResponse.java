package org.tudelft.aircrack.frame.management;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;
import org.tudelft.aircrack.frame.Timestamp;
import org.tudelft.aircrack.frame.management.field.Capability;
import org.tudelft.aircrack.frame.management.field.ElementId;
import org.tudelft.aircrack.frame.management.field.InformationElement;
import org.tudelft.aircrack.frame.management.field.InformationElements;

/**
 * 
 * @author Niels Brouwers
 *
 */
// TODO fix comments
public class BeaconOrProbeResponse extends ManagementFrame
{

	@Bound
	private Timestamp timestamp;
	
	@BoundNumber(size="16")
	private int beaconInterval;

	@Bound
	private Capability capability;
	
	@Bound
	private InformationElements elements;
	
	public Capability getCapability()
	{
		return capability;
	}
	
	public void setCapability(Capability capability)
	{
		this.capability = capability;
	}
	
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
	
	public InformationElements getElements()
	{
		return elements;
	}
	
	public String getSsid()
	{
		return elements.getElement(ElementId.SSID).getInformationAsString();
	}
	
	public void setSsid(String ssid)
	{
		elements.replaceElement(new InformationElement(ElementId.SSID, ssid));
	}
	
	@Override
	public String toString()
	{
		String ret = super.toString() + " SSID: [" + getSsid() + "]\n";
		
		for (InformationElement element : elements.getElements())
			ret += "\t" + element.toString() + "\n";
		
		return ret;
	}

}
