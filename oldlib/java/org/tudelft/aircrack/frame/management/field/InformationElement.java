package org.tudelft.aircrack.frame.management.field;

import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.Order;


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
	@Order(1) public ElementId elementId;
	
	@BoundNumber(size="8")
	@Order(2) public int length;

	@BoundList(size="length")
	@Order(3) public byte[] data;
	
	public InformationElement()
	{
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
	
	public ElementId getElementId()
	{
		return elementId;
	}
	
	public byte[] getData()
	{
		return data;
	}
	
	@Override
	public String toString()
	{
		StringBuffer dataStr = new StringBuffer();
		
		for (int i=0; i<data.length; i++)
			dataStr.append(String.format("%02x ", data[i]));
		
		return String.format("%-20s length:%-4d %s", elementId, length, dataStr.toString());
	}
	
	public String getInformationAsString()
	{
		return new String(data);
	}
	
}
