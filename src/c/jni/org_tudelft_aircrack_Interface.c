#include <stdlib.h>
#include <jni.h>

#include "org_tudelft_aircrack_Interface.h"
#include "osdep.h"

JNIEXPORT jlong JNICALL Java_org_tudelft_aircrack_Interface__1open(JNIEnv * env, jclass class, jstring interfaceName)
{
	// Interface pointer
	struct wif * wi;

	// Interface to open
	const char * iface = (*env)->GetStringUTFChars(env, interfaceName, 0);

	wi = wi_open((char*)iface);

	// Success
	return (jlong)(void*)wi;
}

JNIEXPORT void JNICALL Java_org_tudelft_aircrack_Interface__1close(JNIEnv * env, jclass class, jlong wifPointer)
{
	struct wif * wi = (struct wif*)wifPointer;

	wi_close(wi);
}

void setIntField(JNIEnv * env, jobject object, const char * fieldName, jint value)
{
	jclass class = (*env)->GetObjectClass(env, object);
	jfieldID fid = (*env)->GetFieldID(env, class, fieldName, "I");

	(*env)->SetIntField(env, object, fid, value);
}

void setLongField(JNIEnv * env, jobject object, const char * fieldName, jlong value)
{
	jclass class = (*env)->GetObjectClass(env, object);
	jfieldID fid = (*env)->GetFieldID(env, class, fieldName, "J");

	(*env)->SetLongField(env, object, fid, value);
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1read(JNIEnv * env, jclass class, jlong wifPointer, jbyteArray bufferArray, jobject receiveInfo)
{

	int bytesRead;
	struct wif * wi = (struct wif*)wifPointer;
	struct rx_info rxi;

	jsize bufferSize = (*env)->GetArrayLength(env, bufferArray);

	// Allocate a temporary receive buffer
	unsigned char *buffer = (unsigned char*)malloc(bufferSize);

	// Receive a WiFi packet
	bytesRead = wi_read(wi, buffer, bufferSize, &rxi);

	// Copy the received data to the java byte array
	(*env)->SetByteArrayRegion(env, bufferArray, 0, bufferSize, buffer);

	// Free buffer
	free(buffer);

	// Copy the RX info into the ReceiverInfo object
	setLongField(env, receiveInfo, "macTime", (jlong)rxi.ri_mactime);
	setIntField(env, receiveInfo, "power", (jint)rxi.ri_power);
	setIntField(env, receiveInfo, "noise", (jint)rxi.ri_noise);
	setIntField(env, receiveInfo, "channel", (jint)rxi.ri_channel);
	setIntField(env, receiveInfo, "frequency", (jint)rxi.ri_freq);
	setIntField(env, receiveInfo, "rate", (jint)rxi.ri_rate);
	setIntField(env, receiveInfo, "antenna", (jint)rxi.ri_antenna);

	return bytesRead;
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1write(JNIEnv * env, jclass class, jlong wifPointer, jbyteArray bufferArray, jobject object)
{
	struct wif * wi = (struct wif*)wifPointer;
	struct tx_info txi;

	jsize bufferSize = (*env)->GetArrayLength(env, bufferArray);

	// Allocate a temporary transmit buffer and copy packet into it
	unsigned char *buffer = (unsigned char*)malloc(bufferSize);
	(*env)->GetByteArrayRegion(env, bufferArray, 0, bufferSize, buffer);
	
	// Transmit packet
	int ret = wi_write(wi, buffer, bufferSize, &txi);

	// Free temporary buffer
	free(buffer);

	return ret;
}


JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1setChannel(JNIEnv * env, jclass class, jlong wifPointer, jint channel)
{
	struct wif * wi = (struct wif*) wifPointer;
	return wi_set_channel(wi, channel);
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getChannel(JNIEnv * env, jclass class, jlong wifPointer)
{
	struct wif * wi = (struct wif*) wifPointer;
	return wi_get_channel(wi);
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1setFrequency(JNIEnv * env, jclass class, jlong wifPointer, jint frequency)
{
	struct wif * wi = (struct wif*) wifPointer;
	return wi_set_freq(wi, frequency);
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getFrequency(JNIEnv * env, jclass class, jlong wifPointer)
{
	struct wif * wi = (struct wif*) wifPointer;
	return wi_get_freq(wi);
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1setMac(JNIEnv * env, jclass class, jlong wifPointer, jbyteArray macArray)
{
	struct wif * wi = (struct wif*) wifPointer;
	char mac[6];

	// Copy byte array contents
	(*env)->GetByteArrayRegion(env, macArray, 0, 6, mac);

	return wi_set_mac(wi, mac);
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getMac(JNIEnv * env, jclass class, jlong wifPointer, jbyteArray macArray)
{
	struct wif * wi = (struct wif*) wifPointer;
	char mac[6];

	// Get mac
	int ret = wi_get_mac(wi, mac);

	// Copy to byte array
	(*env)->SetByteArrayRegion(env, macArray, 0, 6, mac);

	return ret;
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1setRate(JNIEnv * env, jclass class, jlong wifPointer, jint rate)
{
	struct wif * wi = (struct wif*) wifPointer;
	return wi_set_rate(wi, rate);
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getRate(JNIEnv * env, jclass class, jlong wifPointer)
{
	struct wif * wi = (struct wif*) wifPointer;
	return wi_get_rate(wi);
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getMonitor(JNIEnv * env, jclass class, jlong wifPointer)
{
	struct wif * wi = (struct wif*) wifPointer;
	return wi_get_monitor(wi);
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1setMtu(JNIEnv * env, jclass class, jlong wifPointer, jint mtu)
{
	struct wif * wi = (struct wif*) wifPointer;
	return wi_set_mtu(wi, mtu);
}

JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getMtu(JNIEnv * env, jclass class, jlong wifPointer)
{
	struct wif * wi = (struct wif*) wifPointer;
	return wi_get_mtu(wi);
}
