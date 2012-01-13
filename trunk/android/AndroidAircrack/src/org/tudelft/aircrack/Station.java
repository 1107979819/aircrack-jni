package org.tudelft.aircrack;

import java.util.ArrayList;

import org.tudelft.aircrack.frame.Address;

public class Station
{
	Address bssid;
	String ssid;
	ArrayList<Integer> power;
	long lastHeard;
}