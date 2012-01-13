package org.tudelft.aircrack;

import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.Order;

public class ReceiveInfo
{
	
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
				this.channel,
				this.frequency,
				this.rate,
				this.antenna
				);
	}

}
