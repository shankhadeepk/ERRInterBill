/**
 * @author ejyoban
 * 
 */
package com.ericsson.ibp.exceptions;

public class InitializationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InitializationException(){
		super();
	}
	
	public InitializationException(String message)
	{
		super(message);
	}
	
	public InitializationException(Exception ex)
	{
		super(ex);
	}

}


