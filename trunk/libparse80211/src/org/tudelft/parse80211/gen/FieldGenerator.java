package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.Element;

public abstract class FieldGenerator implements Generator
{

	private final Element field;

	public FieldGenerator(Element field)
	{
		super();
		this.field = field;
	}
	
	public void generate(PrintWriter writer)
	{
		generateGetter(writer);
		writer.println();
		generateSetter(writer);
	}
	
	public String getFieldName()
	{
		return field.getSimpleName().toString();
	}

	private String getModifiedFieldName()
	{
		String ret = field.getSimpleName().toString();
		return ret.substring(0, 1).toUpperCase() + ret.substring(1);
	}

	protected void generateGetter(PrintWriter writer, String type, String ... body)
	{
		writer.printf("\tpublic %s get%s()\n", type, getModifiedFieldName());
		writer.println("\t{");
		for (String bodyStr : body)
			writer.printf("\t\t%s\n", bodyStr);
		writer.println("\t}");
	}
	
	protected void generateSetter(PrintWriter writer, String type, String ... body)
	{
		writer.printf("\tpublic void set%s(%s value)\n", getModifiedFieldName(), type);
		writer.println("\t{");
		for (String bodyStr : body)
			writer.printf("\t\t%s\n", bodyStr);
		writer.println("\t}");
	}
	
	public abstract void generateGetter(PrintWriter writer);
	
	public abstract void generateSetter(PrintWriter writer);

}
