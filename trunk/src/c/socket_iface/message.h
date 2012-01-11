#ifndef __message_h__
#define __message_h__

typedef enum
{
	Read = 0,
	Write,
	SetChannel,
	GetChannel,
	SetFrequency,
	GetFrequency,
	SetMac,
	GetMac,
	SetRate,
	GetRate,
	GetMonitor,
	SetMtu,
	GetMtu
} MessageID;

typedef struct
{

	// Method to call
	unsigned char methodId;

	// Argument
	unsigned int argument;

	// Payload length
	unsigned short payloadLength;

} __attribute__((packed)) Message;

#endif
