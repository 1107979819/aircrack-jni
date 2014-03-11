package org.tudelft.parse80211.template.frames.data;

import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.annotations.U16;
import org.tudelft.parse80211.template.frames.Frame;
import org.tudelft.parse80211.types.BufferBacked;

@Template
@FrameType(type=2, subType=8, size=34)
public class QosData extends Frame
{

	@U16(offset=30) int QOSControl;
	
	@Mapped(offset=32) BufferBacked payload;
	
}
