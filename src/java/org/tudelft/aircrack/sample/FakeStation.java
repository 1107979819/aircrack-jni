package org.tudelft.aircrack.sample;

import java.io.IOException;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.JniInterface;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.management.Beacon;
import org.tudelft.aircrack.frame.management.field.ElementId;
import org.tudelft.aircrack.frame.management.field.InformationElement;

public class FakeStation
{
	
	
	
	public static void main(String[] args) throws IOException, InterruptedException, DecodingException
	{
		
		final JniInterface iface = new JniInterface("mon0");
		iface.open();
		
		iface.setChannel(1);
		
		while (true)
		{
			
			// Parse incoming
			Frame frame;
			while ((frame=iface.receive())!=null)
			{
				if (frame instanceof Beacon)
					System.out.println(frame);
			}
			
			// Send a beacon message
			Beacon beacon = new Beacon();
			beacon.setAddress1(Address.Broadcast);
			beacon.setSA(iface.getMac());
			beacon.setSsid("Lollercopter");
			beacon.setBSSID(iface.getMac());
			beacon.setBeaconInterval(100);		
			beacon.capability.cfPollRequest = true;		
			beacon.capability.channelAgility = true;		
			beacon.capability.dsssOfdm = true;		
			
			beacon.getElements().addElement(new InformationElement(
					ElementId.SupportedRates,
					new byte[] { (byte)0x82, (byte)0x84, (byte)0x8b, (byte)0x96 }
					));
			
			iface.send(beacon);

			System.out.println(beacon);
			Thread.sleep(100L);
		}
		
		
		// iface.close();
		
	}

}
