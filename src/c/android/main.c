#include "stdio.h"
#include "osdep.h"

void errx(int code, const char * str)
{
}

void err(int code, const char * str)
{
}

void main()
{
	struct wif * wi;
	struct rx_info rxi;
	unsigned char buffer[4096];
	int i, bytesRead;
	
	// Open wireless interface

	// open
	wi = wi_open("mon0");
	
	// Channel 11
	wi_set_channel(wi, 5);
	
	// Get MAC
	char mac[6];
	int ret = wi_get_mac(wi, mac);
	printf("MAC: %02x:%02x:%02x:%02x:%02x:%02x\n", mac[0], mac[1], mac[2], mac[3], mac[4], mac[5]);

	// Receive a WiFi packet
	
	while (1)
	{
	
		bytesRead = wi_read(wi, buffer, 4096, &rxi);

		// if (buffer[0]=='T')	
		{
			printf("[%02x]", bytesRead);
			for (i=0; i<(bytesRead>40 ? 40 : bytesRead); i++)
				printf("%02x ", buffer[i]);
			printf("\n");
		}

	}
	
	// close interface
//	wi_close(wi);
}
