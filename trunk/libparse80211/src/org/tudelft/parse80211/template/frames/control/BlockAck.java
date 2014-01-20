package org.tudelft.parse80211.template.frames.control;

import org.tudelft.parse80211.annotations.FrameTemplate;
import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.annotations.U16;
import org.tudelft.parse80211.types.Address;
import org.tudelft.parse80211.types.BufferBacked;

@FrameTemplate
@FrameType(type=1, subType=9, size=20)
public class BlockAck extends Control
{
	
	@Mapped(offset=4) Address RA;
	
	@Mapped(offset=10) Address TA;
	
	@U16(offset=16) int barControl; 

	@U16(offset=18) int blockAckStartingSequenceControl;
	
	@Mapped(offset=20) BufferBacked bitmap;
	
}
