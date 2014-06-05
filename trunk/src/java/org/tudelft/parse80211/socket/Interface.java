package org.tudelft.parse80211.socket;

import java.io.IOException;

import org.tudelft.parse80211.frames.Frame;
import org.tudelft.parse80211.types.Address;

public abstract class Interface
{
	
	/**
	 * Opens the interface. This method must be called before any other operations
	 * may be performed.
	 */
	public abstract void open();

	/**
	 * Closes the interface. After this method has been called, no other operations
	 * may be performed on this interface
	 */
	public abstract void close();
	
	/**
	 * Receives a single 802.11 Frame. This method either returns a fully decoded 
	 * Frame object or null if there is no frame to receive. This method is non-blocking.
	 * 
	 * @return a decoded Frame object or null. 
	 */
	public synchronized Frame receive()
	{
		return null;
	}
	
	public synchronized void send(Frame frame) throws IOException
	{
	}

	/*
	public synchronized TransmitInfo send(byte[] data, int length) throws IOException
	{
		
		TransmitInfo transmitInfo = new TransmitInfo();
		
		write(data, length, transmitInfo);
		
		return transmitInfo;
	}
	*/

//	/**
//	 * Reads a raw frame from the interface.
//	 * 
//	 * @param buffer byte buffer for reading into.
//	 * @param receiveInfo ReceiveInfo object for collecting information about how the packet was received (RSSI, channel, etc.) 
//	 * @return the length of the received frame.
//	 */
//	public abstract int read(byte[] buffer, ReceiveInfo receiveInfo);

	/**
	 * Writes a raw frame to the interface.
	 * 
	 * @param buffer byte buffer containing the raw frame.
	 * @param length number of bytes to write.
	 * @param transmitInfo TransmitInfo object describing how the frame should be sent (rate).
	 * 
	 * @return the number of bytes written.
	 */
	// public abstract int write(byte[] buffer, int length, TransmitInfo transmitInfo); 

	/**
	 * Sets the channel of the interface.
	 * 
	 * @param channel 802.11 channel. 
	 */
	public abstract void setChannel(int channel);
	
	/**
	 * Gets the channel of the interface.
	 * 
	 * @return channel 802.11 channel. 
	 */
	public abstract int getChannel();

	/**
	 * Sets the frequency of the interface.
	 * 
	 * @param frequency frequency. 
	 */
	public abstract void setFrequency(int freq);
	
	/**
	 * Gets the frequency of the interface.
	 * 
	 * @return frequency. 
	 */
	public abstract int getFrequency();

	/**
	 * Sets the MAC address of the interface.
	 * 
	 * @param address MAC address. 
	 */
	public abstract void setMac(Address address);
	
	/**
	 * Gets the MAC address of the interface.
	 * 
	 * @return MAC address. 
	 */
	public abstract Address getMac();

	/**
	 * Sets the rate of the interface.
	 * 
	 * @param rate rate 
	 */
	public abstract void setRate(int rate);
	
	/**
	 * Gets the rate of the interface.
	 * 
	 * @return rate 
	 */
	public abstract int getRate();

	/**
	 * Gets the monitor of the interface.
	 * 
	 * @return monitor of the interface.
	 */
	public abstract int getMonitor();
	
	/**
	 * Sets the MTU (maximum transmission unit) of the interface.
	 * 
	 * @param mtu maximum transmission unit in bytes
	 */
	public abstract void setMtu(int mtu);
	
	/**
	 * Gets the MTU (maximum transmission unit) of the interface.
	 * 
	 * @return maximum transmission unit in bytes
	 */
	public abstract int getMtu();

}
