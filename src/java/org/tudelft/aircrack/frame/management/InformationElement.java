package org.tudelft.aircrack.frame.management;

import nl.flotsam.preon.annotation.BoundList;
import nl.flotsam.preon.annotation.BoundNumber;


/**
 * 
 * Elements are defined to have a common general format consisting of a 1 octet Element ID field, a 1 octet
 * length field, and a variable-length element-specific information field. Each element is assigned a unique
 * Element ID as defined in this standard. The Length field specifies the number of octets in the Information
 * field.
 *  
 * @author Niels Brouwers
 *
 */
// TODO is this class specific for management frames?
// TODO fix comments
public class InformationElement
{
	
	// TODO check format
	@BoundNumber(size="8")
	private ElementId elementId;
	
	@BoundNumber(size="8")
	private int length;

	@BoundList(size="length")
	private byte[] data;
	
	public InformationElement()
	{
		// empty constructor for Preon
	}
	
	public InformationElement(ElementId elementId, byte[] data)
	{
		this.elementId = elementId;
		this.length = data.length;
		this.data = data;
	}
	
	public InformationElement(ElementId elementId, String string)
	{
		this.elementId = elementId;
		// TODO encoding
		this.data = string.getBytes();
		this.length = data.length;
	}
	
	@Override
	public String toString()
	{
		return elementId.toString() + " [length="+length+"]";
	}
	
	public String getInformationAsString()
	{
		return new String(data);
	}
	
}
