package org.tudelft.parse80211.template.frames.control;

import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.annotations.FrameType;

@Template
@FrameType(type=1, subType=12, size=10)
public class Cts extends RtsCts
{

}
