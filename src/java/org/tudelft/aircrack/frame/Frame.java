package org.tudelft.aircrack.frame;

import nl.flotsam.preon.Codec;
import nl.flotsam.preon.Codecs;
import nl.flotsam.preon.DecodingException;
import nl.flotsam.preon.annotation.Bound;

import org.tudelft.aircrack.frame.management.ManagementFrame;
import org.tudelft.aircrack.frame.management.Beacon;

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
				return decodeManagementFrame(frameControl, buffer);				

			case Control:

			case Data:

		}

		return Codecs.decode(frameCodec, buffer);						
	}

	private static Frame decodeManagementFrame(FrameControl frameControl, byte[] buffer) throws DecodingException
	{
		switch (frameControl.getSubType())
		{
			case Beacon:
				Codec<Beacon> beaconCodec = Codecs.create(Beacon.class);
				return Codecs.decode(beaconCodec, buffer);						
		}
		
		// The subtype hasn't been implemented yet, decode the part common to all management frames.
		Codec<ManagementFrame> managementFrameCodec = Codecs.create(ManagementFrame.class);
		return Codecs.decode(managementFrameCodec, buffer);						
	}
	
	public FrameControl getFrameControl()
	{
		return frameControl;
	}
	
	@Override
	public String toString()
	{
		return frameControl.toString();
	}

}
