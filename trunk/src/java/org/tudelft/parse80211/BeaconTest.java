package org.tudelft.parse80211;

import java.io.IOException;
import java.util.Date;

import org.tudelft.parse80211.frames.management.Beacon;
import org.tudelft.parse80211.socket.LinuxSocketInterface;
import org.tudelft.parse80211.socket.SocketInterface;
import org.tudelft.parse80211.types.InformationElementId;

public class BeaconTest
{
	
	public static void main(String[] args) throws InterruptedException, IOException
	{
		
		SocketInterface sock = new LinuxSocketInterface("mon0", "@aircrack");
		sock.open();
		
		while (true)
		{
			
			byte buf[] = sock.getEncoder().getBuffer().data;
			for (int i=0; i<buf.length; i++)
				buf[i] = 0;
			
			Beacon beacon = sock.getEncoder().getBeacon();
			
			// System.out.println(sock.getEncoder().getBuffer().toHex(256));

			beacon.getDA().set("ff:ff:ff:ff:ff:ff");
			beacon.getSA().set("12:34:56:78:9a:bc");
			beacon.getBSSID().set("12:34:56:78:9a:bc");
			
			beacon.setBeaconInterval(100);
			
			beacon.getCapability().setCfPollRequest(true);
			beacon.getCapability().setChannelAgility(true);
			beacon.getCapability().setDsssOfdm(true);
			
			Date now = new Date();
			String ssid = String.format("%02d:%02d", now.getHours(), now.getMinutes());
			
			beacon.getInformationList().addInformationElement(InformationElementId.SSID, ssid.getBytes("UTF-8"));
			beacon.getInformationList().addInformationElement(InformationElementId.SupportedRates, new byte[] { (byte)0x82, (byte)0x84, (byte)0x8b, (byte)0x96 });

//			System.out.println("Buffer Size: " + beacon.getBuffer().size);
//			System.out.println("Buffer Offset: " + beacon.getInformationList().getOffset());
//			System.out.println("Count: " + beacon.getInformationList().count());
//			System.out.println(sock.getEncoder().getBuffer().toHex(63));
			
//			beacon.setAddress1(address);
//			beacon.setSA(iface.getMac());
//			beacon.setBSSID(iface.getMac());
//			beacon.setBeaconInterval(100);		
//			beacon.capability.cfPollRequest = true;		
//			beacon.capability.channelAgility = true;		
//			beacon.capability.dsssOfdm = true;		
//			
			Thread.sleep(10);
			
			sock.send(beacon);
			
		}
		
	}

}
