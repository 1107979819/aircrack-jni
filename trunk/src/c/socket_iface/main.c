#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>

#include "osdep.h"

#include "message.h"

#define SOCKET_ADRESS "aircrack"
#define WIFI_BUFFER_SIZE 4096
#define RXTX_BUFFER_SIZE (WIFI_BUFFER_SIZE + sizeof(Message) + sizeof(struct rx_info))
#define INTERFACENAME_LENGTH 128

// Aircrack state
static struct wif * wi = NULL;
static struct rx_info rxInfo;

// Wifi rx/tx buffer
static char wifiBuffer[WIFI_BUFFER_SIZE];

// Socket buffer
static char rxtxBuffer[RXTX_BUFFER_SIZE];

void createSocketAddress(const char *name, struct sockaddr_un *address, socklen_t * length)
{
	// Zero out the address struct
    memset(address, 0, sizeof (*address));

    address->sun_family = AF_LOCAL;

    // Copy the socket name into the address struct.
    // Note that we're using the Linux abstract namespace, hence the leading zero-byte.
    memcpy(address->sun_path + 1, name, strlen(name));
    address->sun_path[0] = 0;

    * length = strlen(name) + offsetof(struct sockaddr_un, sun_path) + 1;
}

int setupSocket(const char * name)
{
	int n = 1, err;
	int serverSocket;
	socklen_t alen;

	// Server socket address
	struct sockaddr_un address;

	// Create socket
	if ((serverSocket = socket(AF_LOCAL, SOCK_STREAM, 0)) == -1)
	{
		perror("socket");
		exit(1);
	}

	createSocketAddress(name, &address, &alen);

	// Re-use address (needed?)
    setsockopt(serverSocket, SOL_SOCKET, SO_REUSEADDR, &n, sizeof(n));

	// Bind to address
    err = bind(serverSocket, (struct sockaddr *) &address, alen);
    if (err < 0)
    {
		perror("bind");
    	exit(2);
    }

    return serverSocket;

}

/**
 * Sends a success message.
 *
 * @param socket socket on which to send the message.
 * @param methodId method that generated the message.
 */
int sendSuccess(int socket, int methodId)
{
	Message returnMessage;

	// Clear out the message struct
	memset(&returnMessage, 0, sizeof(Message));

	returnMessage.methodId = methodId;
	returnMessage.argument = Success;

	// Send the message on the socket
	return send(socket, &returnMessage, sizeof(Message), 0);
}

/**
 * Sends a success message.
 *
 * @param socket socket on which to send the message.
 * @param methodId method that generated the message.
 * @param argument return value
 */
int sendReturnValue(int socket, int methodId, int argument)
{
	Message returnMessage;

	// Clear out the message struct
	memset(&returnMessage, 0, sizeof(Message));

	returnMessage.methodId = methodId;
	returnMessage.argument = argument;

	// Send the message on the socket
	return send(socket, &returnMessage, sizeof(Message), 0);
}

/**
 * Sends an error message.
 *
 * @param socket socket on which to send the message.
 * @param methodId method that generated the message.
 * @param message a string that describes the error.
 */
int sendError(int socket, int methodId, const char * message)
{
	Message returnMessage;

	// Clear out the message struct
	memset(&returnMessage, 0, sizeof(Message));

	returnMessage.methodId = methodId;
	returnMessage.argument = Error;
	returnMessage.payloadLength = strlen(message);

	// Copy the message and payload into a single buffer
	memcpy(rxtxBuffer, &returnMessage, sizeof(Message));
	memcpy(rxtxBuffer + sizeof(Message), message, strlen(message));

	// Send the message on the socket
	return send(socket, rxtxBuffer, sizeof(Message) + strlen(message), 0);
}

/**
 * Sends a message with payload.
 *
 * @param socket socket on which to send the message.
 * @param methodId method that generated the message.
 * @param payloadLength payload length.
 * @param payload payload data.
 */
