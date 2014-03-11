package org.tudelft.aircrack.frame.management.field;

import java.lang.reflect.AnnotatedElement;

import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecFactory;
import org.codehaus.preon.ResolverContext;

public class InformationElementListCodecFactory implements CodecFactory
{
	
	public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type, ResolverContext context)
	{
		if (metadata != null && metadata.isAnnotationPresent(InformationElementList.class))
		{
			return (Codec<T>) new InformationElementListCodec();
		} else
		{
			return null;
		}
	}

}
