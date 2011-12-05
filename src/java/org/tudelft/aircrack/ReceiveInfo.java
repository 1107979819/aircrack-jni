package org.tudelft.aircrack;

public class ReceiveInfo
{
	
	private long macTime;
	private int power;
	private int noise;
	private int channel;
	private int frequency;
	private int rate;
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
