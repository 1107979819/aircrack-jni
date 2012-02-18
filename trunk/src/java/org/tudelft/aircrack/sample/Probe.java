package org.tudelft.aircrack.sample;

import java.io.IOException;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.Interface;
import org.tudelft.aircrack.JniInterface;
import org.tudelft.aircrack.TransmitInfo;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.management.ProbeRequest;
import org.tudelft.aircrack.frame.management.ProbeResponse;
import org.tudelft.aircrack.frame.management.field.ElementId;
import org.tudelft.aircrack.frame.management.field.InformationElement;

public class Probe
{
	
	// Wait time in milliseconds
	private final static long waitTime = 120;
	
	public static void probeChannel(Interface iface, int channel) throws IOException, DecodingException
	{
		
		long time = System.currentTimeMillis();
		iface.setChannel(channel);
		System.out.println("Change channel time: " + (System.currentTimeMillis() - time));
		
		System.out.println("Scanning on channel: " + iface.getChannel() + " " + channel);
		
		// construct probe request
		ProbeRequest probeRequest = new ProbeRequest();
		probeRequest.setAddress1(Address.Broadcast);
		probeRequest.setSA(iface.getMac());
		probeRequest.setBSSID(Address.Broadcast);
		probeRequest.setDuration((int)(waitTime * 10));
		probeRequest.getElements().addElement(new InformationElement(ElementId.SSID, ""));
		
		// Send probe request
		TransmitInfo transmitInfo = new TransmitInfo();
		byte rawFrame[] = Frame.encode(probeRequest);
		
		iface.write(rawFrame, transmitInfo);
		Thread.yield();
		iface.write(rawFrame, transmitInfo);
		
		// Collect probe responses
		long startTime = System.currentTimeMillis();
		
		while (System.currentTimeMillis()-startTime < waitTime)
		{
			Frame frame = iface.receive();
			
			if (frame instanceof ProbeResponse)
			{
				ProbeResponse response = (ProbeResponse)frame;
				System.out.printf("\t%s %d %d %d %s\n",
						response.getBSSID().toString(),
						(response.getReceiveInfo().getChannel() - 103) / 5,
						response.getReceiveInfo().getNoise() + 1,
						response.getReceiveInfo().getPower(),
						response.getSsid()
						);
			}
			
		}
		
	}
	

	public static void main(String args[]) throws IOException, DecodingException
	{
		JniInterface iface = new JniInterface("mon0");
		iface.open();

		long startTime = System.currentTimeMillis();
		
//		for (int i=1; i<=13; i++)
//			probeChannel(iface, i);
		
		probeChannel(iface, 6);
		probeChannel(iface, 9);
		probeChannel(iface, 11);

		System.out.println("Total scan time: " + (System.currentTimeMillis() - startTime));
		
		iface.close();
	}	

}
