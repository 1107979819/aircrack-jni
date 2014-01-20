package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.TypeElement;

import org.tudelft.parse80211.types.BufferBacked;
import org.tudelft.parse80211.types.ByteBuffer;

public class FrameClassGenerator extends ClassGenerator
{

	protected final static Class<?> baseClass = BufferBacked.class;

	public FrameClassGenerator(TypeElement classElement)
	{
		super(classElement);
		addInclude(ByteBuffer.class);
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
		writer.printf("\tpublic %s(ByteBuffer buffer)\n", classElement.getSimpleName());
		writer.println("\t{");
		writer.println("\t\tsuper(buffer);");
		
		writer.println("");
		for (Generator generator : generators)
			generator.generateInitializer(writer);
		
		writer.println("\t}");
	}

}
