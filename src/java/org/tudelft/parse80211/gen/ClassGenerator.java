package org.tudelft.parse80211.gen;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

public class ClassGenerator
{

	protected List<Generator> generators = new ArrayList<Generator>();
	protected HashSet<String> includes = new HashSet<String>();
	
	protected TypeElement classElement;
	
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
	
	public String getClassName(TypeElement classElement)
	{
		return getPackageName(classElement) + "." + classElement.getSimpleName();
	}
	
	public String getClassName()
	{
		return getPackageName() + "." + classElement.getSimpleName();
	}
	
	public String getSuperClass()
	{
		// Super class. Inherit from ByteBuffer if none given.
		String superClass = classElement.getSuperclass().toString();
		if (superClass.equals("java.lang.Object"))
			return null;
		else
			return superClass.replace(".template", "");
	}
	
	public void addGenerator(Generator generator)
	{
		generators.add(generator);
	}
	
	public void addInclude(Class<?> clazz)
	{
		includes.add(clazz.getCanonicalName());
	}
	
	public void addInclude(String canonicalName)
	{
		includes.add(canonicalName);
	}
	
	private void writeStart(PrintWriter writer)
	{
		writer.printf("package %s;\n", getPackageName());
		writer.println("");
		
		// Super class. Inherit from ByteBuffer if none given.
		String superClass = getSuperClass();
		
		// Collect imports from generators
		for (Generator generator : generators)
			if (generator.getInclude()!=null)
				includes.add(generator.getInclude());
		
		// Generate imports
		for (String include : includes)
			writer.printf("import %s;\n", include);

		// Import superclass
		if (superClass!=null)
			writer.printf("import %s;\n", superClass);

		// Blank line
		if (includes.size()>0 || superClass!=null)
			writer.println("");
		
		// Class declaration
		writer.printf("public class %s", classElement.getSimpleName());
		
		if (superClass!=null)	
			writer.printf(" extends %s", superClass.substring(superClass.lastIndexOf('.')+1));
		
		writer.println("");
		writer.println("{");
	}

	protected void writeFields(PrintWriter writer)
	{
		writer.println("");
		for (Generator generator : generators)
			generator.generateField(writer);
	}

	protected void writeConstructor(PrintWriter writer)
	{
	}
	
	protected void writeBody(PrintWriter writer)
	{
		writer.println("");
		for (Generator generator : generators)
			generator.generateMethods(writer);
	}
	
	private void writeEnd(PrintWriter writer)
	{
		writer.println("");
		writer.println("}");
	}

	public void generate(PrintWriter writer)
	{
		writeStart(writer);
		writeFields(writer);
		writeConstructor(writer);
		writeBody(writer);
		writeEnd(writer);
	}
}
