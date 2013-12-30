package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.Element;

import org.tudelft.parse80211.annotations.U8;

public class U8FieldGenerator extends FieldGenerator
{
	
	private final U8 annotation;

	public U8FieldGenerator(Element field, U8 annotation)
	{
		super(field);
		this.annotation = annotation;
	}

	@Override
	public void generateGetter(PrintWriter writer)
	{
		generateGetter(writer, "int", String.format("return data[%d] & 0xff;", annotation.offset()));
	}

	@Override
	public void generateSetter(PrintWriter writer)
	{
		generateSetter(writer, "int", String.format("data[%d] = value;", annotation.offset()));
	}

}
