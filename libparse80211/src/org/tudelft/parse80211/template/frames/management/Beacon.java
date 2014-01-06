package org.tudelft.parse80211.template.frames.management;

import org.tudelft.parse80211.annotations.FrameTemplate;
import org.tudelft.parse80211.annotations.FrameType;

@FrameTemplate
@FrameType(type=0, subType=8)
public class Beacon extends BeaconOrProbeResponse
{

}
