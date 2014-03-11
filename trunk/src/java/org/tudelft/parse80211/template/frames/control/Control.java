package org.tudelft.parse80211.template.frames.control;

import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.annotations.U16;
import org.tudelft.parse80211.template.frames.Frame;

@Template
public class Control extends Frame
{
	
	@U16(offset=2) int duration;

}
