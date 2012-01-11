package org.tudelft.aircrack.socket;

import org.codehaus.preon.annotation.BoundEnumOption;

public enum MethodID
{
	
	@BoundEnumOption(value = 0)
	Read,
	
	@BoundEnumOption(value = 1)
	Write,
	
	@BoundEnumOption(value = 2)
	SetChannel,
	
	@BoundEnumOption(value = 3)
	GetChannel,
	
	@BoundEnumOption(value = 4)
	SetFrequency,
	
	@BoundEnumOption(value = 5)
	GetFrequency,
	
	@BoundEnumOption(value = 6)
	SetMac,
	
	@BoundEnumOption(value = 7)
	GetMac,
	
	@BoundEnumOption(value = 8)
	SetRate,
	
	@BoundEnumOption(value = 9)
	GetRate,
	
	@BoundEnumOption(value = 10)
	GetMonitor,
	
	@BoundEnumOption(value = 11)
	SetMtu,
	
	@BoundEnumOption(value = 12)
	GetMtu

}
