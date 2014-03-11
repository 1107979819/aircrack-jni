package org.tudelft.parse80211.processors;

import javax.annotation.processing.AbstractProcessor;
import javax.tools.Diagnostic.Kind;

/**
 * 
 * Base class for processors that adds a convenience method for outputting 'note' messages to the
 * compilation process.
 * 
 * @author Niels Brouwers
 *
 */
public abstract class Processor extends AbstractProcessor
{

	protected void note(Object ... msg)
	{
		StringBuilder builder = new StringBuilder();
		
		for (Object obj : msg)
		{
			builder.append(obj.toString());
			builder.append(" ");
		}
		
		this.processingEnv.getMessager().printMessage(Kind.NOTE, builder.toString());
	}

}
