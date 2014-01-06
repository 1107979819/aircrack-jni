package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

import javax.lang.model.element.Element;

import org.tudelft.parse80211.annotations.Mapped;

public class MappedFieldGenerator extends SimpleFieldGenerator
{

	private final Mapped mapped;
	private final Element type;
	
	public MappedFieldGenerator(Element field, Mapped mapped, Element type)
	{
		super(field);
		this.mapped = mapped;
		this.type = type;
	}
	
	private String getTypeName()
	{
		return type.toString();
	}

	private String getSimpleTypeName()
	{
		String ret = getTypeName();
		return ret.substring(ret.lastIndexOf(".") + 1);
	}
	
	@Override
	public String getInclude()
	{
		return getTypeName();
	}
	
	
	@Override
	public void generateField(PrintWriter writer)
	{
		writer.printf("\tprivate %s %s;\n", getSimpleTypeName(), field.getSimpleName());
	}
	
	@Override
	public void generateInitializer(PrintWriter writer)
	{
		writer.printf("\t\t%s = new %s(data, %d);\n",
				field.getSimpleName(),
				getSimpleTypeName(),
				mapped.offset()
				);
	}

	@Override
	public void generateGetter(PrintWriter writer)
	{
		generateGetter(writer, getSimpleTypeName(), String.format("return %s;", field.getSimpleName()));
	}

	@Override
	public void generateSetter(PrintWriter writer)
	{
		generateSetter(writer, getSimpleTypeName(), "");
	}
	
}
