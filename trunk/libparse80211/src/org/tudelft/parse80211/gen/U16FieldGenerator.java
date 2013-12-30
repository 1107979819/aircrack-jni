package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.Element;

import org.tudelft.parse80211.annotations.U16;

public class U16FieldGenerator extends FieldGenerator
{
	
	private final U16 annotation;

	public U16FieldGenerator(Element field, U16 annotation)
	{
		super(field);
		this.annotation = annotation;
	}

	@Override
	public void generateGetter(PrintWriter writer)
	{
		
		generateGetter(writer, "int",
				String.format("return (data[%d] & 0xff) + ((data[%d] & 0xff)<<8);", annotation.offset(), annotation.offset()+1)
				);
	}

	@Override
	public void generateSetter(PrintWriter writer)
	{
		
		generateSetter(writer, "int",
				String.format("data[%d] = (byte)(value & 0xff);", annotation.offset()),
				String.format("data[%d] = (byte)((value>>8) & 0xff);", annotation.offset()+1)
				);
	}

}
