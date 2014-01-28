package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.Element;

import org.tudelft.parse80211.annotations.U32;

public class U32FieldGenerator extends SimpleFieldGenerator
{
	
	private final U32 annotation;

	public U32FieldGenerator(Element field, U32 annotation)
	{
		super(field);
		this.annotation = annotation;
	}

	@Override
	public void generateGetter(PrintWriter writer)
	{
		generateGetter(writer, "int",
				String.format(
						"return (buffer.data[offset+%d] & 0xff) + " + 
						"((buffer.data[offset+%d] & 0xff)<<8) + " + 
						"((buffer.data[offset+%d] & 0xff)<<16) + " + 
						"((buffer.data[offset+%d] & 0xff)<<24);",
						annotation.offset(),
						annotation.offset()+1,
						annotation.offset()+2,
						annotation.offset()+3
				));
	}

	@Override
	public void generateSetter(PrintWriter writer)
	{
		generateSetter(writer, "int",
				String.format("buffer.data[offset+%d] = (byte)(value & 0xff);", annotation.offset()),
				String.format("buffer.data[offset+%d] = (byte)((value>>8) & 0xff);", annotation.offset()+1),
				String.format("buffer.data[offset+%d] = (byte)((value>>16) & 0xff);", annotation.offset()+2),
				String.format("buffer.data[offset+%d] = (byte)((value>>24) & 0xff);", annotation.offset()+3)
				);
	}

}
