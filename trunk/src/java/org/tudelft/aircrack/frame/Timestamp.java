package org.tudelft.aircrack.frame;

import nl.flotsam.preon.annotation.BoundList;

public class Timestamp
{
	
	@BoundList(size="8")
	private byte[] timestamp;	

}
