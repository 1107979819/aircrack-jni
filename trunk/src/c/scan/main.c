#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>

#include "osdep.h"

#define CHANNEL_STAY_TIME 120

// Wifi rx/tx buffer
#define WIFI_BUFFER_SIZE 4096

static unsigned char wifiBuffer[WIFI_BUFFER_SIZE];

#define PROBE_FRAME_LENGTH 32

static unsigned char probe[PROBE_FRAME_LENGTH] = {
		// Frame control, duration
		0x40, 0x00, 0x00, 0x00,
		// DA (broadcast)
		0xff, 0xff, 0xff, 0xff, 0xff, 0xff,
		// SA (source address)
		0x12, 0x34, 0x56, 0x78, 0x9a, 0xbc,
		// BSSID
		0xff, 0xff, 0xff, 0xff, 0xff, 0xff,
		// Sequence Control
		0x00, 0x00,
		// SSID (empty string)
		0x00, 0x00,
		// Supported Rates
		0x01,0x04,0x02,0x04,0x0b,0x16
	};

static unsigned long millis()
{
	struct timeval tv;
	gettimeofday(&tv, NULL);

	return (tv.tv_sec) * 1000 + (tv.tv_usec) / 1000;
}

static void decode(unsigned char * frame, int length, struct rx_info * rxInfo, int channel)
{
	int i;
	char b;

	printf("%d ", (frame[0] & 0xff) >> 2);

	// BSSID
	for (i=0; i<6; i++)
		printf(i==0?"%02x":":%02x", frame[i + 10] & 0xff );

	// Channel and rss
	printf(" %d %d  %d ", rxInfo->ri_power, rxInfo->ri_channel, channel);

	// SSID
	int offset = 36;

	for (offset=36; offset<length && frame[offset+1]!=0; offset+=(frame[offset+1]&0xff))
	{
		if (frame[offset]==0)
		{
			for (i=0; i<frame[offset+1]; i++)
			{
				char c = frame[offset+2+i];
				if (c<32) c = ' ';
				if (c>128) c = ' ';
				printf("%c", c);
			}

			break;
		}
	}

	printf("\n");
}

static void scan(struct wif * wi, int channel)
{
	struct rx_info rxInfo;
	struct tx_info txInfo;

	unsigned long lastProbeTime = 0;
	int numProbes = 3;
	unsigned long probeInterval = 1;

	// Tune to channel
	wi_set_channel(wi, channel);
	
	// Receive packets
	unsigned long startTime = millis();

	unsigned long time;
	while ((time=millis())-startTime < CHANNEL_STAY_TIME)
	{
		// printf("probes: %d, %d\n", numProbes, (time-lastProbeTime) > 1);
		if (numProbes>0 && (time-lastProbeTime)>probeInterval)
		{
			// Send probe
			int byteCount = wi_write(wi, probe, PROBE_FRAME_LENGTH, &txInfo);
			// printf("probes: %d, %d, %d\n", numProbes, lastProbeTime-time, byteCount);
			lastProbeTime = time;
			numProbes--;
		}

		int byteCount = wi_read(wi, wifiBuffer, WIFI_BUFFER_SIZE, &rxInfo);

		if (byteCount>0)
		{
			int type = (wifiBuffer[0] & 0xff) >> 2;

			// Probe response, probe
			if (type==20 || type==32)
				decode(wifiBuffer, byteCount, &rxInfo, channel);

			/*
			if (type==16)
			{
				printf(">> ");
				int i;
				for (i=0; i<byteCount; i++)
					printf("%02x ", wifiBuffer[i] & 0xff);
				printf("\n");
			}
			*/

//			printf("%d bytes read: %d\n", byteCount, type);
		}
	}

}

int main()
{

	int i;
	char mac[6];

	// Init aircrack
	struct wif * wi;
	struct rx_info rxi;

	wi = wi_open("wlan0");

	// Set mac in probe
	wi_get_mac(wi, mac);
	for (i=0; i<6; i++)
	{
		probe[i+10] = mac[i];
		probe[i+16] = mac[i];
	}

	// Channel 11
	/*
	wi_set_channel(wi, 11);
	printf("CHANNEL: %d\n", wi_get_channel(wi));
	*/

	for (i=1; i<=11; i++)
		scan(wi, i);

//	printf("%02x:%02x:%02x:%02x:%02x:%02x\n", mac[0], mac[1], mac[2], mac[3], mac[4], mac[5]);

	return 0;
}

void errx(int code, const char * str)
{
}

void err(int code, const char * str)
{
}


