package com.ericsson.ibp.exceptions;

/**
 * @author ejyoban
 * 
 */

public class DataWritingException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public DataWritingException(){
		super();
	}
	
	public DataWritingException(String message)
	{
		super(message);
	}
	
	public DataWritingException(Exception ex)
	{
		super(ex);
	}
}


