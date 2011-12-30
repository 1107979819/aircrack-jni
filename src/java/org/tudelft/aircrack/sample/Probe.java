package org.tudelft.aircrack.sample;

import java.io.IOException;

import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.Interface;
import org.tudelft.aircrack.TransmitInfo;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.management.ProbeRequest;
import org.tudelft.aircrack.frame.management.ProbeResponse;
import org.tudelft.aircrack.frame.management.field.ElementId;
import org.tudelft.aircrack.frame.management.field.InformationElement;
import org.tudelft.aircrack.frame.management.field.InformationElementListCodecFactory;

public class Probe
{

	public static void main(String args[]) throws IOException, DecodingException
	{
		Interface iface = new Interface("mon0");
		iface.open();

		Address myMac = new Address(new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 });

		// Send probe request
		ProbeRequest probeRequest = new ProbeRequest();
		probeRequest.setAddress1(Address.Broadcast);
		probeRequest.setSA(myMac);
		probeRequest.setBSSID(Address.Broadcast);
		probeRequest.setDuration(1 * 1000);
		probeRequest.getElements().addElement(new InformationElement(ElementId.SSID, ""));
		
		// Transmit probe request
		TransmitInfo transmitInfo = new TransmitInfo();
		byte rawFrame[] = Codecs.encode(probeRequest, Codecs.create(ProbeRequest.class, new InformationElementListCodecFactory()));
		
		iface.write(rawFrame, transmitInfo);
		
		// Wait for responses during 10 seconds
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis()-time < 10000)
		{
			
			try
			{
				Frame frame = iface.receive();
				if (frame==null)
					continue;
				
				if (frame instanceof ProbeResponse)
				{
					ProbeResponse response = (ProbeResponse)frame;
					
					if (response.getAddress1().compareTo(myMac)==0)
					{
						System.out.printf(
								"%s | %4d dBm | %s \n",
								((ProbeResponse) frame).getBSSID().toString(),
								frame.getReceiveInfo().getPower(),
								((ProbeResponse) frame).getSsid()
								);
					}
				}
				
			} catch (DecodingException ex)
			{
				// skip
			}
			
			
		}
		
		iface.close();
	}	

}
