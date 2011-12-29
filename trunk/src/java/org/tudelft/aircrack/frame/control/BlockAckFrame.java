package org.tudelft.aircrack.frame.control;

import org.codehaus.preon.annotation.BoundList;

public class BlockAckFrame extends BlockAckOrRequestFrame 
{
	
	// TODO length should be 128 according to spec, but this doesn't seem to work?
	@BoundList(size="8")
	private byte[] bitmap;
	
}
