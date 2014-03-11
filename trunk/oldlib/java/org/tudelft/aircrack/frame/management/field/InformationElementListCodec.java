package org.tudelft.aircrack.frame.management.field;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.preon.Builder;
import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDescriptor;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.Resolver;
import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.channel.BitChannel;
import org.codehaus.preon.el.Expression;

public class InformationElementListCodec implements Codec<Object>
{
	
	private final static Codec<InformationElement> codec = Codecs.create(InformationElement.class);

    @Override
	public Object decode(BitBuffer bitBuffer, Resolver resolver, Builder builder) throws DecodingException
	{
		ArrayList<Object> ret = new ArrayList<Object>();
		
		while (bitBuffer.getBitPos()<bitBuffer.getBitBufBitSize())
		{
			// read element
            ret.add(codec.decode(bitBuffer, resolver, builder));
		}
		
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void encode(Object list, BitChannel channel, Resolver resolver) throws IOException
	{
		for (InformationElement element : (List<InformationElement>)list)
            codec.encode(element, channel, resolver);
	}

	@Override
	public CodecDescriptor getCodecDescriptor()
	{
		// TODO
		return new org.codehaus.preon.descriptor.NullCodecDescriptor2();
	}

	@Override
	public Class<?> getType()
	{
		return InformationElement.class;
	}

	@Override
	public Class<?>[] getTypes()
	{
        return codec.getTypes();
	}

	@Override
	public Expression<Integer, Resolver> getSize()
	{
		return null;
	}

}
