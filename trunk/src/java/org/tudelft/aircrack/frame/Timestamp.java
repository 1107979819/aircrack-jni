package org.tudelft.aircrack.frame;

import org.codehaus.preon.annotation.BoundList;

public class Timestamp
{
	
	@BoundList(size="8")
	public byte[] timestamp = new byte[8];

}
