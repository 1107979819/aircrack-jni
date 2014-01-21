package org.tudelft.parse80211;

import java.io.IOException;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.ReceiveInfo;
import org.tudelft.aircrack.TransmitInfo;
import org.tudelft.aircrack.frame.management.field.ElementId;
import org.tudelft.aircrack.frame.management.field.InformationElement;
import org.tudelft.aircrack.socket.LinuxSocketInterface;
import org.tudelft.aircrack.socket.SocketInterface;
import org.tudelft.parse80211.frames.Decoder;
import org.tudelft.parse80211.frames.Encoder;
import org.tudelft.parse80211.frames.Frame;
import org.tudelft.parse80211.frames.management.Beacon;
import org.tudelft.parse80211.types.ByteBuffer;

public class BeaconTest
{
	
	public static void main(String[] args) throws DecodingException, InterruptedException, IOException
	{
		
		SocketInterface sock = new LinuxSocketInterface("mon0", "@aircrack");
		sock.open();
		
		// sock.setChannel(11);
		
		byte[] data = new byte[8192];
		ByteBuffer buffer = new ByteBuffer(data);
		ReceiveInfo receiveInfo = new ReceiveInfo();

		// org.tudelft.parse80211.frames.Frame frame = new org.tudelft.parse80211.frames.Frame(data);
		
		Encoder encoder = new Encoder();

		// Decoder decoder = new Decoder(buffer);
		Decoder decoder = new Decoder(encoder.getBuffer());
		
		while (true)
		{
			
			Beacon beacon = encoder.getBeacon();
			
			System.out.println("Buffer Size: " + beacon.getBuffer().size);
			System.out.println("Count: " + beacon.getInformationList().count());
			
			beacon.getDA().set("00:00:00:00:00:00");
			beacon.getSA().set("12:34:56:78:9a:bc");
			beacon.getBSSID().set("12:34:56:78:9a:bc");
			
			beacon.setBeaconInterval(100);
			
			beacon.getCapability().setCfPollRequest(true);
			beacon.getCapability().setChannelAgility(true);
			beacon.getCapability().setDsssOfdm(true);
			
//			beacon.setAddress1(address);
//			beacon.setSA(iface.getMac());
//			beacon.setSsid("Lollercopter" + System.currentTimeMillis());
//			beacon.setBSSID(iface.getMac());
//			beacon.setBeaconInterval(100);		
//			beacon.capability.cfPollRequest = true;		
//			beacon.capability.channelAgility = true;		
//			beacon.capability.dsssOfdm = true;		
//			
//			beacon.getElements().addElement(new InformationElement(
//					ElementId.SupportedRates,
//					new byte[] { (byte)0x82, (byte)0x84, (byte)0x8b, (byte)0x96 }
//					));
			
			TransmitInfo txInfo = sock.send(beacon.getBuffer().data, beacon.getBuffer().size);
			
			Frame frame = decoder.decode(beacon.getBuffer().size);
			System.out.println("Decoded: " + frame);
			
			Thread.sleep(1000L);
			
		}
		
	}

}
