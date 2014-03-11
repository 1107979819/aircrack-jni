package org.tudelft.aircrack.frame.data.field;

import java.lang.reflect.AnnotatedElement;

import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecFactory;
import org.codehaus.preon.ResolverContext;

public class FrameBodyCodecFactory implements CodecFactory
{
	
	public <T> Codec<T> create(AnnotatedElement metadata, Class<T> type, ResolverContext context)
	{
		if (metadata != null && metadata.isAnnotationPresent(FrameBody.class))
		{
			return (Codec<T>) new FrameBodyCodec();
		} else
		{
			return null;
		}
	}

}
