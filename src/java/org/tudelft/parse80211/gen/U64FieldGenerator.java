package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.Element;

import org.tudelft.parse80211.annotations.U64;

public class U64FieldGenerator extends SimpleFieldGenerator
{
	
	private final U64 annotation;

	public U64FieldGenerator(Element field, U64 annotation)
	{
		super(field);
		this.annotation = annotation;
	}

	@Override
	public void generateGetter(PrintWriter writer)
	{
		generateGetter(writer, "long",
				String.format(
						"return (buffer.data[offset+%d] & 0xff) + " + 
						"((buffer.data[offset+%d] & 0xff)<<8) + " + 
						"((buffer.data[offset+%d] & 0xff)<<16) + " + 
						"((buffer.data[offset+%d] & 0xff)<<24) + " + 
						"((buffer.data[offset+%d] & 0xff)<<32) + " + 
						"((buffer.data[offset+%d] & 0xff)<<40) + " + 
						"((buffer.data[offset+%d] & 0xff)<<48) + " + 
						"((buffer.data[offset+%d] & 0xff)<<56);", 
						annotation.offset(),
						annotation.offset()+1,
						annotation.offset()+2,
						annotation.offset()+3,
						annotation.offset()+4,
						annotation.offset()+5,
						annotation.offset()+6,
						annotation.offset()+7
				));
	}

	@Override
	public void generateSetter(PrintWriter writer)
	{
		generateSetter(writer, "long",
				String.format("buffer.data[offset+%d] = (byte)(value & 0xff);", annotation.offset()),
				String.format("buffer.data[offset+%d] = (byte)((value>>8) & 0xff);", annotation.offset()+1),
				String.format("buffer.data[offset+%d] = (byte)((value>>16) & 0xff);", annotation.offset()+2),
				String.format("buffer.data[offset+%d] = (byte)((value>>24) & 0xff);", annotation.offset()+3),
				String.format("buffer.data[offset+%d] = (byte)((value>>32) & 0xff);", annotation.offset()+4),
				String.format("buffer.data[offset+%d] = (byte)((value>>40) & 0xff);", annotation.offset()+5),
				String.format("buffer.data[offset+%d] = (byte)((value>>48) & 0xff);", annotation.offset()+6),
				String.format("buffer.data[offset+%d] = (byte)((value>>56) & 0xff);", annotation.offset()+7)
				);
	}

}
