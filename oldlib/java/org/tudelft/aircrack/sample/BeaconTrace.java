package org.tudelft.aircrack.sample;

import java.io.IOException;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.JniInterface;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.management.Beacon;
import org.tudelft.aircrack.frame.visitor.AbstractFrameVisitor;

public class BeaconTrace
{

	private static class DataVisitor extends AbstractFrameVisitor
	{

		@Override
		public void visit(Beacon frame)
		{
			System.out.printf("%d, power=%d, channel=%d, mac=%s, ssid=%s\n",
					System.currentTimeMillis(),
					frame.getReceiveInfo().getPower(),
					frame.getReceiveInfo().getChannel(),
					frame.getBssid(),
					frame.getSsid()
					);
		}

	}

	public static void main(String[] args) throws DecodingException, IOException
	{

		final JniInterface iface = new JniInterface("mon0");
		iface.open();
		
		iface.setChannel(11);

		DataVisitor dataVisitor = new DataVisitor();

		while (true)
		{

			Frame frame = iface.receive();

			if (frame != null)
				frame.accept(dataVisitor);

		}

		// iface.close();

	}

}
