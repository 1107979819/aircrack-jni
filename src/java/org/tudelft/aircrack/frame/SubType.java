package org.tudelft.aircrack.frame;


public enum SubType
{

	// Management packets
	AssociationRequest(FrameType.Management, 0x0),
	AssociationResponse(FrameType.Management, 0x1),
	ReassociationRequest(FrameType.Management, 0x2),
	ReassociationResponse(FrameType.Management, 0x3),
	ProbeRequest(FrameType.Management, 0x4),
	ProbeResponse(FrameType.Management, 0x5),
	Beacon(FrameType.Management, 0x8),
	ATIM(FrameType.Management, 0x9),
	Disassociation(FrameType.Management, 0xa),
	Authentication(FrameType.Management, 0xb),
	Deauthentication(FrameType.Management, 0xc),
	
	// Control
	BlockAckRequest(FrameType.Control, 0x8),
	BlockAck(FrameType.Control, 0x9),
	PsPoll(FrameType.Control, 0xa),
	RTS(FrameType.Control, 0xb),
	CTS(FrameType.Control, 0xc),
	ACK(FrameType.Control, 0xd),
	CFEND(FrameType.Control, 0xe),
	CFEND_CFACK(FrameType.Control, 0xf),
	
	// Data
	DATA(FrameType.Data, 0x0),
	DATA_CFACK(FrameType.Data, 0x1),
	DATA_CFPOLL(FrameType.Data, 0x2),
	DATA_CFACK_CFPOLL(FrameType.Data, 0x3),
	NullFunction(FrameType.Data, 0x4),
	CFACK(FrameType.Data, 0x5),
	CFPOLL(FrameType.Data, 0x6),
	CFACK_CFPOLL(FrameType.Data, 0x7),
	
	// ???
	Reserved(FrameType.Reserved, -1);
	
	private final FrameType type;
	private final int subType;
	
	private SubType(FrameType type, int subType)
	{
		this.type = type;
		this.subType = subType;
	}
	
	public static SubType find(FrameType type, int subType)
	{
		for (SubType sub : SubType.values())
			if (sub.type==type && sub.subType==subType)
				return sub;
		
		return Reserved;
	}
	
}
