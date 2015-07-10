package com.ericsson.ibp.web.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ericsson.ibp.input.adapter.ReadProcessingQueue;
import com.ericsson.ibp.process_executors.ExecutorThreadPool;
import com.ericsson.ibp.web.listeners.SpringContextHolder;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author ejyoban
 * 
 */

public class IBPAdmin extends ActionSupport{

	private static Logger logger = Logger.getLogger(IBPAdmin.class);
	
	private static final long serialVersionUID = 4664988554931197086L;
	
	private String noOfProcessors;
	
	private String maxMemory;
	
	private String totalMemory;
	
	private String freeMemory;
	
	private String pendingTasks;
	
	private String pollingStatus;
	
	private String loggingLevel;
	
	private String chosenLoggingLevel;
	
	private List<String> loggingLevels;
	
	public String showAdmin()
	{
		return SUCCESS;
	}
	
	public String changeLogger()
	{
		if("DEBUG".compareTo(this.chosenLoggingLevel)==0)
			LogManager.getRootLogger().setLevel(Level.DEBUG);
		else if("WARN".compareTo(this.chosenLoggingLevel) == 0)
			LogManager.getRootLogger().setLevel(Level.WARN);
		else if("ERROR".compareTo(this.chosenLoggingLevel) ==0)
			LogManager.getRootLogger().setLevel(Level.ERROR);
		else if("FATAL".compareTo(this.chosenLoggingLevel) ==0)
			LogManager.getRootLogger().setLevel(Level.FATAL);
		return SUCCESS;
	}
	
	public String temporarilyStopPolling()
	{
		ApplicationContext context = SpringContextHolder.getApplicationContext();
		ReadProcessingQueue readProcessingQueue = (ReadProcessingQueue)context.getBean("ReadProcessingQueue");
		if(readProcessingQueue.isShouldRun())
			readProcessingQueue.setShouldRun(false);
		else
			readProcessingQueue.setShouldRun(true);
		return SUCCESS;
	}
	
	
	
	public String execute()
	{
		logger.warn("About to start the process of gracefully shutting down IBP..");
		
		ApplicationContext context = SpringContextHolder.getApplicationContext();
		
		//first stop reading the processing queue
		ReadProcessingQueue readProcessingQueue = (ReadProcessingQueue)context.getBean("ReadProcessingQueue");
		if(readProcessingQueue != null)
		{
			logger.warn("About to stop the thread responsible for reading the processing queue..");
			readProcessingQueue.setShouldRun(false);
			readProcessingQueue.shutDown();
		}
		
		//next signal the thread pool to complete all tasks and stop.
		ExecutorThreadPool executor = (ExecutorThreadPool)context.getBean("ExecutorThreadPool");
		if(executor!=null)
		{
			logger.warn("About to stop the main thread pool");
			executor.stopThreadPool();
		}
		
		return SUCCESS;
	}

	public String getNoOfProcessors() {
		return Runtime.getRuntime().availableProcessors() + "";
	}

	public void setNoOfProcessors(String noOfProcessors) {
		this.noOfProcessors = noOfProcessors;
	}

	public String getMaxMemory() {
		return Runtime.getRuntime().maxMemory() + "";
	}

	public void setMaxMemory(String maxMemory) {
		this.maxMemory = maxMemory;
	}

	public String getTotalMemory() {
		return Runtime.getRuntime().totalMemory() + "";
	}

	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}

	public String getFreeMemory() {
		return Runtime.getRuntime().freeMemory() + "";
	}

	public void setFreeMemory(String freeMemory) {
		this.freeMemory = freeMemory;
	}
	
	public String getPendingTasks()
	{
		ExecutorThreadPool pool = (ExecutorThreadPool)SpringContextHolder.getApplicationContext().getBean("ExecutorThreadPool");
		return ":"+pool.getNoOfPendingTasks();
	}

	public String getPollingStatus() {
		ApplicationContext context = SpringContextHolder.getApplicationContext();
		ReadProcessingQueue readProcessingQueue = (ReadProcessingQueue)context.getBean("ReadProcessingQueue");
		if(readProcessingQueue.isShouldRun())
			return "RUNNING";
		else 
			return "INACTIVE";
	}

	public void setPollingStatus(String pollingStatus) {
		this.pollingStatus = pollingStatus;
	}

	public String getLoggingLevel() {
		return LogManager.getRootLogger().getLevel().toString();
	}

	public void setLoggingLevel(String loggingLevel) {
		this.loggingLevel = loggingLevel;
	}

	public String getChosenLoggingLevel() {
		return chosenLoggingLevel;
	}

	public void setChosenLoggingLevel(String chosenLoggingLevel) {
		this.chosenLoggingLevel = chosenLoggingLevel;
	}

	public List<String> getLoggingLevels() {
		loggingLevels = new ArrayList<String>();
		loggingLevels.add("DEBUG");
		loggingLevels.add("INFO");
		loggingLevels.add("ERROR");
		loggingLevels.add("FATAL");
		return loggingLevels;
	}

	public void setLoggingLevels(List<String> loggingLevels) {
		this.loggingLevels = loggingLevels;
	}
}


