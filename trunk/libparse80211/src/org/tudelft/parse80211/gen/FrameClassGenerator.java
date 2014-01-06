package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.TypeElement;

import org.tudelft.parse80211.types.ByteBuffer;

public class FrameClassGenerator extends ClassGenerator
{

	protected final static Class<?> baseClass = ByteBuffer.class;

	public FrameClassGenerator(TypeElement classElement)
	{
		super(classElement);
	}
	
	// Super class. Inherit from ByteBuffer if none given.
	@Override
	public String getSuperClass()
	{
		String superClass = super.getSuperClass();
		
		if (superClass==null)
			superClass = baseClass.getCanonicalName();
		
		return superClass;		
	}

	protected void writeConstructor(PrintWriter writer)
	{
		writer.println("");
		writer.printf("\tpublic %s(byte[] data)\n", classElement.getSimpleName());
		writer.println("\t{");
		writer.println("\t\tsuper(data);");
		
		writer.println("");
		for (Generator generator : generators)
			generator.generateInitializer(writer);
		
		writer.println("\t}");
	}

}
