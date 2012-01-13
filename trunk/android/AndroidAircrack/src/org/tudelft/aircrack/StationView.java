package org.tudelft.aircrack;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

public class StationView extends TextView
{

	public StationView(Context context, Station station)
	{
		super(context);

		this.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));

		refresh(station);
	}
	
	public void refresh(Station station)
	{
		double avgPower = 0;
		
		for (Integer power : station.power)
			avgPower += power;
		
		avgPower /= station.power.size();
		
		setText(String.format("%s %.2f %d %s",
				station.bssid.toString(),
				avgPower,
				station.power.get(station.power.size()-1),
				station.ssid
				));
	}
	
}
