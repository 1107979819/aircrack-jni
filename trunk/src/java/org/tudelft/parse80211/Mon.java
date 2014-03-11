package org.tudelft.parse80211;

import org.tudelft.parse80211.frames.Frame;
import org.tudelft.parse80211.socket.LinuxSocketInterface;
import org.tudelft.parse80211.socket.SocketInterface;

public class Mon
{
	
	public static void main(String[] args)
	{
		
		SocketInterface sock = new LinuxSocketInterface("mon0", "@aircrack");
		sock.open();
		
		System.out.printf("Channel: %d\n", sock.getChannel());
		System.out.printf("Frequency: %d\n", sock.getFrequency());
		System.out.printf("Mac: %s\n", sock.getMac().toString());
		
		while (true)
		{
			Frame frame = sock.receive();
			
			if (frame != null)
			{
				System.out.println(frame);
				System.out.println(sock.getReceiveInfo().getPower());
			}
		}
		
		// sock.close();
		
		
	}

}
