package org.tudelft.aircrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.management.BeaconOrProbeResponse;
import org.tudelft.aircrack.socket.AndroidSocketInterface;

public class StationScanner implements Runnable
{

	private static long pruneTime = 60*1000;
	
	private Thread runThread;
	private AndroidSocketInterface iface;
	private HashMap<String, Station> stations = new HashMap<String, Station>();
	
	private final static Comparator<Station> stationComparator = new Comparator<Station>() {
		public int compare(Station object1, Station object2)
		{
			return String.CASE_INSENSITIVE_ORDER.compare(object1.ssid, object2.ssid);
		}
	};
	
	public void start()
	{
		iface = new AndroidSocketInterface("mon0", "aircrack");
		iface.open();
		
		runThread = new Thread(this);
		runThread.start();		
	}
	
	public void stop()
	{
		runThread.interrupt();

		try
		{
			runThread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		iface.close();
		
	}
	
	public List<Station> getStations()
	{
		synchronized (stations)
		{
			ArrayList<Station> stationList = new ArrayList<Station>(stations.values());
			
			// Sort by SSID 
			Collections.sort(stationList, stationComparator);
			
			return stationList;
		}
	}

	@Override
	public void run()
	{
		long lastPruneTime = System.currentTimeMillis();
		
		while (true)
		{
			
			try
			{
				Frame frame = iface.receive();
				
				if (frame instanceof BeaconOrProbeResponse)
				{
					BeaconOrProbeResponse beacon = (BeaconOrProbeResponse)frame;
			
					Station station;
					
					synchronized (stations)
					{
						if (!stations.containsKey(beacon.getBSSID().toString()))
						{
							station = new Station();
							station.power = new ArrayList<Integer>();
							station.bssid = beacon.getBSSID();
							station.ssid = beacon.getSsid().toString();
							stations.put(beacon.getBSSID().toString(), station);
						} else
							station = stations.get(beacon.getBSSID().toString());
						
						station.power.add(frame.getReceiveInfo().getPower());
						station.lastHeard = System.currentTimeMillis();
					}
					
				}
				
			} catch (DecodingException e)
			{
				e.printStackTrace();
			}			
			
			// Prune stations that haven't been heard for a certain amount of time
			if (System.currentTimeMillis()-lastPruneTime > pruneTime)
			{
				ArrayList<Station> prunedStations = new ArrayList<Station>();

				for (Station station : stations.values())
					if (System.currentTimeMillis() - station.lastHeard > pruneTime)
						prunedStations.add(station);

				synchronized (stations)
				{
					for (Station station : prunedStations)
						stations.remove(station);
				}
			}
			
			Thread.yield();
		}		
	}

}
