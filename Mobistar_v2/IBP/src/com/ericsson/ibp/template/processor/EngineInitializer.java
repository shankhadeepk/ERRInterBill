package com.ericsson.ibp.template.processor;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

/**
 * @author ejyoban
 *used to initialize the Velocity Engine
 */

public class EngineInitializer {
	private static Logger logger = Logger.getLogger(EngineInitializer.class);
	
	private static String RELATIVE_PATH_FOR_TEMPLATES = "/WEB-INF/config/templates";
	
	public void initializeEngine()
	{
		if(logger.isDebugEnabled())
			logger.debug("About to initialize the Velocity Enging");
		Properties p = new Properties();
		String absolutePath=new File(Thread.currentThread().getContextClassLoader().getResource("").getFile()).getParentFile().getParentFile().getPath();//this goes to webapps directory
		p.put("file.resource.loader.path", absolutePath + RELATIVE_PATH_FOR_TEMPLATES);
		//configure the velocity logger to use the default logging
		p.put( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute" );
		
		
//		// Cannot find template with this:
//		p.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//		p.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
//		p.setProperty("resourceLoaderPath", "/WEB-INF/config");

		Velocity.init(p);
		if(logger.isInfoEnabled())
			logger.info("The velocity engine is now initialized..");
	}
}


