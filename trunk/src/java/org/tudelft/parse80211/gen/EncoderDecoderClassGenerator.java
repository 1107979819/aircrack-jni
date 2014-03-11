package org.tudelft.parse80211.gen;

import java.util.ArrayList;

import javax.lang.model.element.TypeElement;

import org.tudelft.parse80211.types.ByteBuffer;

public class EncoderDecoderClassGenerator extends ClassGenerator
{

	protected ArrayList<TypeElement> frames = new ArrayList<TypeElement>();

	public EncoderDecoderClassGenerator(TypeElement classElement)
	{
		super(classElement);
		addInclude(ByteBuffer.class);
	}

	public void addFrameType(TypeElement frame)
	{
		frames.add(frame);
		addInclude(getClassName(frame));
	}

}
