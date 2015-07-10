/**
 * @author ejyoban
 * 
 */
package com.ericsson.ibp.generic.utils;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ericsson.ibp.constants.Constants;

public class LoggerInitializer{
	
	//the relative path after WEB-INF for the log4j.properties file
	private static String RELATIVE_PATH = "/WEB-INF/config/log4j.properties";
	
	///the class constructor does nothing more than loading the log configuration
	public LoggerInitializer()
	{
		try
		{
			File logConfig = new File(Constants.LOG_CONFIG_LOCATION);
		    if(logConfig.exists()){
		    	PropertyConfigurator.configure(logConfig.getAbsolutePath());
		    	Logger logger = Logger.getLogger(LoggerInitializer.class.getName());
		    	if(logger.isInfoEnabled())
		    		logger.info("Logger configured successfully..");
		    }else
		    {
		    	//the log4j.properties was not found at the configured path
		    		//now load the configuration from the /WEB-INF/config location
		    	String absolutePath=new File(Thread.currentThread().getContextClassLoader().getResource("").getFile()).getParentFile().getParentFile().getPath();
				File defaultConfig = new File(absolutePath + RELATIVE_PATH);
				if(defaultConfig.exists())
					PropertyConfigurator.configure(defaultConfig.getAbsolutePath());
				Logger logger = Logger.getLogger(LoggerInitializer.class.getName());
		    	logger.warn("Logger has been configured with the basis of default configuration..");
		    }
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}


