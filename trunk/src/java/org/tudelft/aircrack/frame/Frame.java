package org.tudelft.aircrack.frame;

import java.io.IOException;
import java.util.HashMap;

import org.codehaus.preon.Codec;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.Order;
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
import org.tudelft.aircrack.frame.visitor.FrameVisitor;
import org.tudelft.aircrack.frame.visitor.Visitable;

public class Frame implements Visitable
{

	private ReceiveInfo receiveInfo;
	
	private final static HashMap<Class<?>, Codec<?>> codecCache = new HashMap<Class<?>, Codec<?>>(); 
	
	protected final static Codec<Frame> frameCodec = Codecs.create(Frame.class);
	protected final static Codec<FrameControl> frameControlCodec = Codecs.create(FrameControl.class);

	@Bound
	@Order(1) public FrameControl frameControl;
	
	// This is where the magic happens
	@SuppressWarnings("serial")
	private final static HashMap<SubType, Class<?>> frameTypeMap = new HashMap<SubType, Class<?>>() {{
	
		// Management frames
		put(SubType.Beacon, Beacon.class);
		put(SubType.ProbeRequest, ProbeRequest.class);
		put(SubType.ProbeResponse, ProbeResponse.class);
		
		// Control frames
		put(SubType.BlockAckRequest, BlockAckRequestFrame.class);
		put(SubType.BlockAck, BlockAckFrame.class);
		put(SubType.PsPoll, PsPollFrame.class);
		put(SubType.RTS, RtsFrame.class);
		put(SubType.CTS, CtsFrame.class);
		put(SubType.ACK, AckFrame.class);
		put(SubType.CfEnd, CfEndFrame.class);
		put(SubType.CfEnd_CfAck, CfEndCfAckFrame.class);
		
	}}; 
	
	public Frame()
	{
		frameControl = new FrameControl();
	}
	
	@SuppressWarnings("unchecked")
	private final static <T> Codec<T> getCodec(SubType type)
	{
		// Determine the class to decode the frame subtype into
		Class<?> cls = frameTypeMap.get(type);
		
		// If the given subtype does not appear in the frameTypeMap, there is no specific Java 
		// class to represent that frame type (yet). Instead, just decode it into a generic management/frame/data
		// frame.
		if (cls==null)
		{
			if (type.getType()==FrameType.Management) cls = ManagementFrame.class;
			if (type.getType()==FrameType.Control) cls = ControlFrame.class;
			if (type.getType()==FrameType.Data) cls = DataFrame.class;
		}

		// Instantiate the appropriate codec lazily
		if (!codecCache.containsKey(cls))
			codecCache.put(cls, Codecs.create(cls, new FrameBodyCodecFactory(), new InformationElementListCodecFactory()));
		
		// Return the codec
		return (Codec<T>)codecCache.get(cls);
	}
	
	public static Frame decode(ReceiveInfo receiveInfo, byte[] buffer) throws DecodingException
	{
		// Decode packet header
		Frame frame = null;
		FrameControl frameControl = Codecs.decode(frameControlCodec, buffer);
		
		// Get the appropriate codec for this frame type
		Codec<Frame> codec = getCodec(frameControl.getSubType());
		
		// Decode and return
		frame = Codecs.decode(codec, buffer);

		frame.receiveInfo = receiveInfo;
		
		return frame;
	}
	
	public static byte[] encode(Frame frame) throws IOException
	{
		
		Codec<Frame> codec = getCodec(frame.getFrameControl().getSubType());
		
		return Codecs.encode(frame, codec);		
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

	@Override
	public void accept(FrameVisitor visitor)
	{
		visitor.visit(this);
	}
	
}
