package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.TypeElement;

import org.tudelft.parse80211.annotations.FrameType;

public class EncoderClassGenerator extends EncoderDecoderClassGenerator
{
	
	private final static int bufferSize = 8192;

	public EncoderClassGenerator(TypeElement classElement)
	{
		super(classElement);
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
		writer.printf("\tprivate ByteBuffer buffer = new ByteBuffer(new byte[%d]);\n", bufferSize);
		writer.println();

		for (TypeElement element : frames)
			writer.printf("\tprivate %s %s = new %s(buffer);\n",
					element.getSimpleName(),
					getFrameFieldName(element),
					element.getSimpleName()
					);
	}

	@Override
	protected void writeConstructor(PrintWriter writer)
	{
	}
	
	protected void writeGetter(PrintWriter writer, TypeElement frame)
	{
		FrameType type = frame.getAnnotation(FrameType.class);
		int index = type.type() | (type.subType()<<2);
		
		writer.println();
		writer.printf("\tpublic %s get%s()\n", frame.getSimpleName(), frame.getSimpleName());
		writer.println("\t{");
		writer.printf("\t\tbuffer.data[0] = (byte)0x%02x;\n", index << 2);
		writer.printf("\t\tbuffer.size = %d;\n", type.size());
		writer.printf("\t\treturn %s;\n", getFrameFieldName(frame));
		writer.println("\t}");
	}
	
	@Override
	protected void writeBody(PrintWriter writer)
	{
		super.writeBody(writer);
		
		for (TypeElement element : frames)
			writeGetter(writer, element);
	}

}
