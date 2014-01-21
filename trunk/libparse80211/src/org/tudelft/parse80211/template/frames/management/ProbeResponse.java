package org.tudelft.parse80211.template.frames.management;

import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.annotations.FrameType;

@Template
@FrameType(type=0, subType=5, size=36)
public class ProbeResponse extends BeaconOrProbeResponse
{
	
}
