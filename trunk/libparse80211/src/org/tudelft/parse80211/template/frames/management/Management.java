package org.tudelft.parse80211.template.frames.management;

import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.annotations.U16;
import org.tudelft.parse80211.template.frames.Frame;
import org.tudelft.parse80211.types.Address;

@Template
public class Management extends Frame
{
	
	@U16(offset=2) int duration;
	
	@Mapped(offset=4) Address DA;
	
	@Mapped(offset=10) Address SA;
	
	@Mapped(offset=16) Address BSSID;
	
	@U16(offset=22) int sequenceControl;
}
