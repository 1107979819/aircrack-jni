package org.tudelft.parse80211.socket;

@SuppressWarnings("serial")
public class InterfaceException extends RuntimeException
{
	
	public InterfaceException(Exception ex)
	{
		super(ex);
	}
	
	public InterfaceException(String message)
	{
		super(message);
	}

}
