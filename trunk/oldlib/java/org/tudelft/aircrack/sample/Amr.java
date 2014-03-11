package org.tudelft.aircrack.sample;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.JniInterface;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.management.ProbeRequest;

public class Amr
{
	
	public static void main(String[] args) throws DecodingException
	{
		
		JniInterface iface = new JniInterface("mon0");
		iface.open();

		while (true)
		{
			
			Frame frame = iface.receive();
			if (frame==null)
				continue;
			
			if (frame instanceof ProbeRequest)
			{
				ProbeRequest request = (ProbeRequest)frame;
				
				if (request.getSsid().startsWith("HHID"))
					System.out.println(frame);
			}
			
		}		
		
		// iface.close();
		
	}

}
