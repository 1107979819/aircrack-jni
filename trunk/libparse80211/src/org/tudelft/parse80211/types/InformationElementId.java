package org.tudelft.parse80211.types;



public enum InformationElementId
{
	
	SSID(0),
	SupportedRates(1),
	fhParameterSet(2),
	dsParameterSet(3),
	cfParameterSet(4),
	tim(5),
	ibssParameterSet(6),
	country(7),
	hoppingPatternParameters(8),
	hoppingPatternTable(9),
	request(10),
	bssLoad(11),
	edcaParameterSet(12),
	tspec(13),
	tclas(14),
	schedule(15),
	challenge(16),
	
	// reserved 17-31
	
	powerConstraint(32),
	powerCapability(33),
	tpcRequest(34),
	tpcReport(35),
	supportedChannels(36),
	channelSwitchAnnouncement(37),
	measurementRequest(38),
	measurementReport(39),
	quiet(40),
	ibssDfs(41),
	erpInformation(42),
	tsDelay(43),
	tclasProcessing(44),
	reserved_45(45),
	qosCapability(46),
	reserved_47(47),
	rsn(48),
	reserved_49(49),
	extendedSupportRates(50),
	
	// reserved 51-126 
	
	extendedCapabilities(127),
	
	// reserved 128-220
	
	vendorSpecific(221),	
	
	// reserved 222-255

	Unknown(-1);
	
	private final int code;
	
	private InformationElementId(int code)
	{
		this.code = code;
	}
	
	public static InformationElementId fromCode(int code)
	{
		for (InformationElementId id : values())
			if (id.code==code)
				return id;
		
		return Unknown;
	}
	
	public int getCode()
	{
		return code;
	}
	
}
