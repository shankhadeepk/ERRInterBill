package com.ericsson.ibp.process_executors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * @author ejyoban
 * This class is responsible for creating a thread pool. The pool contains a configurable number
 * of threads. 
 * The class is a singleton (Spring managed). 
 * All interactions with this class should be managed through Spring Application context
 * This class is made spring managed so as to ease in creation of instance and starting of the pool
 */

public class ExecutorThreadPool {
	
	private static Logger logger = Logger.getLogger(ExecutorThreadPool.class);
	
	private int DEFAULT_NO_OF_THREADS = 10;
	
	private int noOfThreads;
	
	private ExecutorService threadPool;
	
	private BlockingQueue<Runnable> poolQueue;
	
	public void startThreadPool()
	{		
		
		poolQueue = new LinkedBlockingQueue<Runnable>();
		
		//create the thread pool with the required number of threads
			//we need to have a custom copnstructed pool so that we can have a reference to the
			//queue. Which will later enable us to get the pending tasks in the queue
		threadPool = new ThreadPoolExecutor(noOfThreads > 0? noOfThreads : DEFAULT_NO_OF_THREADS,
				noOfThreads > 0? noOfThreads : DEFAULT_NO_OF_THREADS,
						0L,
						TimeUnit.MILLISECONDS,
						poolQueue); 
				//Executors.newFixedThreadPool(noOfThreads > 0? noOfThreads : DEFAULT_NO_OF_THREADS);
		if(logger.isInfoEnabled())
			logger.info("The thread pool has been initialized with " + 
					(noOfThreads > 0? noOfThreads : DEFAULT_NO_OF_THREADS) + " threads");
	}
	
	public ExecutorService getPool()
	{
		return this.threadPool;
	}

	public int getNoOfThreads() {
		return noOfThreads;
	}

	public void setNoOfThreads(int noOfThreads) {
		this.noOfThreads = noOfThreads;
	}
	
	//returns the number of tasks present in the queue
	public int getNoOfPendingTasks()
	{
		return this.poolQueue.size();
	}
	
	//jyotirmoy: this method is to be used to gracefully shutdown the thread pool
	//before the method is called, ensure that the thread responsible for putting jobs in the queue is stopped first.
	public void stopThreadPool()
	{
		try{
			threadPool.shutdown();
			threadPool.awaitTermination(20, TimeUnit.MINUTES);
			logger.info("The thread pool has now been shutdown!");
		}catch(Exception ex)
		{
			logger.error("Exception while stopping the thread pool..", ex);
		}
	}
	
	public void shutDown(){
		this.threadPool.shutdown();
	}
	
	public void shutDownNow(){
		this.threadPool.shutdownNow();
	}
	
}