int sendPayload(int socket, int methodId, int payloadLength, const char * payload)
{
	Message returnMessage;

	// Clear out the message struct
	memset(&returnMessage, 0, sizeof(Message));

	returnMessage.methodId = methodId;
	returnMessage.argument = payloadLength;
	returnMessage.payloadLength = payloadLength;

	// Copy the message and payload into a single buffer
	memcpy(rxtxBuffer, &returnMessage, sizeof(Message));
	memcpy(rxtxBuffer + sizeof(Message), payload, payloadLength);

	// Send the message on the socket
	return send(socket, rxtxBuffer, sizeof(Message) + payloadLength, 0);
}

/**
 * Sends a message with payload.
 *
 * @param socket socket on which to send the message.
 * @param methodId method that generated the message.
 * @param payloadLength payload length.
 * @param payload payload data.
 */
int sendPayloadWithRxInfo(int socket, int methodId, struct rx_info * rxInfo, int payloadLength, const char * payload)
{
	Message returnMessage;

	// Clear out the message struct
	memset(&returnMessage, 0, sizeof(Message));

	returnMessage.methodId = methodId;
	returnMessage.argument = payloadLength;
	returnMessage.payloadLength = payloadLength;

	// Copy the message and payload into a single buffer
	memcpy(rxtxBuffer, &returnMessage, sizeof(Message));
	memcpy(rxtxBuffer + sizeof(Message), rxInfo, sizeof(struct rx_info));
	memcpy(rxtxBuffer + sizeof(Message) + sizeof(struct rx_info), payload, payloadLength);

	// Send the message on the socket
	return send(socket, rxtxBuffer, sizeof(Message) + payloadLength + sizeof(struct rx_info), 0);
}

/**
 * Handles an incoming RPC call from a connected client.
 *
 */
