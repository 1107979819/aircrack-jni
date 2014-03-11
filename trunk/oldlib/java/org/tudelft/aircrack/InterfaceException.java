package org.tudelft.aircrack;

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
