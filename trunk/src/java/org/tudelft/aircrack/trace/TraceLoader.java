package org.tudelft.aircrack.trace;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.codehaus.preon.Codec;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.control.ControlFrame;
import org.tudelft.aircrack.frame.control.RtsFrame;
import org.tudelft.aircrack.frame.management.BeaconOrProbeResponse;
import org.tudelft.aircrack.frame.management.ManagementFrame;
import org.tudelft.aircrack.frame.management.ProbeRequest;

public class TraceLoader
{
	
	private final static int HEADER_SIZE = 11*4+5;
	
	private final static Codec<TraceElement> elementCodec = Codecs.create(TraceElement.class);

	public static Frame loadFrame(BufferedInputStream input) throws IOException, DecodingException
	{
		// Read element header
		byte[] buf = new byte[HEADER_SIZE];
		input.read(buf);

		// Decode trace element header
		TraceElement element = Codecs.decode(elementCodec, buf);
		
		// Read variable-sized payload
		buf = new byte[element.getPayloadLength()];
		input.read(buf);
		
		// Decode the frame
		Frame frame = Frame.decode(element.getReceiveInfo(), buf);
		
		return frame;
	}
	
	public static ArrayList<Frame> loadTrace(String fileName) throws IOException, DecodingException
	{
		ArrayList<Frame> ret = new ArrayList<Frame>();
		
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));

		while (input.available()>=HEADER_SIZE)
		{
			ret.add(loadFrame(input));
			
			if (ret.size()>=150)
				break;
		}
		
		return ret;
	}
	
	public static void getDecodingStatistics(ArrayList<Frame> trace)
	{
		// Get some statistics about successful decoding rate
		int decoded = 0, packetCount = 0;
		
		for (Frame frame : trace)
		{
			if (frame.getClass() != Frame.class
					&& frame.getClass() != ControlFrame.class
					&& frame.getClass() != ManagementFrame.class)
				decoded++;
			
			packetCount++;
		}
		
		System.out.printf("Packets: %d, decoded: %.2f%%\n", packetCount, 100*decoded / (double)packetCount);
	}
	
	public static void getApStatistics(ArrayList<Frame> trace)
	{
		// Find stations based on beacon packets
		TreeMap<Address, ArrayList<Integer>> power = new TreeMap<Address, ArrayList<Integer>>();
		for (Frame frame : trace)
		{
			if (frame instanceof BeaconOrProbeResponse)
			{
				Address bssid = ((BeaconOrProbeResponse)frame).getBssid();
				
				if (!power.containsKey(bssid))
					power.put(bssid, new ArrayList<Integer>());
				
				power.get(bssid).add(frame.getReceiveInfo().getPower());
			}
		}
		
		for (Frame frame : trace)
		{
			if (frame instanceof RtsFrame)
			{
				Address bssid = ((RtsFrame)frame).getTA();
				
				if (power.containsKey(bssid))
					power.get(bssid).add(frame.getReceiveInfo().getPower());
			}
		}
		
		// Print results
		for (Address bssid : power.keySet())
		{
			
			// Skip stations with fewer than 100 beacons
			if (power.get(bssid).size()<100) continue;
				
			// Calculate mean
			double mean = 0;
			if (power.get(bssid).size()>0)
			{
				for (int pwr : power.get(bssid))
					mean += pwr;
				mean /= power.get(bssid).size();
			}
					
			// Calculate mean
			double variance = 0;
			if (power.get(bssid).size()>0)
			{
				for (int pwr : power.get(bssid))
					variance += (pwr - mean) * (pwr - mean);
				variance /= power.get(bssid).size();
			}
					
			System.out.printf(
					"%s %6d %6.2f %6.2f %6.2f\n",
					bssid.toString(),
					power.get(bssid).size(),
					mean,
					variance,
					Math.sqrt(variance)
					);
		}
	}
	
	public static void main(String[] args)
	{
		
		// Load trace.
		ArrayList<Frame> trace = null;
		try
		{
			trace = loadTrace("wifi.log");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (DecodingException e)
		{
			e.printStackTrace();
		}
		
		for (Frame frame : trace)
		{
			if (frame instanceof ProbeRequest)
			{
				System.out.println(frame);
			}
		}
		
		getDecodingStatistics(trace);
		getApStatistics(trace);
		
	}

}
