package org.tudelft.parse80211.template.frames.control;

import org.tudelft.parse80211.annotations.Address;
import org.tudelft.parse80211.annotations.Bound;

@Bound
public class Cts extends Control
{
	
	@Address(offset=4) Address RA;

}
