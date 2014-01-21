package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.TypeElement;

import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.types.BufferBacked;

public class DecoderClassGenerator extends EncoderDecoderClassGenerator
{

	protected final static Class<?> baseClass = BufferBacked.class;

	public DecoderClassGenerator(TypeElement classElement)
	{
		super(classElement);
	}

	// Inherit from ByteBuffer
	@Override
	public String getSuperClass()
	{
		return baseClass.getCanonicalName();
	}

	@Override
	protected void writeFields(PrintWriter writer)
	{
		writer.println();
		writer.println("\tprivate Frame[] frames = new Frame[64];");
	}

	@Override
	protected void writeConstructor(PrintWriter writer)
	{
		writer.println("");
		writer.printf("\tpublic %s(ByteBuffer buffer)\n", classElement.getSimpleName());
		writer.println("\t{");
		writer.println("\t\tsuper(buffer);");
		writer.println("\t\t");
		
		writer.println("\t\tFrame frame = new Frame(buffer);");
		writer.println("\t\tfor (int i=0; i<64; i++)");
		writer.println("\t\t\tframes[i] = frame;");
		writer.println("\t\t");
		
		for (TypeElement element : frames)
		{
			FrameType type = element.getAnnotation(FrameType.class);
			int index = type.type() | (type.subType()<<2);
			writer.printf("\t\tframes[%d] = new %s(buffer);\n", index, element.getSimpleName());
		}
		
		writer.println("\t}");
	}
	
	@Override
	protected void writeBody(PrintWriter writer)
	{
		super.writeBody(writer);
		
		writer.println("");
		writer.printf("\tpublic Frame decode(int size)\n", classElement.getSimpleName());
		writer.println("\t{");
		writer.println("\t\treturn frames[(buffer.data[offset]&0xff)>>2];");
		writer.println("\t}");
	}

}
