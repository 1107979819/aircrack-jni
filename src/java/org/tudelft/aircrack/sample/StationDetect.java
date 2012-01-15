package org.tudelft.aircrack.sample;

import java.io.IOException;

import org.codehaus.preon.Codec;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.JniInterface;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.control.CtsFrame;
import org.tudelft.aircrack.frame.control.RaTaFrame;
import org.tudelft.aircrack.frame.control.RtsFrame;

public class StationDetect
{
	
	public static void main(String[] args) throws DecodingException, IOException
	{
		
		final JniInterface iface = new JniInterface("mon0");
		iface.open();
		
		iface.setChannel(6);
		
		// 00:26:37:3b:1e:e6
		final Address deviceAddress = new Address("00:a1:b0:00:bf:62");
		
		// Transmit rate in ms
		final long txRate = 1000;
		
		iface.setChannel(6);
		

		new Thread(new Runnable() {
			@Override
			public void run()
			{
				int channel = 1;
				while (true)
				{
					
//					iface.setChannel(channel++);
//					
//					if (channel>13)
//						channel = 1;
					
					// Send RTS
					RtsFrame rts = new RtsFrame();
					rts.setRA(deviceAddress);
					rts.setTA(iface.getMac());
					rts.setDuration(10*1000);
					
					// Encode
					byte[] raw;
					try
					{
						
						Codec<RaTaFrame> codec = Codecs.create(RaTaFrame.class);
						Codecs.encode(rts, codec);

						//raw = Frame.encode(rts);
						
						raw = Codecs.encode(rts, codec);
						
//						Util.printByteBuffer(raw);
						
						iface.write(raw);
						
					} catch (IOException e)
					{
						e.printStackTrace();
					}
					
					try
					{
						Thread.sleep(txRate);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}).start();

		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis()-startTime < 60*1000)
		{
			
			// Collect CTS frames
			Frame frame = iface.receive();
			if (frame==null)
				continue;
			
//			if (frame instanceof ProbeRequest)
//			{
//				ProbeRequest request = (ProbeRequest)frame;
//				if (!request.getSsid().startsWith("HHID"))
//					System.out.println(request);
//			}
			
			if (frame instanceof CtsFrame)
			{
				if (((CtsFrame) frame).getRA().compareTo(iface.getMac())==0)
				{
					System.out.println(frame);
					System.out.println("\t" + frame.getReceiveInfo());
					System.out.flush();
				}
			}

			Thread.yield();
			
		}		
		
		iface.close();
		
	}

}
