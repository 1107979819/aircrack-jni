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

#define SOCKET_ADRESS "echo"
#define RX_BUFFER_SIZE 5000

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

void handleMessage(struct wif * wi, Message * message)
{

	switch (message->methodId)
	{

	case Read:
		break;

	case Write:
		break;

	case SetChannel:

		wi_set_channel(wi, message->argument);

		break;

	case GetChannel:
		break;

	case SetFrequency:
		break;

	case GetFrequency:
		break;

	case SetMac:
		break;

	case GetMac:
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

	char receiveBuffer[RX_BUFFER_SIZE];

	int clientSocket;

	// Init aircrack
	struct wif * wi;
	struct rx_info rxi;

	wi = wi_open("mon0");

	// Channel 11
	wi_set_channel(wi, 11);
	printf("CHANNEL: %d\n", wi_get_channel(wi));

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
		printf("Waiting for a connection... on [echo]\n");

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

			int length = recv(clientSocket, receiveBuffer, RX_BUFFER_SIZE, 0);

			if (n <= 0)
			{
				if (n < 0)
					perror("recv");

				done = 1;
				continue;
			}

			// Unpack message
			Message message;
			memcpy(&message, receiveBuffer, sizeof(Message));

			handleMessage(wi, &message);


		} while (!done);

		close(clientSocket);
	}

	printf("EXIT.\n");

	return 0;
}
