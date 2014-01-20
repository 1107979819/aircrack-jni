package org.tudelft.parse80211.annotations;

public @interface Mapped
{
	
	int offset();
	boolean variableSize() default false;

}
