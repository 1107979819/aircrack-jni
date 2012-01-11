package org.tudelft.aircrack;

import org.codehaus.preon.DecodingException;
import org.tudelft.aircrack.frame.Address;
import org.tudelft.aircrack.frame.Frame;

public abstract class Interface
{

	private final static byte[] buffer = new byte[4096];

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
	 * Frame object, or null if there was an error receiving a frame. Note that this
	 * method is blocking.
	 * 
	 * @return a decoded Frame object or null in case of error. 
	 * 
	 * @throws DecodingException
	 */
	public synchronized Frame receive() throws DecodingException
	{
	
		// Create a new ReceiveInfo object
		ReceiveInfo receiveInfo = new ReceiveInfo();
		
		// Read raw packet
		int bytesRead = read(buffer, receiveInfo);
		
		// If bytesRead is returned as 0, there was a problem reading from the interface.
		// Return null in this case.
		if (bytesRead==0)
			return null;
		else
		{
			// Copy the received frame into a new buffer with the correct size
			byte[] buf = new byte[bytesRead];
			System.arraycopy(buffer, 0, buf, 0, bytesRead);
			
			// Decode the frame
			return Frame.decode(receiveInfo, buf);
		}
	}

	/**
	 * Reads a raw frame from the interface.
	 * 
	 * @param buffer byte buffer for reading into.
	 * @param receiveInfo ReceiveInfo object for collecting information about how the packet was received (RSSI, channel, etc.) 
	 * @return the length of the received frame.
	 */
	public abstract int read(byte[] buffer, ReceiveInfo receiveInfo);

	/**
	 * Writes a raw frame to the interface.
	 * 
	 * @param buffer byte buffer containing the raw frame.
	 * @param transmitInfo TransmitInfo object describing how the frame should be sent (rate).
	 * 
	 * @return the number of bytes written.
	 */
	public abstract int write(byte[] buffer, TransmitInfo transmitInfo); 

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
