package org.tudelft.parse80211.template.frames.control;

import org.tudelft.parse80211.annotations.FrameTemplate;
import org.tudelft.parse80211.annotations.FrameType;

@FrameTemplate
@FrameType(type=1, subType=12)
public class Cts extends RtsCts
{

}
