package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.TypeElement;

import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.types.BufferBacked;

public class EncoderClassGenerator extends EncoderDecoderClassGenerator
{
	
	protected final static Class<?> baseClass = BufferBacked.class;

	public EncoderClassGenerator(TypeElement classElement)
	{
		super(classElement);
	}

	// Inherit from ByteBuffer
	@Override
	public String getSuperClass()
	{
		return baseClass.getCanonicalName();
	}

	private String getFrameFieldName(TypeElement element)
	{
		String name = element.getSimpleName().toString();
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	@Override
	protected void writeFields(PrintWriter writer)
	{
		writer.println();

		for (TypeElement element : frames)
			writer.printf("\tprivate %s %s;\n", element.getSimpleName(), getFrameFieldName(element));
	}

	@Override
	protected void writeConstructor(PrintWriter writer)
	{
		writer.println("");
		writer.printf("\tpublic %s(ByteBuffer buffer, int offset)\n", classElement.getSimpleName());
		writer.println("\t{");
		writer.println("\t\tsuper(buffer, offset);");
		writer.println("\t\t");
		
		for (TypeElement element : frames)
			writer.printf("\t\t%s = new %s(buffer, offset);\n", getFrameFieldName(element), element.getSimpleName());
		
		writer.println("\t\t");
		writer.println("\t}");
	}
	
	protected void writeGetter(PrintWriter writer, TypeElement frame)
	{
		FrameType type = frame.getAnnotation(FrameType.class);
		int index = type.type() | (type.subType()<<2);
		
		writer.println();
		writer.printf("\tpublic %s get%s()\n", frame.getSimpleName(), frame.getSimpleName());
		writer.println("\t{");
		writer.printf("\t\tbuffer.data[offset] = (byte)0x%02x;\n", index << 2);
		writer.printf("\t\tbuffer.size = offset + %d;\n", type.size());
		writer.printf("\t\treturn %s;\n", getFrameFieldName(frame));
		writer.println("\t}");
	}
	
	private void writeBufferGetter(PrintWriter writer)
	{
		writer.println();
		writer.println("\tpublic ByteBuffer getBuffer()");
		writer.println("\t{");
		writer.println("\t\treturn this.buffer;");
		writer.println("\t}");
	}
	
	@Override
	protected void writeBody(PrintWriter writer)
	{
		writeBufferGetter(writer);
		
		super.writeBody(writer);
		
		for (TypeElement element : frames)
			writeGetter(writer, element);
	}

}
