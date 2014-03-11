package org.tudelft.aircrack;

public class WifiPacket
{
	
	private FrameControl frameControl;
	
	public static WifiPacket decode(byte[] buf)
	{
		if (buf.length<24)
			return null;
		
		WifiPacket packet = new WifiPacket();
		
		packet.frameControl = FrameControl.decode((buf[0]&0xff) + ((buf[1]&0xff) << 8));
		
		return packet;
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
