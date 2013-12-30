package org.tudelft.parse80211;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.ReceiveInfo;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.socket.LinuxSocketInterface;
import org.tudelft.aircrack.socket.SocketInterface;

public class Mon
{
	
	public static void main(String[] args) throws DecodingException
	{
		
		SocketInterface sock = new LinuxSocketInterface("mon0", "@aircrack");
		sock.open();
		
		byte[] data = new byte[4096];
		ReceiveInfo receiveInfo = new ReceiveInfo();

		org.tudelft.parse80211.frames.Frame frame = new org.tudelft.parse80211.frames.Frame(data);
		
		while (true)
		{
			int rx = sock.read(data, receiveInfo);
			
			if (rx>0)
				System.out.println(frame.getType() + " " + frame.getSubType());
			
		}
		
	}

}
