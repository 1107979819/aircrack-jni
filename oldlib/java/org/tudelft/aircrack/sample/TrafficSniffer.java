package org.tudelft.aircrack.sample;

import java.io.IOException;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.JniInterface;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.management.ProbeRequest;
import org.tudelft.aircrack.frame.visitor.AbstractFrameVisitor;

public class TrafficSniffer
{
	
	private static class DataVisitor extends AbstractFrameVisitor
	{
		
		private final Address address;
		
		public DataVisitor(Address address)
		{
			this.address = address;
		}
		
//		@Override
//		public void visit(DataFrame frame)
//		{
//			System.out.println(frame.getBody().length);
//
//			if (frame.getAddress1().compareTo(address)==0)
//				System.out.println(frame.getAddress1() + " " + frame.getAddress2());			
//			
//			
//		}
		
		@Override
		public void visit(ProbeRequest frame)
		{
			System.out.println(frame);
		}
		
	}
	
	public static void main(String[] args) throws DecodingException, IOException
	{

		final JniInterface iface = new JniInterface("mon0");
		iface.open();
		
		DataVisitor dataVisitor = new DataVisitor(iface.getMac());

		while (true)
		{
			
			Frame frame = iface.receive();
			
			if (frame!=null)
				frame.accept(dataVisitor);
			
		}
		
		// iface.close();

	}

}
