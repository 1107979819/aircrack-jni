package org.tudelft.parse80211.template.frames.data;

import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.annotations.U16;
import org.tudelft.parse80211.template.frames.Frame;
import org.tudelft.parse80211.types.Address;

@Template
public class DataFrame extends Frame
{

	@U16(offset=2) int duration;
	
	@Mapped(offset=4) Address Address1;
	
	@Mapped(offset=10) Address Address2;
	
	@Mapped(offset=16) Address Address3;
	
	@U16(offset=22) int sequenceControl;
	
	@Mapped(offset=24) Address Address4;

}
