package org.tudelft.parse80211.gen;

import java.io.PrintWriter;

public interface Generator
{

	public String getInclude();
	
	public void generateField(PrintWriter writer);

	public void generateInitializer(PrintWriter writer);
	
	public void generateMethods(PrintWriter writer);

}
