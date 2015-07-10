package com.ericsson.ibp.input.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.ericsson.ibp.data.model.IBPInputBean;
import com.ericsson.ibp.process_executors.ExecuteProcess;
import com.ericsson.ibp.process_executors.ExecutorThreadPool;
import com.ericsson.ibp.web.listeners.SpringContextHolder;

/**
 * @author ejyoban
 * This class is responsible for actually polling the PROCESSING_QUEUE table to check 
 * if there are any new entries to be processed
 * The polling method is run by a quartz scheduler
 */

public class ReadProcessingQueue implements Runnable{
	
	private static Logger logger = Logger.getLogger(ReadProcessingQueue.class);
	
	private DataSource dataSource;
	
	//used to temporarily disable reading of the processing queue in case of heavy load 
	private boolean shouldRun = true;
	
	private ScheduledExecutorService pollingThreadPool;
	
	private long pollingInterval;
	
	private long initialDelay;
	
	public void startPolling()
	{
		pollingThreadPool = Executors.newScheduledThreadPool(1);	//only 1 thread must be used to poll
		pollingThreadPool.scheduleAtFixedRate(this, this.initialDelay, 
				this.pollingInterval, TimeUnit.MILLISECONDS);
	}
	
	
	@Override
	public void run()
	{
		ExecutorThreadPool threadPool = (ExecutorThreadPool)SpringContextHolder.
											getApplicationContext().getBean("ExecutorThreadPool"); 
		
		if(shouldRun)
		{
			try{
				if(logger.isInfoEnabled())
					logger.info("About to poll to check if there are any new jobs that are to be processed");
				Thread.sleep(10000);
				//get the new set of entries to be processed
				List<IBPInputBean> newEntries = this.checkQueue();
				
				ExecuteProcess processor;
				for(IBPInputBean newEntry : newEntries)
				{
					processor = new ExecuteProcess();	//create a Callable instance
					processor.setInput(newEntry);		//set the bean as input to the Callable instance
					threadPool.getPool().submit(processor);	//submit the Callable instance to the pool
				}
//				for(int i = 0; i < 15; i++)
//				{
//					processor = new ExecuteProcess();	//create a Callable instance
//					//processor.setInput(newEntry);		//set the bean as input to the Callable instance
//					threadPool.getPool().submit(processor);	//submit the Callable instance to the pool
//				}
			
			}catch(Exception ex)
			{
				ex.printStackTrace();
				logger.warn("Exception while the input thread went to sleep. The thread must continue. " + ex.getMessage());
			}
		}else{
			logger.warn("Reading of the Processing Queue is now disabled, check the admin console to start polling once again");
		}
	}
	
	private List<IBPInputBean> checkQueue()
	{
		//TODO: check the table and create IBPInputBean objects
		return new ArrayList<IBPInputBean>();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean isShouldRun() {
		return shouldRun;
	}

	public void setShouldRun(boolean shouldRun) {
		this.shouldRun = shouldRun;
		logger.warn("Reading of the Processing Queue is now disabled, check the admin console to start polling once again");
	}

	public ScheduledExecutorService getPollingThreadPool() {
		return pollingThreadPool;
	}

	public void setPollingThreadPool(ScheduledExecutorService pollingThreadPool) {
		this.pollingThreadPool = pollingThreadPool;
	}

	public long getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(long pollingInterval) {
		this.pollingInterval = pollingInterval;
	}
	
	
	public void shutDown(){
		this.pollingThreadPool.shutdown();
	}
	
	public void shutDownNow(){
		this.pollingThreadPool.shutdownNow();
	}

	public long getInitialDelay() {
		return initialDelay;
	}

	public void setInitialDelay(long initialDelay) {
		this.initialDelay = initialDelay;
	}
	
}


