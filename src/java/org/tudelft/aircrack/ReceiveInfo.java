package org.tudelft.aircrack;

import org.codehaus.preon.annotation.BoundNumber;

public class ReceiveInfo
{
	
	@BoundNumber(size="64")
	private long macTime;

	@BoundNumber(size="32")
	private int power;

	@BoundNumber(size="32")
	private int noise;

	@BoundNumber(size="32")
	private int channel;

	@BoundNumber(size="32")
	private int frequency;

	@BoundNumber(size="32")
	private int rate;
	
	@BoundNumber(size="32")
	private int antenna;
	
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
