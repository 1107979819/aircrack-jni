package org.tudelft.parse80211.template.socket.types;

import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.annotations.U32;
import org.tudelft.parse80211.annotations.U64;

@Template
public class ReceiveInfo
{

	@U64(offset=0) long macTime;
	
	@U32(offset=8) int power;
	
	@U32(offset=12) int noise;
	
	@U32(offset=16) int channel;
	
	@U32(offset=20) int frequency;
	
	@U32(offset=24) int rate;
	
	@U32(offset=28) int antenna;
	
}
