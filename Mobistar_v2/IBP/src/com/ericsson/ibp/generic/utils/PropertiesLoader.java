package com.ericsson.ibp.generic.utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ericsson.ibp.constants.Constants;

/**
 * @author ejyoban
 * 
 */

///singleton class responsible for loading properties from a properties file
///provides utility methods for reading properties

public class PropertiesLoader {
	
	private static PropertiesLoader instance;
	
	private Properties properties;
	
	final static Logger logger = Logger.getLogger(PropertiesLoader.class.getName());
	
	public static PropertiesLoader getInstance()
	{
		if(instance == null)
		{
			synchronized (PropertiesLoader.class) {
				instance = new PropertiesLoader();
			}
		}
		return instance;
	}
	
	private PropertiesLoader()
	{
		super();
		properties = new Properties();
		try{
			properties.load(new FileInputStream(Constants.APP_CONFIG_LOCATION));
			if(logger.isInfoEnabled())
	    		logger.info("Application properties loaded successfully..");
		}catch(Exception ex)
		{
			logger.warn("Exception while loading configuration / properties file, continuing with default properties..");
		}
	}
	
	public String getProperty(String key)
	{
		return properties.getProperty(key);
	}
	
}


