package org.tudelft.parse80211.annotations;

public @interface Bits
{
	
	// Offset
	int offset();

	// n-th bit
	int start();

	// n-th bit
	int count();

}
