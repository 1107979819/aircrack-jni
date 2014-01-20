package org.tudelft.parse80211.template.frames.control;

import org.tudelft.parse80211.annotations.FrameTemplate;
import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.types.Address;

@FrameTemplate
public class RtsCts extends Control
{

	@Mapped(offset=4) Address RA;
	
}
