package org.tudelft.parse80211.types;

public class InformationElement extends BufferBacked
{

	public InformationElement(ByteBuffer buffer, int offset)
	{
		super(buffer, offset);
	}

	public InformationElementId getId()
	{
		return InformationElementId.fromCode(getIdCode());
	}

	public int getIdCode()
	{
		return buffer.data[offset] & 0xff;
	}
	
	public int getSize()
	{
		return buffer.data[offset+1] & 0xff;
	}
	
	public int getPayloadByte(int payloadOffset)
	{
		if (payloadOffset<0 || payloadOffset>getSize())
			throw new IndexOutOfBoundsException();
		
		return buffer.data[offset+2+payloadOffset] & 0xff;
	}
	
	@Override
	public String toString()
	{
		return String.format("IE[%d,%d,%s]",
				getIdCode(),
				getSize(),
				getId()
				);
	}
	
}

