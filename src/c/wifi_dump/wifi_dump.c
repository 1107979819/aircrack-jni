#include <stdio.h>
#include <time.h>
#include <sys/time.h>
#include "osdep.h"

void errx(int code, const char * str)
{
}

void err(int code, const char * str)
{
}

unsigned long millis()
{
	struct timeval tv;
	gettimeofday(&tv, NULL);
	return tv.tv_sec * 1000 + tv.tv_usec / 1000;
}

void dump(FILE * fp, unsigned char * data, int length)
{
	fwrite(data, 1, length, fp);
}

void main()
{
	static FILE * traceFile;

	struct wif * wi;
	struct rx_info rxi;
	unsigned char buffer[4096];
	int i, bytesRead;
	
	// Open trace file
	traceFile = fopen("wifi.log", "wb");

	// Open wireless interface
	wi = wi_open("mon0");
	
	// Channel 11
	wi_set_channel(wi, 11);
	printf("CHANNEL: %d\n", wi_get_channel(wi));
	
	// Get MAC
	char mac[6];
	int ret = wi_get_mac(wi, mac);
	printf("MAC: %02x:%02x:%02x:%02x:%02x:%02x\n", mac[0]&0xff, mac[1]&0xff, mac[2]&0xff, mac[3]&0xff, mac[4]&0xff, mac[5]&0xff);

	// Receive a WiFi packet
	
	while (1)
	{
	
		bytesRead = wi_read(wi, buffer, 4096, &rxi);

		// Magic
		unsigned char * magic = "MAGIC";
		dump(traceFile, magic, 5);

		// Dump timestamp
		unsigned long time = millis();
		dump(traceFile, (unsigned char*)&time, sizeof(unsigned long));

		// Dump RX info
		dump(traceFile, (unsigned char*)&rxi, sizeof(struct rx_info));

		// Dump current channel
		int channel = wi_get_channel(wi);
		dump(traceFile, (unsigned char*)&channel, sizeof(int));

		// Dump payload length
		dump(traceFile, (unsigned char*)&bytesRead, sizeof(int));

		// Dump packet
		dump(traceFile, buffer, bytesRead);

	}
	// never gets here :)

	// Close trace file
	fclose(traceFile);
	
	// Close interface
	wi_close(wi);
}
