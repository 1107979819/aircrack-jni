package org.tudelft.parse80211;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.ReceiveInfo;
import org.tudelft.aircrack.socket.LinuxSocketInterface;
import org.tudelft.aircrack.socket.SocketInterface;
import org.tudelft.parse80211.frames.Decoder;
import org.tudelft.parse80211.frames.Frame;
import org.tudelft.parse80211.frames.management.Beacon;

public class Mon
{
	
	public static void main(String[] args) throws DecodingException
	{
		
		SocketInterface sock = new LinuxSocketInterface("mon1", "@aircrack");
		sock.open();
		
		// sock.setChannel(11);
		
		byte[] data = new byte[4096];
		ReceiveInfo receiveInfo = new ReceiveInfo();

		// org.tudelft.parse80211.frames.Frame frame = new org.tudelft.parse80211.frames.Frame(data);
		
		Decoder decoder = new Decoder(data);
		
		while (true)
		{
			int rx = sock.read(data, receiveInfo);
			
			if (rx>0)
			{
				Frame frame = decoder.decode();
				System.out.printf("%d %2d %s\n", frame.getType(), frame.getSubType(), frame.getClass().toString());
				
				if (frame instanceof Beacon)
				{
					Beacon beacon = (Beacon)frame;
					System.out.println(beacon.getBeaconInterval());
				}
				
			}
			
		}
		
	}

}
