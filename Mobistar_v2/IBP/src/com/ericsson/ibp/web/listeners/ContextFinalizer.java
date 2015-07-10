package com.ericsson.ibp.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.ericsson.ibp.input.adapter.ReadProcessingQueue;
import com.ericsson.ibp.process_executors.ExecutorThreadPool;

/**
 * @author ejyoban
 * This listener is as such unnecessary. 
 * However, it has been observed that threads that are started, are not getting killed in due time, during a
 * server restart.
 * So, to ensure there are no memory leaks, we have this listener.
 * Please note, this wont be necessary if we are going for a server shutdown.
 */

public class ContextFinalizer implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ContextFinalizer.class);

    public void contextInitialized(ServletContextEvent sce) {
    }
    

    public void contextDestroyed(ServletContextEvent sce) {
//        Enumeration<Driver> drivers = DriverManager.getDrivers();
//        Driver d = null;
//        while(drivers.hasMoreElements()) {
//            try {
//                d = drivers.nextElement();
//                DriverManager.deregisterDriver(d);
//                LOGGER.warn(String.format("Driver %s deregistered", d));
//            } catch (SQLException ex) {
//                LOGGER.warn(String.format("Error deregistering driver %s", d), ex);
//            }
//        }
//    	//jyoti: the above code should not be necessary, as spring is taking care of it
//        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
//        for(Thread t:threadArray) {
////        	synchronized(t) {
////            	LOGGER.warn("Killing thread " + t.getName());
////                t.stop(); 
////            }
//            if(t.getName().contains("pool-")) {
//                synchronized(t) {
//                	LOGGER.warn("Killing thread " + t.getName());
//                    t.stop(); 
//                }
//            }
//        }
    	//immediately shutdown the thread pools - Note, this is not the preffered way to shutdown the app
    		//but we should close the thread pools when tomcat shuts down
    	ReadProcessingQueue processingQueue = (ReadProcessingQueue)SpringContextHolder
    												.getApplicationContext().getBean("ReadProcessingQueue");
    	processingQueue.shutDownNow();
    	System.out.println("ReadProcessingQueue is now shutdown");
    	
    	ExecutorThreadPool mainThreadPool = (ExecutorThreadPool)SpringContextHolder
    											.getApplicationContext().getBean("ExecutorThreadPool");
    	mainThreadPool.shutDownNow();
    }

}

