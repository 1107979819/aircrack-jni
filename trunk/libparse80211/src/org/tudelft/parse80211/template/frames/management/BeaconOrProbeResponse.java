package org.tudelft.parse80211.template.frames.management;

import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.annotations.U16;
import org.tudelft.parse80211.template.fields.Capability;
import org.tudelft.parse80211.types.InformationList;
import org.tudelft.parse80211.types.TimeStamp;

@Template
public class BeaconOrProbeResponse extends Management
{

	@Mapped(offset=24) TimeStamp timestamp;
	
	@U16(offset=32) int beaconInterval;

	@Mapped(offset=34) Capability capability;
	
	@Mapped(offset=36, variableSize=true) InformationList informationList; 

}
