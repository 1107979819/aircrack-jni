package org.tudelft.parse80211.template.frames.control;

import org.tudelft.parse80211.annotations.FrameTemplate;
import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.types.Address;

@FrameTemplate
@FrameType(type=1, subType=11, size=16)
public class Rts extends RtsCts
{

	@Mapped(offset=10) Address TA;

}
