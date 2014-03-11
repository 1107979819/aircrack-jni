package org.tudelft.parse80211;

import org.tudelft.parse80211.frames.control.Control;
import org.tudelft.parse80211.types.ByteBuffer;


public class Generate
{
	
	public static void main(String[] args) throws Exception
	{
		byte[] data = {
				(byte)0x50, (byte)0x08, (byte)0x3a, (byte)0x01, (byte)0x00, (byte)0x20, (byte)0xa6, (byte)0x4c, (byte)0x3b, (byte)0x87, (byte)0x00, (byte)0x0f, (byte)0xf8, (byte)0x58, (byte)0x40, (byte)0xcb,
				(byte)0x00, (byte)0x0f, (byte)0xf8, (byte)0x58, (byte)0x40, (byte)0xcb, (byte)0xf0, (byte)0x16, (byte)0x0d, (byte)0xab, (byte)0x9f, (byte)0x1e, (byte)0x2e, (byte)0x00, (byte)0x00, (byte)0x00,
				(byte)0x64, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x0b, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x01, (byte)0x04, (byte)0x82, (byte)0x84, (byte)0x8b, (byte)0x96, (byte)0x03, (byte)0x01, (byte)0x0b, (byte)0xdd, (byte)0x06, (byte)0x00, (byte)0x40, (byte)0x96, (byte)0x01,
				(byte)0x01, (byte)0x00, (byte)0xdd, (byte)0x05, (byte)0x00, (byte)0x40, (byte)0x96, (byte)0x03, (byte)0x02, (byte)0xdd, (byte)0x16, (byte)0x00, (byte)0x40, (byte)0x96, (byte)0x04, (byte)0x00,
				(byte)0x03, (byte)0x06, (byte)0xa5, (byte)0x00, (byte)0x00, (byte)0x22, (byte)0xa5, (byte)0x00, (byte)0x00, (byte)0x41, (byte)0x54, (byte)0x00, (byte)0x00, (byte)0x61, (byte)0x43, (byte)0x00,
				(byte)0x00
				};
		
		Control frame = new Control(new ByteBuffer(data));
		System.out.println(frame.getProtocolVersion());
		System.out.println(frame.getType());
		System.out.println(frame.getSubType());
		System.out.println(frame.getFrameType());
		System.out.println(frame.getDuration());
		
	}

}