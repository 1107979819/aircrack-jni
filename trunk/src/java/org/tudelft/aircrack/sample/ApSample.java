package org.tudelft.aircrack.sample;

import java.io.IOException;
import java.util.Collection;

import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.JniInterface;
import org.tudelft.aircrack.TransmitInfo;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.management.ProbeRequest;
import org.tudelft.aircrack.frame.management.ProbeResponse;
import org.tudelft.aircrack.frame.management.field.ElementId;
import org.tudelft.aircrack.frame.management.field.InformationElement;
import org.tudelft.aircrack.frame.management.field.InformationElementListCodecFactory;

public class ApSample
{
	
	
	
	public void waitForStations(Collection<Address> stations)
	{
		
	}

	public static void main(String args[]) throws IOException, DecodingException
	{
		JniInterface iface = new JniInterface("mon0");
		iface.open();
		
		iface.setChannel(11);
		System.out.println("Channel: " + iface.getChannel());
		
		Address myMac = new Address("7c:61:93:2b:a9:c9");
//		Address myMac = new Address("01:02:03:04:05:06");

		// Send probe request
		ProbeRequest probeRequest = new ProbeRequest();
		probeRequest.setAddress1(Address.Broadcast);
		probeRequest.setSA(myMac);
		probeRequest.setBSSID(Address.Broadcast);
		probeRequest.setDuration(100);
		probeRequest.getElements().addElement(new InformationElement(ElementId.SSID, "NaoNet"));
		
		// Transmit probe request
		TransmitInfo transmitInfo = new TransmitInfo();
		byte rawFrame[] = Codecs.encode(probeRequest, Codecs.create(ProbeRequest.class, new InformationElementListCodecFactory()));

		iface.write(rawFrame, transmitInfo);
		
		// Wait for responses
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
						System.out.println(frame);
						System.out.println(((ProbeResponse) frame).getSequenceControl());
//						System.out.printf(
//								"%s | %s | %4d | %4d dBm | %s \n",
//								((ProbeResponse) frame).getAddress1().toString(),
//								((ProbeResponse) frame).getBSSID().toString(),
//								frame.getReceiveInfo().getChannel(),
//								frame.getReceiveInfo().getPower(),
//								((ProbeResponse) frame).getSsid()
//								);
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
