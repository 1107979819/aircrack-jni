package org.tudelft.parse80211.gen;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.lang.model.element.TypeElement;

import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.types.ByteBuffer;

public class DecoderClassGenerator extends ClassGenerator
{

	protected final static Class<?> baseClass = ByteBuffer.class;

	private ArrayList<TypeElement> frames = new ArrayList<>();

	// Inherit from ByteBuffer
	@Override
	public String getSuperClass()
	{
		return baseClass.getCanonicalName();		
	}

	public void addFrameType(TypeElement frame)
	{
		frames.add(frame);
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
		writer.printf("\tpublic %s(byte[] data)\n", classElement.getSimpleName());
		writer.println("\t{");
		writer.println("\t\tsuper(data);");
		writer.println("\t\t");
		
		writer.println("\t\tFrame frame = new Frame(data);");
		writer.println("\t\tfor (int i=0; i<64; i++)");
		writer.println("\t\t\tframes[i] = frame;");
		writer.println("\t\t");
		
		for (TypeElement element : frames)
		{
			FrameType type = element.getAnnotation(FrameType.class);
			int index = type.type() | (type.subType()<<2);
			String classname = getClassName(element);
			writer.printf("\t\tframes[%d] = new %s(data);\n", index, classname);
		}
		
		writer.println("\t}");
	}
	
	@Override
	protected void writeBody(PrintWriter writer)
	{
		super.writeBody(writer);
		
		writer.println("");
		writer.printf("\tpublic Frame decode()\n", classElement.getSimpleName());
		writer.println("\t{");
		writer.println("\t\treturn frames[(data[offset]&0xff)>>2];");
		writer.println("\t}");
	}

	public DecoderClassGenerator(TypeElement classElement)
	{
		super(classElement);
	}

}
