package org.tudelft.aircrack;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class StationListAdapter extends BaseAdapter
{
	private StationScanner scanner;
	private List<Station> stations = new ArrayList<Station>();

	// Handler to post update events from the main thread
	private Handler handler;
	
	public StationListAdapter(final StationScanner scanner)
	{
		this.handler = new Handler();
		
		new Thread() {
			public void run()
			{
				
				while (true)
				{
					try
					{
						
						stations = scanner.getStations();
						
						// Notify observers
						handler.post(new Runnable() {
							public void run()
							{
								notifyDataSetChanged();
							}
						});
						
						Thread.sleep(1000L);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				
			};
		}.start();
	}

	@Override
	public int getCount()
	{
		return stations.size();
	}

	@Override
	public Object getItem(int position)
	{
		return stations.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return stations.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView!=null)
		{
			((StationView)convertView).refresh(stations.get(position));
			return convertView;
		} else
			return new StationView(parent.getContext(), stations.get(position)); 
	}
	

}
