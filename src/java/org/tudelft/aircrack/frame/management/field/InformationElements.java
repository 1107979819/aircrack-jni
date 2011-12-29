package org.tudelft.aircrack.frame.management.field;

import java.util.ArrayList;
import java.util.List;

// TODO keep elements sorted by element id
public class InformationElements
{
	
	@InformationElementList
	public List<InformationElement> elements = new ArrayList<InformationElement>();
	
	public List<InformationElement> getElements()
	{
		return elements;
	}
	
	public void replaceElement(InformationElement element)
	{
		// Remove any elements with the same element ID
		for (int i=0; i<elements.size(); i++)
			if (elements.get(i).getElementId()==element.getElementId())
			{
				elements.remove(i); 
				i--;
			}
		
		// Add the new created element
		elements.add(element);		
	}
	
	public void addElement(InformationElement element)
	{
		// Add the new created element
		elements.add(element);		
	}
	
	public InformationElement getElement(ElementId elementId)
	{
		for (InformationElement element : elements)
			if (element.getElementId()==elementId)
				return element;
		
		return null;
	}
	
	public List<InformationElement> getElements(ElementId elementId)
	{
		ArrayList<InformationElement> ret = new ArrayList<InformationElement>();
		
		for (InformationElement element : elements)
			if (element.getElementId()==elementId)
				ret.add(element);
		
		return ret;
	}

}
