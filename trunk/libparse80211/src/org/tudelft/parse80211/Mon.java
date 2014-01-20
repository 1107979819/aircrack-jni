package org.tudelft.parse80211;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.ReceiveInfo;
import org.tudelft.aircrack.socket.LinuxSocketInterface;
import org.tudelft.aircrack.socket.SocketInterface;
import org.tudelft.parse80211.frames.Decoder;
import org.tudelft.parse80211.frames.Frame;
import org.tudelft.parse80211.frames.management.ProbeRequest;
import org.tudelft.parse80211.types.ByteBuffer;

public class Mon
{
	
	public static void main(String[] args) throws DecodingException
	{
		
		SocketInterface sock = new LinuxSocketInterface("mon0", "@aircrack");
		sock.open();
		
		// sock.setChannel(11);
		
		byte[] data = new byte[4096];
		ByteBuffer buffer = new ByteBuffer(data);
		ReceiveInfo receiveInfo = new ReceiveInfo();

		// org.tudelft.parse80211.frames.Frame frame = new org.tudelft.parse80211.frames.Frame(data);
		
		Decoder decoder = new Decoder(buffer);
		
		while (true)
		{
			int rx = sock.read(data, receiveInfo);
			buffer.size = rx;
			
			if (rx>0)
			{
				Frame frame = decoder.decode(rx);
				
				// System.out.printf("%d %2d %s\n", frame.getType(), frame.getSubType(), frame.getClass().toString());
				
				if (frame instanceof ProbeRequest)
				{
					ProbeRequest beacon = (ProbeRequest)frame;
					
					System.out.printf("%d %2d %s\n", frame.getType(), frame.getSubType(), frame.getClass().toString());
					System.out.println(beacon.getInformationList().getSSID());

					for (int i=0; i<beacon.getInformationList().count(); i++)
						System.out.println("\t" + beacon.getInformationList().get(i));

				}
				
			}
			
		}
		
	}

}
