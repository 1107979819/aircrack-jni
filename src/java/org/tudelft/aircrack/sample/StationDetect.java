package org.tudelft.aircrack.sample;

import java.io.IOException;

import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.Interface;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.Util;
import org.tudelft.aircrack.frame.control.CtsFrame;
import org.tudelft.aircrack.frame.control.RtsFrame;

public class StationDetect
{
	
	public static void main(String[] args) throws DecodingException, IOException
	{
		
		final Interface iface = new Interface("mon0");
		iface.open();
		
		// 00:26:37:3b:1e:e6
		final Address phoneAddress = new Address(
				new byte[] {
						(byte)0x00, (byte)0x26, (byte)0x37,
						(byte)0x3b, (byte)0x1e, (byte)0xe6
						});

//		final Address phoneAddress = new Address(
//				new byte[] {
//						(byte)0xec, (byte)0x55, (byte)0xf9,
//						(byte)0x23, (byte)0x72, (byte)0x87
//						});
		
		// Transmit rate in ms
		final long txRate = 1000;

		new Thread(new Runnable() {
			@Override
			public void run()
			{
				while (true)
				{
//					System.out.println("RTS");
					
					// Send RTS
					RtsFrame rts = new RtsFrame();
					rts.setRA(phoneAddress);
					rts.setTA(iface.getMac());
					rts.setDuration(10);
					
					// Encode
					byte[] raw;
					try
					{

						raw = Codecs.encode(rts, Codecs.create(RtsFrame.class));
						iface.write(raw);
						
//						Util.printByteBuffer(raw);
					
//						Frame test = Frame.decode(null, raw);
//						System.out.println(test);
						
						
					} catch (IOException e)
					{
						e.printStackTrace();
					}
//					catch (DecodingException e)
//					{
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
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

		while (true)
		{
			
			// Collect CTS frames
			Frame frame = iface.receive();
			if (frame==null)
				continue;
			
			if (frame instanceof CtsFrame)
			{
//				System.out.println(((CtsFrame) frame).getRA());
					
				if (((CtsFrame) frame).getRA().compareTo(iface.getMac())==0)
					System.out.println(frame);
			}
			
		}		
		
		// iface.close();
		
	}

}
