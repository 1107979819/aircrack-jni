package org.tudelft.parse80211.template.fields;

import org.tudelft.parse80211.annotations.Bit;
import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.types.BufferBacked;
import org.tudelft.parse80211.types.ByteBuffer;

@Template
public class Capability extends BufferBacked
{

	public Capability(ByteBuffer data, int offset)
	{
		super(data, offset);
	}
	
	@Bit(offset=1, bit=0) public boolean ess;
	@Bit(offset=1, bit=1) public boolean ibss;
	@Bit(offset=1, bit=2) public boolean cfPollable;
	@Bit(offset=1, bit=3) public boolean cfPollRequest;
	@Bit(offset=1, bit=4) public boolean privacy;
	@Bit(offset=1, bit=5) public boolean shortPreamble;
	@Bit(offset=1, bit=6) public boolean pbcc;
	@Bit(offset=1, bit=7) public boolean channelAgility;
	@Bit(offset=2, bit=0) public boolean spectrumManagement;
	@Bit(offset=2, bit=1) public boolean qos;
	@Bit(offset=2, bit=2) public boolean shortSlotTime;
	@Bit(offset=2, bit=3) public boolean apsd;
	@Bit(offset=2, bit=4) public boolean reserved1;
	@Bit(offset=2, bit=5) public boolean dsssOfdm;
	@Bit(offset=2, bit=6) public boolean delayedBlockAck;
	@Bit(offset=2, bit=7) public boolean immediateBlockAck;
		
}
