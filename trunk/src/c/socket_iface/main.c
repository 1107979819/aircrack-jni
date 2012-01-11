#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>

#include "message.h"

int socket_make_sockaddr_un(const char *name, struct sockaddr_un *p_addr, socklen_t *alen)
{
    memset (p_addr, 0, sizeof (*p_addr));
    size_t namelen;

    namelen  = strlen(name);

    // Test with length +1 for the *initial* '\0'.
    if ((namelen + 1) > sizeof(p_addr->sun_path))
    	return -1;

    p_addr->sun_path[0] = 0;
    memcpy(p_addr->sun_path + 1, name, namelen);

    p_addr->sun_family = AF_LOCAL;

    *alen = namelen + offsetof(struct sockaddr_un, sun_path) + 1;

    return 0;
}

/**
 * Binds a pre-created socket(AF_LOCAL) 's' to 'name'
 * returns 's' on success, -1 on fail
 *
 * Does not call listen()
 */
int socket_local_server_bind(int s, const char *name)
{
    struct sockaddr_un addr;
    socklen_t alen;
    int n;
    int err;

    err = socket_make_sockaddr_un(name, &addr, &alen);

    if (err < 0)
    {
        return -1;
    }

    n = 1;
    setsockopt(s, SOL_SOCKET, SO_REUSEADDR, &n, sizeof(n));

    if(bind(s, (struct sockaddr *) &addr, alen) < 0) {
        return -1;
    }

    return s;

}

int main()
{

	int serverSocket, s2, t;
	int i;
	char str[100];

	// Remove connection address
	struct sockaddr_un remote;
	
	// Server socket address
	struct sockaddr_un address;

	// Create
	if ((serverSocket = socket(AF_LOCAL, SOCK_STREAM, 0)) == -1)
	{
		perror("socket");
		exit(1);
	}

    int err = socket_local_server_bind(serverSocket, "echo");
    printf(">>>>>%d\n", err);

	if (err < 0)
	{
		perror("bind");
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
		if ((s2 = accept(serverSocket, (struct sockaddr *) &remote, &t)) == -1)
		{
			perror("accept");
			exit(1);
		}

		printf("Connected.\n");

		done = 0;
		do
		{
			n = recv(s2, str, 100, 0);

			Message message;

			for (i=0; i<n; i++)
				printf("%d\n", str[i]);

			memcpy(&message, str, sizeof(Message));

			printf("len: %d, %d\n", n, sizeof(Message));

			printf("Method id: %d, argument: %d, payloadLength: %d\n",
					message.methodId,
					message.argument,
					message.payloadLength
				);

			if (n <= 0)
			{
				if (n < 0)
					perror("recv");
				done = 1;
			}

			if (!done)
				if (send(s2, str, n, 0) < 0)
				{
					perror("send");
					done = 1;
				}

		} while (!done);

		close(s2);
	}

	return 0;
}