int handleMessage(int socket, Message * message)
{
	char interfaceName[INTERFACENAME_LENGTH];
	char mac[6];
	int i;

	struct tx_info txi;

	int err, byteCount, len;
	Message returnMessage;
	memset(&returnMessage, 0, sizeof(Message));

	// Precondition
	if (message->methodId != Open && wi == NULL)
		return sendError(socket, Open, "Interface not open");

	/*
	printf("Message received\n");
	printf("\tMethod ID: %d\n", message->methodId);
	printf("\tArgument: %d\n", message->argument);
	printf("\tPayload length: %d\n", message->payloadLength);
	fflush(stdout);
	*/

	// Handle messages
	switch (message->methodId)
	{

	case Open:

		// Check if the interface name is not too long
		if (message->payloadLength > INTERFACENAME_LENGTH-1)
			return sendError(socket, Open, "Interface name exceeds maximum length");

//		printf("Payload length: %d\n", message->payloadLength);

		// Get interface name from payload
		len = message->payloadLength;
		if (len>INTERFACENAME_LENGTH-1)
			len = INTERFACENAME_LENGTH-1;
		memcpy(interfaceName, message->payload, len);
		interfaceName[len] = 0;

		// Open interface
//		printf("OPEN: [%s]\n", interfaceName);
		wi = wi_open(interfaceName);

		if (wi==NULL)
			return sendError(socket, Open, "Unable to open interface");
		else
			// Success
			return sendSuccess(socket, Open);

		break;

	case Close:

		wi_close(wi);
		wi = NULL;

		// Success
		return sendSuccess(socket, Close);

		break;

	case Read:

		// Read a frame
//		printf("\tBlocking for read ...\n");
		byteCount = wi_read(wi, wifiBuffer, WIFI_BUFFER_SIZE, &rxInfo);
//		printf("\tFrame received\n");

		if (byteCount<0)
			return sendError(socket, Read, "Unable to read from interface.");
		else
		{
			sendPayloadWithRxInfo(socket, Read, &rxInfo, byteCount, wifiBuffer);
//			printf("\tFrame sent\n");
		}

		break;

	case Write:
	
		// TODO check payload max length

		byteCount = wi_write(wi, message->payload, message->payloadLength, &txi);
		
		for (i=0; i<4; i++)
			printf("\t%02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x %02x\n",
				message->payload[i*16+0],
				message->payload[i*16+1],
				message->payload[i*16+2],
				message->payload[i*16+3],
				message->payload[i*16+4],
				message->payload[i*16+5],
				message->payload[i*16+6],
				message->payload[i*16+7],
				message->payload[i*16+8],
				message->payload[i*16+9],
				message->payload[i*16+10],
				message->payload[i*16+11],
				message->payload[i*16+12],
				message->payload[i*16+13],
				message->payload[i*16+14],
				message->payload[i*16+15]
				);
		
		printf("Bytes tx: %d\n", byteCount);

		if (byteCount<0)
			return sendError(socket, Read, "Unable to write packet to interface.");
		else
		{
			sendPayload(socket, Write, sizeof(struct tx_info), &txi);
		}
	
		break;

	case SetChannel:

		// printf("Setting channel to %d, %d\n", message->argument, wi_get_channel(wi));

		if (wi_set_channel(wi, message->argument))
			return sendError(socket, SetChannel, "Unable to set channel");
		else
			// Success
			return sendSuccess(socket, SetChannel);

		break;

	case GetChannel:

		err = wi_get_channel(wi);

		if (err == -1)
			return sendError(socket, Open, "Unable to get channel");
		else
			return sendReturnValue(socket, GetChannel, err);

		break;

	case SetFrequency:

		if (wi_set_freq(wi, message->argument))
			return sendError(socket, SetChannel, "Unable to set frequency");
		else
			// Success
			return sendSuccess(socket, SetFrequency);

		break;

	case GetFrequency:

		err = wi_get_freq(wi);

		if (err == -1)
			return sendError(socket, Open, "Unable to get frequency");
		else
			return sendReturnValue(socket, GetFrequency, err);

		break;

	case SetMac:

		if (message->payloadLength!=6)
			return sendError(socket, Open, "MAC address must be 6 bytes long");
			
		err = wi_set_mac(wi, message->payload);

		if (err == -1)
			return sendError(socket, Open, "Unable to set MAC address");
		else
			return sendReturnValue(socket, SetMac, err);

		break;

	case GetMac:
	
		err = wi_get_mac(wi, mac);

		if (err == -1)
			return sendError(socket, Open, "Unable to get MAC address");
		else
			return sendPayload(socket, GetMac, 6, mac);
	
		break;

	case SetRate:
		break;

	case GetRate:
		break;

	case GetMonitor:
		break;

	case SetMtu:
		break;

	case GetMtu:
		break;

	}

}

int main()
{

	int serverSocket;
	int i, t;

	struct sockaddr_un remote;

	int clientSocket;

	/*
	// Init aircrack
	struct wif * wi;
	struct rx_info rxi;

	wi = wi_open("mon0");

	// Channel 11
	wi_set_channel(wi, 11);
	printf("CHANNEL: %d\n", wi_get_channel(wi));
	*/

	serverSocket = setupSocket(SOCKET_ADRESS);

	if (serverSocket < 0)
	{
		exit(1);
	}

	if (listen(serverSocket, 5) == -1)
	{
		perror("listen");
		exit(1);
	}

	for (;;)
	{
		int done, n;
		printf("Waiting for a connection... on [%s]\n", SOCKET_ADRESS);

		t = sizeof(remote);
		if ((clientSocket = accept(serverSocket, (struct sockaddr *) &remote, &t)) == -1)
		{
			perror("accept");
			exit(1);
		}

		printf("Connected.\n");

		done = 0;
		do
		{

			int length = recv(clientSocket, rxtxBuffer, RXTX_BUFFER_SIZE, 0);

			if (length <= 0)
			{
				if (length < 0)
					perror("recv");

				done = 1;
				continue;
			}
			
			// Process
			handleMessage(clientSocket, (Message*)rxtxBuffer);

		} while (!done);

		close(clientSocket);
	}

	printf("EXIT.\n");

	return 0;
}

void errx(int code, const char * str)
{
}

void err(int code, const char * str)
{
}


