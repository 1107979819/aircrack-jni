package org.tudelft.aircrack.sample;

import java.io.IOException;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.JniInterface;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.control.CtsFrame;
import org.tudelft.aircrack.frame.control.RtsFrame;

public class StationDetect
{
	
	public static void main(String[] args) throws DecodingException, IOException
	{
		
		final JniInterface iface = new JniInterface("mon0");
		iface.open();
		
		// 00:26:37:3b:1e:e6
		// final Address deviceAddress = new Address("00:26:37:3B:1E:E6"); // ZTE blade black
		// final Address deviceAddress = new Address("78:D6:F0:BF:19:2B"); // Marco
		// final Address deviceAddress = new Address("00:26:37:98:2C:A3"); // Andrei	
		final Address deviceAddress = new Address("94:00:70:60:05:FE"); // Lumia
		// final Address deviceAddress = new Address("A4:ED:4E:EE:24:32"); // Wendo	
		 
		
		// Transmit rate in ms
		final long txRate = 10;
		
		iface.setChannel(1);

		new Thread(new Runnable() {
			@Override
			public void run()
			{
				int channel = 1;
				while (true)
				{
					
					// iface.setChannel(channel++);
					
					if (channel>11)
						channel = 1;

					// Send RTS
					RtsFrame rts = new RtsFrame();
					rts.setRA(deviceAddress);
					rts.setTA(iface.getMac());
					rts.setDuration(100);
					
					try
					{
						iface.send(rts);
					} catch (IOException e1)
					{
						e1.printStackTrace();
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
		while (true)
		{
			
			// Collect CTS frames
			Frame frame = iface.receive();
			if (frame==null)
				continue;
			
			if (frame instanceof CtsFrame)
			{
				if (((CtsFrame) frame).getRA().compareTo(iface.getMac())==0)
				{
					System.out.println(frame);
					System.out.println("\t" + frame.getReceiveInfo());
					System.out.flush();
				}
			}
			
//			if (frame instanceof DataFrame)
//			{
//				DataFrame dataFrame = (DataFrame)frame;
//				System.out.println(dataFrame);
//				System.out.println("\t" + frame.getReceiveInfo());
//				System.out.flush();
//			}

			Thread.yield();
			
		}		
		
//		iface.close();
		
	}

}
