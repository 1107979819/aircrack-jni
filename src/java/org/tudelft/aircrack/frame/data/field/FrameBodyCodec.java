package org.tudelft.aircrack.frame.data.field;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.codehaus.preon.Builder;
import org.codehaus.preon.Codec;
import org.codehaus.preon.CodecDescriptor;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.Resolver;
import org.codehaus.preon.buffer.BitBuffer;
import org.codehaus.preon.channel.BitChannel;
import org.codehaus.preon.el.Expression;

public class FrameBodyCodec implements Codec<Object>
{
	
    @Override
	public Object decode(BitBuffer bitBuffer, Resolver resolver, Builder builder) throws DecodingException
	{
		long bits = bitBuffer.getBitBufBitSize() - bitBuffer.getBitPos();

		ByteBuffer buf = bitBuffer.readAsByteBuffer((int)Math.ceil(bits/8.0));
		return buf.array();
	}

	@Override
	public void encode(Object body, BitChannel channel, Resolver resolver) throws IOException
	{
		if (body!=null)
		{
			byte[] buf = (byte[])body;
			channel.write(buf, 0, buf.length);
		}
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
		return byte[].class;
	}

	@Override
	public Class<?>[] getTypes()
	{
        return new Class<?>[]{ byte[].class };
	}

	@Override
	public Expression<Integer, Resolver> getSize()
	{
		return null;
	}

}
