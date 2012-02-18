#ifndef __message_h__
#define __message_h__

typedef enum
{
	Success = 0,
	Error = -1
} ResultCode;

typedef enum
{
	Open,
	Close,
	Read,
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
} MethodID;

typedef struct
{

	// Method to call
	unsigned char methodId;

	// Argument
	unsigned int argument;

	// Payload length
	unsigned short payloadLength;

	// Message payload
	unsigned char payload[];

} __attribute__((packed)) Message;

#endif