package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.Element;

import org.tudelft.parse80211.annotations.Bit;

public class BitFieldGenerator extends FieldGenerator
{

	private final Bit annotation;
	
	public BitFieldGenerator(Element field, Bit annotation)
	{
		super(field);
		this.annotation = annotation;
	}

	@Override
	public void generateGetter(PrintWriter writer)
	{
		String body = String.format("return (data[%d] & 0x%02x) != 0;", annotation.offset(), 1<<annotation.bit());
		generateGetter(writer, "boolean", body);
	}

	@Override
	public void generateSetter(PrintWriter writer)
	{
		generateSetter(writer, "boolean",
				"if (value)",
				String.format("\tdata[%d] |= 0x%02x;", annotation.offset(), 1<<annotation.bit()),
				"else",
				String.format("\tdata[%d] &= ~0x%02x;", annotation.offset(), 1<<annotation.bit())
				);
	}

}
