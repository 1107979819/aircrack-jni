/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_tudelft_aircrack_Interface */

#ifndef _Included_org_tudelft_aircrack_Interface
#define _Included_org_tudelft_aircrack_Interface
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _open
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_org_tudelft_aircrack_Interface__1open
  (JNIEnv *, jclass, jstring);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_tudelft_aircrack_Interface__1close
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _read
 * Signature: (J[BLorg/tudelft/aircrack/ReceiveInfo;)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1read
  (JNIEnv *, jclass, jlong, jbyteArray, jobject);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _write
 * Signature: (J[BLorg/tudelft/aircrack/TransmitInfo;)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1write
  (JNIEnv *, jclass, jlong, jbyteArray, jobject);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _setChannel
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1setChannel
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _getChannel
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getChannel
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _setFrequency
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1setFrequency
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _getFrequency
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getFrequency
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _setMac
 * Signature: (J[B)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1setMac
  (JNIEnv *, jclass, jlong, jbyteArray);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _getMac
 * Signature: (J[B)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getMac
  (JNIEnv *, jclass, jlong, jbyteArray);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _setRate
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1setRate
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _getRate
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getRate
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _getMonitor
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getMonitor
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _setMtu
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1setMtu
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     org_tudelft_aircrack_Interface
 * Method:    _getMtu
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_tudelft_aircrack_Interface__1getMtu
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif