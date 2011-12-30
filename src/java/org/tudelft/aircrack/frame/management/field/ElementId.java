package org.tudelft.aircrack.frame.management.field;

import org.codehaus.preon.annotation.BoundEnumOption;

public enum ElementId
{
	
	@BoundEnumOption(value = 0)
	SSID,
	
	@BoundEnumOption(value = 1)
	SupportedRates,
	
	@BoundEnumOption(value = 2)
	fhParameterSet,
	
	@BoundEnumOption(value = 3)
	dsParameterSet,
	
	@BoundEnumOption(value = 4)
	cfParameterSet,
	
	@BoundEnumOption(value = 5)
	tim,
	
	@BoundEnumOption(value = 6)
	ibssParameterSet,
	
	@BoundEnumOption(value = 7)
	country,
	
	@BoundEnumOption(value = 8)
	hoppingPatternParameters,
	
	@BoundEnumOption(value = 9)
	hoppingPatternTable,
	
	@BoundEnumOption(value = 10)
	request,
	
	@BoundEnumOption(value = 11)
	bssLoad,
	
	@BoundEnumOption(value = 12)
	edcaParameterSet,
	
	@BoundEnumOption(value = 13)
	tspec,
	
	@BoundEnumOption(value = 14)
	tclas,
	
	@BoundEnumOption(value = 15)
	schedule,
	
	@BoundEnumOption(value = 16)
	challenge,
	
	// reserved 17-31
	
	@BoundEnumOption(value = 32)
	powerConstraint,
	
	@BoundEnumOption(value = 33)
	powerCapability,
	
	@BoundEnumOption(value = 34)
	tpcRequest,
	
	@BoundEnumOption(value = 35)
	tpcReport,
	
	@BoundEnumOption(value = 36)
	supportedChannels,
	
	@BoundEnumOption(value = 37)
	channelSwitchAnnouncement,
	
	@BoundEnumOption(value = 38)
	measurementRequest,
	
	@BoundEnumOption(value = 39)
	measurementReport,
	
	@BoundEnumOption(value = 40)
	quiet,
	
	@BoundEnumOption(value = 41)
	ibssDfs,
	
	@BoundEnumOption(value = 42)
	erpInformation,
	
	@BoundEnumOption(value = 43)
	tsDelay,
	
	@BoundEnumOption(value = 44)
	tclasProcessing,

	@BoundEnumOption(value = 45)
	reserved_45,
	
	@BoundEnumOption(value = 46)
	qosCapability,
	
	@BoundEnumOption(value = 47)
	reserved_47,
	
	@BoundEnumOption(value = 48)
	rsn,
	
	@BoundEnumOption(value = 49)
	reserved_49,
	
	@BoundEnumOption(value = 50)
	extendedSupportRates,
	
	// reserved 51-126 
	
	@BoundEnumOption(value = 127)
	extendedCapabilities,
	
	// reserved 128-220

	@BoundEnumOption(value = 221)
	vendorSpecific;	
	
	// reserved 222-255
	
}
