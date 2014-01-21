package org.tudelft.parse80211.template.frames.data;

import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.types.BufferBacked;

@Template
@FrameType(type=2, subType=0, size=32)
public class Data extends DataFrame
{

	@Mapped(offset=30) BufferBacked payload;
	
}
