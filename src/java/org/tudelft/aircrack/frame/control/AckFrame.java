package org.tudelft.aircrack.frame.control;

import nl.flotsam.preon.annotation.Bound;
import nl.flotsam.preon.annotation.BoundNumber;

import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.FCS;
import org.tudelft.aircrack.frame.Frame;


public class AckFrame extends Frame {
	
	@BoundNumber(size="16")
	int duration;

	@Bound
	Address recipient;
	
	@Bound
	FCS fcs;
	
	@Override
	public String toString(){
		return String.format("[ACK: recipient=%s]", recipient.toString());
	}
}
