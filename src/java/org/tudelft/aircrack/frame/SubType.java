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
	Action(FrameType.Management, 0xd),
	
	// Control
	BlockAckRequest(FrameType.Control, 0x8),
	BlockAck(FrameType.Control, 0x9),
	PsPoll(FrameType.Control, 0xa),
	RTS(FrameType.Control, 0xb),
	CTS(FrameType.Control, 0xc),
	ACK(FrameType.Control, 0xd),
	CfEnd(FrameType.Control, 0xe),
	CfEnd_CfAck(FrameType.Control, 0xf),
	
	// Data
	Data(FrameType.Data, 0x0),
	Data_CfAck(FrameType.Data, 0x1),
	Data_CfPoll(FrameType.Data, 0x2),
	Data_CfAck_CfPoll(FrameType.Data, 0x3),
	NullFunction(FrameType.Data, 0x4),
	CfAck(FrameType.Data, 0x5),
	CfPoll(FrameType.Data, 0x6),
	CfAck_CfPoll(FrameType.Data, 0x7),

	QoS_Data(FrameType.Data, 0x8),
	QoS_Data_CfAck(FrameType.Data, 0x9),
	QoS_Data_CfPoll(FrameType.Data, 0xa),
	QoS_Data_CfAck_CfPoll(FrameType.Data, 0xb),
	QOS_NullFunction(FrameType.Data, 0xc),
	QoS_Reserved(FrameType.Data, 0xd),
	QoS_CfPoll(FrameType.Data, 0xe),
	Qos_CfAck_CfPoll(FrameType.Data, 0xf),
	
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
	
	public FrameType getType()
	{
		return type;
	}
	
	public int getSubType()
	{
		return subType;
	}
	
	public boolean isQos()
	{
		System.out.println("Zomg!");
		return (subType & 0x8) != 0;
	}
	
}
