package org.tudelft.aircrack;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.Order;

public class ReceiveInfo
{
	
	private final static int minChannel = 1;
	private final static int maxChannel = 13;

	@BoundNumber(size="64")
	@Order(1) public long macTime;

	@BoundNumber(size="32")
	@Order(2) public int power;

	@BoundNumber(size="32")
	@Order(3) public int noise;

	@BoundNumber(size="32")
	@Order(4) public int channel;

	@BoundNumber(size="32")
	@Order(5) public int frequency;

	@BoundNumber(size="32")
	@Order(6) public int rate;
	
	@BoundNumber(size="32")
	@Order(7) public int antenna;
	
	public ReceiveInfo()
	{
	}
	
	public long getMacTime()
	{
		return macTime;
	}
	
	public int getPower()
	{
		return power;
	}
	
	public int getNoise()
	{
		return noise;
	}
	
	public int getChannel()
	{
		return channel;
	}
	
	/**
	 * The channel returned by aircrack in the receive information struct
	 * tends to be wrong. For example, channel 11 is sometimes reported
	 * as 158. This method computes the most likely channel. 
	 * 
	 * Use <code>getChannel()</code> to get the 'raw' value. 
	 * 
	 * @return
	 */
	public int getFixedChannel()
	{
		if (channel>=minChannel && channel<=maxChannel)
			return channel;
		else
			return (channel - 103) / 5;
	}
	
	
	public int getFrequency()
	{
		return frequency;
	}
	
	public int getRate()
	{
		return rate;
	}

	public int getAntenna()
	{
		return antenna;
	}
	
	@Override
	public String toString()
	{
		return String.format("[macTime=%d power=%d noise=%d channel=%d frequency=%d rate=%d antenna=%d]", 
				this.macTime,
				this.power,
				this.noise,
				this.getFixedChannel(),
				this.frequency,
				this.rate,
				this.antenna
				);
	}

}
