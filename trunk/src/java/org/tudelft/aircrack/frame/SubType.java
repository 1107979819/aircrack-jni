package org.tudelft.aircrack.frame;


public enum SubType
{

	// Management packets
	AssociationRequest(PacketType.Management, 0x0),
	AssociationResponse(PacketType.Management, 0x1),
	ReassociationRequest(PacketType.Management, 0x2),
	ReassociationResponse(PacketType.Management, 0x3),
	ProbeRequest(PacketType.Management, 0x4),
	ProbeResponse(PacketType.Management, 0x5),
	Beacon(PacketType.Management, 0x8),
	ATIM(PacketType.Management, 0x9),
	Disassociation(PacketType.Management, 0xa),
	Authentication(PacketType.Management, 0xb),
	Deauthentication(PacketType.Management, 0xc),
	
	// Control
	BlockAckRequest(PacketType.Control, 0x8),
	BlockAck(PacketType.Control, 0x9),
	PsPoll(PacketType.Control, 0xa),
	RTS(PacketType.Control, 0xb),
	CTS(PacketType.Control, 0xc),
	ACK(PacketType.Control, 0xd),
	CFEND(PacketType.Control, 0xe),
	CFEND_CFACK(PacketType.Control, 0xf),
	
	// Data
	DATA(PacketType.Data, 0x0),
	DATA_CFACK(PacketType.Data, 0x1),
	DATA_CFPOLL(PacketType.Data, 0x2),
	DATA_CFACK_CFPOLL(PacketType.Data, 0x3),
	NullFunction(PacketType.Data, 0x4),
	CFACK(PacketType.Data, 0x5),
	CFPOLL(PacketType.Data, 0x6),
	CFACK_CFPOLL(PacketType.Data, 0x7),
	
	// ???
	Reserved(PacketType.Reserved, -1);
	
	private final PacketType type;
	private final int subType;
	
	private SubType(PacketType type, int subType)
	{
		this.type = type;
		this.subType = subType;
	}
	
	public static SubType find(PacketType type, int subType)
	{
		for (SubType sub : SubType.values())
			if (sub.type==type && sub.subType==subType)
				return sub;
		
		return Reserved;
	}
	
}
