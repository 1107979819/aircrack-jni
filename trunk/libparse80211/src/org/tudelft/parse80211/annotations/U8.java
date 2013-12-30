package org.tudelft.parse80211.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface U8
{
	
	// Offset
	int offset();

}
