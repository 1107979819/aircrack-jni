package org.tudelft.aircrack.frame;

import org.tudelft.aircrack.frame.management.BeaconFrame;
import org.tudelft.aircrack.frame.management.ManagementFrame;

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
				switch (frameControl.getSubType())
				{
					case Beacon:
						Codec<BeaconFrame> beaconFrameCodec = Codecs.create(BeaconFrame.class);
						return Codecs.decode(beaconFrameCodec, buffer);
						
					default: 
						Codec<ManagementFrame> managementFrameCodec = Codecs.create(ManagementFrame.class);
						return Codecs.decode(managementFrameCodec, buffer);		
				}

			case Control:

			case Data:

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
