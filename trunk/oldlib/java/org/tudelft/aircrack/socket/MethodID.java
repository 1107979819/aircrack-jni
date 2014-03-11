package org.tudelft.aircrack.socket;

import org.codehaus.preon.annotation.BoundEnumOption;

public enum MethodID
{
	
	@BoundEnumOption(value = 0)
	Open,
	
	@BoundEnumOption(value = 1)
	Close,
	
	@BoundEnumOption(value = 2)
	Read,
	
	@BoundEnumOption(value = 3)
	Write,
	
	@BoundEnumOption(value = 4)
	SetChannel,
	
	@BoundEnumOption(value = 5)
	GetChannel,
	
	@BoundEnumOption(value = 6)
	SetFrequency,
	
	@BoundEnumOption(value = 7)
	GetFrequency,
	
	@BoundEnumOption(value = 8)
	SetMac,
	
	@BoundEnumOption(value = 9)
	GetMac,
	
	@BoundEnumOption(value = 10)
	SetRate,
	
	@BoundEnumOption(value = 11)
	GetRate,
	
	@BoundEnumOption(value = 12)
	GetMonitor,
	
	@BoundEnumOption(value = 13)
	SetMtu,
	
	@BoundEnumOption(value = 14)
	GetMtu

}
