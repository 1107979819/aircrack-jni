package org.tudelft.aircrack.frame;

import org.codehaus.preon.Codec;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.annotation.Bound;
import org.tudelft.aircrack.ReceiveInfo;
import org.tudelft.aircrack.frame.control.AckFrame;
import org.tudelft.aircrack.frame.control.BlockAckFrame;
import org.tudelft.aircrack.frame.control.BlockAckRequestFrame;
import org.tudelft.aircrack.frame.control.CfEndCfAckFrame;
import org.tudelft.aircrack.frame.control.CfEndFrame;
import org.tudelft.aircrack.frame.control.ControlFrame;
import org.tudelft.aircrack.frame.control.CtsFrame;
import org.tudelft.aircrack.frame.control.PsPollFrame;
import org.tudelft.aircrack.frame.control.RtsFrame;
import org.tudelft.aircrack.frame.data.DataFrame;
import org.tudelft.aircrack.frame.data.field.FrameBodyCodecFactory;
import org.tudelft.aircrack.frame.management.Beacon;
import org.tudelft.aircrack.frame.management.ManagementFrame;
import org.tudelft.aircrack.frame.management.ProbeRequest;
import org.tudelft.aircrack.frame.management.ProbeResponse;
import org.tudelft.aircrack.frame.management.field.InformationElementListCodecFactory;

public class Frame
{

	private ReceiveInfo receiveInfo;
	
	protected final static Codec<Frame> frameCodec = Codecs.create(Frame.class);
	protected final static Codec<FrameControl> frameControlCodec = Codecs.create(FrameControl.class);

	@Bound
	public FrameControl frameControl;
	
	public Frame()
	{
		frameControl = new FrameControl();
	}
	
	public static Frame decode(ReceiveInfo receiveInfo, byte[] buffer) throws DecodingException
	{
		// Decode packet header
		Frame frame = null;
		FrameControl frameControl = Codecs.decode(frameControlCodec, buffer);

		switch (frameControl.getType())
		{
			case Management:
				frame = decodeManagementFrame(frameControl, buffer);
				break;

			case Control:
				frame = decodeControlFrame(frameControl, buffer);
				break;

			case Data:
				frame = decodeDataFrame(frameControl, buffer);
				break;

		}

		if (frame==null)
			frame = Codecs.decode(frameCodec, buffer);

		frame.receiveInfo = receiveInfo;
		
		return frame;
	}

	private static Frame decodeManagementFrame(FrameControl frameControl, byte[] buffer) throws DecodingException
	{
		
		switch (frameControl.getSubType())
		{
			case Beacon:
				return Codecs.decode(Codecs.create(Beacon.class, new InformationElementListCodecFactory()), buffer);						
			case ProbeResponse:
				return Codecs.decode(Codecs.create(ProbeResponse.class, new InformationElementListCodecFactory()), buffer);						
			case ProbeRequest:
				return Codecs.decode(Codecs.create(ProbeRequest.class, new InformationElementListCodecFactory()), buffer);						
		}
		
		// The subtype hasn't been implemented yet, decode the part common to all management frames.
		Codec<ManagementFrame> managementFrameCodec = Codecs.create(ManagementFrame.class);
		return Codecs.decode(managementFrameCodec, buffer);						
	}
	
	private static Frame decodeControlFrame(FrameControl frameControl, byte[] buffer) throws DecodingException
	{
		switch (frameControl.getSubType())
		{
			case BlockAckRequest:
				Codec<BlockAckRequestFrame> blockAckRequestCodec = Codecs.create(BlockAckRequestFrame.class);
				return Codecs.decode(blockAckRequestCodec, buffer);
			case BlockAck:
				Codec<BlockAckFrame> blockAckCodec = Codecs.create(BlockAckFrame.class);
				return Codecs.decode(blockAckCodec, buffer);
			case PsPoll:
				Codec<PsPollFrame> psPollCodec = Codecs.create(PsPollFrame.class);
				return Codecs.decode(psPollCodec, buffer);
			case RTS:
				Codec<RtsFrame> rtsCodec = Codecs.create(RtsFrame.class);
				return Codecs.decode(rtsCodec, buffer);
			case CTS:
				Codec<CtsFrame> ctsCodec = Codecs.create(CtsFrame.class);
				return Codecs.decode(ctsCodec, buffer);
			case ACK:
				Codec<AckFrame> ackCodec = Codecs.create(AckFrame.class);
				return Codecs.decode(ackCodec, buffer);
			case CfEnd:
				Codec<CfEndFrame> cfEndCodec = Codecs.create(CfEndFrame.class);
				return Codecs.decode(cfEndCodec, buffer);
			case CfEnd_CfAck:
				Codec<CfEndCfAckFrame> cfEndAckCodec = Codecs.create(CfEndCfAckFrame.class);
				return Codecs.decode(cfEndAckCodec, buffer);
		}
		
		// The subtype hasn't been implemented yet, decode the part common to all management frames.
		Codec<ControlFrame> controlFrameCodec = Codecs.create(ControlFrame.class);
		return Codecs.decode(controlFrameCodec, buffer);						
	}
	
	private static Frame decodeDataFrame(FrameControl frameControl, byte[] buffer) throws DecodingException
	{
		// TODO all data frames are decoded into a single DataFrame class
		try {
			return Codecs.decode(Codecs.create(DataFrame.class, new FrameBodyCodecFactory()), buffer);
		} catch (Throwable t)
		{
			System.out.println(frameControl);
			t.printStackTrace();
			System.exit(-1);
			return null;
		}
	}

	
	public FrameControl getFrameControl()
	{
		return frameControl;
	}
	
	public ReceiveInfo getReceiveInfo()
	{
		return receiveInfo;
	}
	
	@Override
	public String toString()
	{
		return frameControl.toString();
	}

}
