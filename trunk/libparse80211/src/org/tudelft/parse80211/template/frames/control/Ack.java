package org.tudelft.parse80211.template.frames.control;

import org.tudelft.parse80211.annotations.FrameTemplate;
import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.annotations.Mapped;

@FrameTemplate
@FrameType(type=1, subType=13, size=10)
public class Ack extends RtsCts
{

	@Mapped(offset=4) org.tudelft.parse80211.types.Address RA;

}
