package org.tudelft.parse80211.template.frames.management;

import org.tudelft.parse80211.annotations.FrameTemplate;
import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.annotations.U16;
import org.tudelft.parse80211.types.InformationList;
import org.tudelft.parse80211.types.TimeStamp;

@FrameTemplate
public class BeaconOrProbeResponse extends Management
{

	@Mapped(offset=24) TimeStamp timestamp;
	
	@U16(offset=32) int beaconInterval;

	@U16(offset=34) int capability;
	
	@Mapped(offset=36, variableSize=true) InformationList informationList; 

}
