package org.tudelft.parse80211.gen;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import org.tudelft.parse80211.types.ByteBuffer;

public class ClassGenerator implements Generator
{

	private final static Class<?> baseClass = ByteBuffer.class;
	
	private List<Generator> generators = new ArrayList<>();
	private List<Class<?>> includes = new ArrayList<>();
	
	private TypeElement classElement;
	
	public ClassGenerator(TypeElement classElement)
	{
		this.classElement = classElement;
	}
	
	private static String getPackageName(TypeElement element)
	{
		PackageElement packageElement = (PackageElement)element.getEnclosingElement();
		String packageName = packageElement.getQualifiedName().toString();
		return packageName.replaceAll("template.", "");
	}
	
	public String getPackageName()
	{
		return getPackageName(classElement);
	}
	
	public String getClassName()
	{
		return getPackageName() + "." + classElement.getSimpleName();
	}
	
	public void addGenerator(Generator generator)
	{
		generators.add(generator);
	}
	
	public void addInclude(Class<?> clazz)
	{
		includes.add(clazz);
	}
	
	private void writeStart(PrintWriter writer)
	{
		writer.printf("package %s;\n", getPackageName());
		writer.println("");
		
		// Super class. Inherit from ByteBuffer if none given.
		String superClass = classElement.getSuperclass().toString();
		if (superClass.equals("java.lang.Object"))
			superClass = baseClass.getCanonicalName();
		else
			superClass = superClass.replace(".template", "");
		
		// Generate imports
		for (Class<?> include : includes)
			writer.printf("import %s;\n", include.getCanonicalName());
		
		// Import superclass
		writer.printf("import %s;\n", superClass);
		
		writer.println("");
		writer.printf("public class %s extends %s\n",
				classElement.getSimpleName(),
				superClass.substring(superClass.lastIndexOf('.')+1)
				);
		writer.println("{");
	}

	private void writeConstructor(PrintWriter writer)
	{
		writer.println("");
		writer.printf("\tpublic %s(byte[] data)\n", classElement.getSimpleName());
		writer.println("\t{");
		writer.println("\t\tsuper(data);");
		writer.println("\t}");
	}
	
	private void writeBody(PrintWriter writer)
	{
		writer.println("");
		for (Generator generator : generators)
			generator.generate(writer);
	}
	
	private void writeEnd(PrintWriter writer)
	{
		writer.println("");
		writer.println("}");
	}

	@Override
	public void generate(PrintWriter writer)
	{
		writeStart(writer);
		writeConstructor(writer);
		writeBody(writer);
		writeEnd(writer);
	}
}
