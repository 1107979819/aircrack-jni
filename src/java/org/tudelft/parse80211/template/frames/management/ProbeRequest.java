package org.tudelft.parse80211.template.frames.management;

import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.annotations.FrameType;
import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.types.InformationList;

@Template
@FrameType(type=0, subType=4, size=24)
public class ProbeRequest extends Management
{
	
	@Mapped(offset=24, variableSize=true) InformationList informationList;
	
}
