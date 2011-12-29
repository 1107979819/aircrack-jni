package org.tudelft.aircrack.sample;

import java.io.IOException;

import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.Interface;
import org.tudelft.aircrack.TransmitInfo;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.Util;
import org.tudelft.aircrack.frame.management.ProbeRequest;
import org.tudelft.aircrack.frame.management.ProbeResponse;
import org.tudelft.aircrack.frame.management.field.InformationElementListCodecFactory;

public class Probe
{

	public static void main(String args[]) throws IOException
	{
		Interface iface = new Interface("mon0");
		iface.open();

//		ManagementFrame frame = new ManagementFrame();
//		Codec<ManagementFrame> codec = Codecs.create(ManagementFrame.class/*, new InformationElementListCodecFactory()*/);
//		byte rawFrame[] = Codecs.encode(frame, codec);
//		
//		Util.printByteBuffer(rawFrame);

		// Send probe request
		ProbeRequest probeRequest = new ProbeRequest();
		probeRequest.setAddress1(Address.Broadcast);
		probeRequest.setSA(iface.getMac());
		probeRequest.setBSSID(Address.Broadcast);
		probeRequest.setDuration(1 * 1000);
		
		// Transmit probe request
		TransmitInfo transmitInfo = new TransmitInfo();
		byte rawFrame[] = Codecs.encode(probeRequest, Codecs.create(ProbeRequest.class, new InformationElementListCodecFactory()));

		Util.printByteBuffer(rawFrame);
		
		System.out.println(iface.write(rawFrame, transmitInfo));
		System.out.println(iface.write(rawFrame, transmitInfo));
		System.out.println(iface.write(rawFrame, transmitInfo));
		System.out.println(iface.write(rawFrame, transmitInfo));

		Util.printByteBuffer(rawFrame);
		
		// Wait for responses during 10 seconds
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis()-time < 10000)
		{
			
			try
			{
				Frame frame = iface.receive();
				
				if (frame instanceof ProbeResponse)
				{
					ProbeResponse response = (ProbeResponse)frame;
					
					if (response.getAddress1().compareTo(iface.getMac())==0)
						System.out.println(frame);
					else
						System.out.println(response.getAddress1());
				}
				
			} catch (DecodingException ex)
			{
				// skip
			}
			
			
		}
		
		iface.close();
	}	

}
