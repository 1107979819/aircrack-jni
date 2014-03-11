package org.tudelft.aircrack.frame;

import org.codehaus.preon.annotation.BoundEnumOption;

public enum FrameType
{
	
	@BoundEnumOption(value = 0)
	Management,

	@BoundEnumOption(value = 1)
	Control,

	@BoundEnumOption(value = 2)
	Data,
	
	@BoundEnumOption(value = 3)
	Reserved

}
