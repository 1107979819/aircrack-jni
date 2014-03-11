package org.tudelft.parse80211.template.frames;

import org.tudelft.parse80211.annotations.Bit;
import org.tudelft.parse80211.annotations.Bits;
import org.tudelft.parse80211.annotations.Template;

@Template
public class Frame
{

	@Bits(offset=0, start=0, count=2) int protocolVersion;
	@Bits(offset=0, start=2, count=2) int type;
	@Bits(offset=0, start=4, count=4) int subType;
	@Bits(offset=0, start=2, count=6) int frameType;
	
	@Bit(offset=1, bit=0) boolean toDs;
	@Bit(offset=1, bit=1) boolean fromDs;
	@Bit(offset=1, bit=2) boolean moreFrag;
	@Bit(offset=1, bit=3) boolean retry;
	@Bit(offset=1, bit=4) boolean pwrMgt;
	@Bit(offset=1, bit=5) boolean moreData;
	@Bit(offset=1, bit=6) boolean protectedFrame;
	@Bit(offset=1, bit=7) boolean order;

}
