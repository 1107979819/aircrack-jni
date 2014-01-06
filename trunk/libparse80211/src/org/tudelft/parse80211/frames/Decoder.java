package org.tudelft.parse80211.frames;

import org.tudelft.parse80211.types.ByteBuffer;

public class Decoder extends ByteBuffer
{

	private Frame[] frames = new Frame[64];

	public Decoder(byte[] data)
	{
		super(data);
		
		Frame frame = new Frame(data);
		for (int i=0; i<64; i++)
			frames[i] = frame;
		
		frames[49] = new org.tudelft.parse80211.frames.control.Cts(data);
		frames[45] = new org.tudelft.parse80211.frames.control.Rts(data);
		frames[32] = new org.tudelft.parse80211.frames.management.Beacon(data);
	}


	public Frame decode()
	{
		return frames[(data[offset]&0xff)>>2];
	}

}
