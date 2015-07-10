package com.ericsson.ibp.generic.utils;


/**
 * @author ejyoban
 * 
 */

public class Utils {
	private static Utils instance = null ;
	private Utils()
	{
		
	}
	
	public static Utils getInstance()
	{
		if(instance == null)
		{
			synchronized (Utils.class) {
				instance = new Utils();
			}
		}
		return instance;
	}
}


