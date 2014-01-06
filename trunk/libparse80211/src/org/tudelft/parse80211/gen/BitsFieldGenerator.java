package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.Element;

import org.tudelft.parse80211.annotations.Bits;

public class BitsFieldGenerator extends SimpleFieldGenerator
{
	
	private final Bits annotation;

	public BitsFieldGenerator(Element field, Bits annotation)
	{
		super(field);
		this.annotation = annotation;
	}

	@Override
	public void generateGetter(PrintWriter writer)
	{
		int mask = (1 << annotation.count()) - 1;
		generateGetter(writer, "int", String.format("return (data[offset+%d] >> %d) & 0x%02x;", annotation.offset(), annotation.start(), mask));
	}

	@Override
	public void generateSetter(PrintWriter writer)
	{
		int mask = (1 << annotation.count()) - 1;
		generateSetter(writer, "int", String.format("data[offset+%d] = (byte)value;", annotation.offset()));
	}

}
