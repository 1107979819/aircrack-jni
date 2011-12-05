package org.tudelft.aircrack.frame;

import nl.flotsam.preon.Codec;
import nl.flotsam.preon.Codecs;
import nl.flotsam.preon.DecodingException;
import nl.flotsam.preon.annotation.Bound;

public class Frame
{
	
	protected final static Codec<Frame> frameCodec = Codecs.create(Frame.class);
	protected final static Codec<FrameControl> frameControlCodec = Codecs.create(FrameControl.class);
	
	@Bound
	FrameControl frameControl;
	
	public static Frame decode(byte[] buffer) throws DecodingException
	{
		
		// Decode packet header
		FrameControl frameControl = Codecs.decode(frameControlCodec, buffer);
		
		switch (frameControl.getType())
		{
			case Management:
				Codec<ManagementFrame> managementFrameCodec = Codecs.create(ManagementFrame.class);
				return Codecs.decode(managementFrameCodec, buffer);						
		}
		
//		switch (frameControl.getSubType())
//		{
//			case Beacon:
//				return Beacon.decode(buffer);		
//		}			
		
		return Codecs.decode(frameCodec, buffer);						
	}
	
	public FrameControl getFrameControl()
	{
		return frameControl;
	}

}
