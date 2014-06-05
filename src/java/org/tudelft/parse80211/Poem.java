package org.tudelft.parse80211;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.tudelft.parse80211.frames.Frame;
import org.tudelft.parse80211.frames.management.ProbeRequest;
import org.tudelft.parse80211.frames.management.ProbeResponse;
import org.tudelft.parse80211.socket.LinuxSocketInterface;
import org.tudelft.parse80211.socket.SocketInterface;
import org.tudelft.parse80211.types.InformationElementId;

public class Poem
{
	private static SocketInterface sock;
	
	public final static String[] poem =
	{
		"1 ga je mee vliegen",
		"2 vroeg het meisje",
		"3 vliegen is voor vogels",
		"4 zei de jongen",
		"5 denk maar wij zijn vogels",
		"6 zei ze",
		"7 met armen vol veren",
		"8 voel maar",
		"9 wij zijn vogels"
	};
	
	public static void main(String[] args) throws InterruptedException, IOException
	{

		sock = new LinuxSocketInterface("mon0", "@aircrack");
		sock.open();
		sock.setChannel(11);

		while (true)
		{
			
			Frame frame = sock.receive();
			
			if (frame!=null)
			{
				if (frame instanceof ProbeRequest)
					receiveProbeRequest((ProbeRequest)frame);

				if (frame instanceof ProbeResponse)
				{
					System.out.println(frame);
					
					System.out.println(frame.getBuffer().toHex(7+32, 128));
				}

			}

		}

	}
	
	private static void receiveProbeRequest(ProbeRequest probeRequest)
	{
		
		System.out.printf("Probe Request from %s, to %s/%s, ssid='%s'\n",
			probeRequest.getSA().toString(),
			probeRequest.getDA().toString(),
			probeRequest.getBSSID().toString(),
			probeRequest.getInformationList().getSSID()
			);
		
		for (int i=0; i<poem.length; i++)
		{
			sendProbeResponse(
					String.format("12:34:56:78:9a:%02x", i),
					poem[i],
					probeRequest.getSA().toString()
					);
			try
			{
				Thread.sleep(3L);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}			
		}
		
	}
	
	private static void sendProbeResponse(String bssid, String ssid, String destination)
	{
		
		byte buf[] = sock.getEncoder().getBuffer().data;
		for (int i=0; i<buf.length; i++)
			buf[i] = 0;
		
		ProbeResponse response = sock.getEncoder().getProbeResponse();
		
		response.getSA().set(bssid);
		response.getBSSID().set(bssid);
		response.getDA().set(destination);
		
		response.getCapability().setCfPollRequest(true);
		response.getCapability().setChannelAgility(true);
		response.getCapability().setDsssOfdm(true);
		
		response.setBeaconInterval(100);

		try
		{
			response.getInformationList().addInformationElement(InformationElementId.SSID, ssid.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getInformationList().addInformationElement(InformationElementId.SupportedRates, new byte[] { (byte)0x82, (byte)0x84, (byte)0x8b, (byte)0x96 });
		
//		Decoder dec = new Decoder(response.getBuffer(), 7);
//		Frame frame = dec.decode(128);
//		System.out.println(frame);
//		
//		ProbeResponse resp = (ProbeResponse)frame;
//		System.out.println(">" + resp.getInformationList().getSSID() + "<");
		
		sock.send(response);
		
	}
	

}
