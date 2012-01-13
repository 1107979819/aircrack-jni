package org.tudelft.aircrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AndroidAircrackActivity extends Activity
{

	private StationScanner scanner = new StationScanner();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		((ListView)findViewById(R.id.stationList)).setAdapter(new StationListAdapter(scanner));
				
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		System.out.println("ON START");
		scanner.start();
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		scanner.stop();
	}
	
}