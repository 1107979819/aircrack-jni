package org.tudelft.parse80211;

import java.io.UnsupportedEncodingException;

import org.tudelft.parse80211.frames.Encoder;
import org.tudelft.parse80211.frames.management.ProbeRequest;
import org.tudelft.parse80211.types.ByteBuffer;
import org.tudelft.parse80211.types.InformationElementId;


public class BeaconFrame
{
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		Encoder encoder = new Encoder(new ByteBuffer(new byte[4096]), 0);

		// Initialise a new 802.11 beacon packet
		ProbeRequest probeRequest = encoder.getProbeRequest();
		
		probeRequest.getDA().set("ff:ff:ff:ff:ff:ff");
		probeRequest.getSA().set("12:34:56:78:9a:bc");
		probeRequest.getBSSID().set("12:34:56:78:9a:bc");
		
		probeRequest.setDuration(60);

		probeRequest.getInformationList().addInformationElement(InformationElementId.SSID, new byte[0]);
		
		System.out.println(probeRequest.getBuffer().toHex());
	}

}
