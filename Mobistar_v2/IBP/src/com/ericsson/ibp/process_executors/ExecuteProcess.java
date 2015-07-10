package com.ericsson.ibp.process_executors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ericsson.ibp.configurationDB.Workflow;
import com.ericsson.ibp.configurationDB.WorkflowAction;
import com.ericsson.ibp.configurationDB.WorkflowDetails;
import com.ericsson.ibp.data.model.ActionActivity;
import com.ericsson.ibp.data.model.IBPInputBean;
import com.ericsson.ibp.exceptions.DataWritingException;
import com.ericsson.ibp.generic.utils.DBConnectionManager;
import com.ericsson.ibp.processor.orderline.ProcessorInt;
import com.ericsson.ibp.web.listeners.SpringContextHolder;

/**
 * @author ejyoban
 * These are the individual threads that process each IBP request of the IBP.PROCESSING_QUEUE
 * 
 */


public class ExecuteProcess implements Callable<Boolean> {
	
	private static Logger logger = Logger.getLogger(ExecuteProcess.class);

	private IBPInputBean input;
	
	private WorkflowDetails workflowDetails;
	
	private static int i = 0;
	
	private String activity=null;
	
	Connection conn = null;
	
	PreparedStatement pstmt = null; 
	
//	public Boolean call()
//	{
//		//logger.debug("At the beginning.." + i);
//		try{
//			Thread.sleep(1000);
//		}catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		//logger.debug("At the end..");
//		return true;
//	}

	//First get the execution logic, and then execute the processes
	@Override
	public Boolean call()  {
		try{
			ApplicationContext appContext = SpringContextHolder.getApplicationContext();
			
			ActionActivity acc =(ActionActivity)appContext.getBean("action");
			
			//first determine the processing logic applicable
			workflowDetails = Workflow.getInstance().getWorkflow(this.input);
			
			activity=acc.getActionMap().get(input.getBscs_action());
			
			if(activity.equals("Activation")) 
				goForActivation(input);
			else
				goForNormalAction(input,activity);

			//TODO: complete the log
			if(logger.isDebugEnabled())
				logger.debug("Execution logic determined for BSCS Action ");
			
			//process MBC events in sequence
			if(workflowDetails.getMBCImpactIndicator() != -1)	//-1 indicates no action necessary
			{
				List<WorkflowAction> mbcActionSeq = workflowDetails.getMBCActionSequence();
				for(WorkflowAction action : mbcActionSeq)
				{
					//the processor names match the action names that are registered in the configuration tables
					//these processors are again registered with the same name in the spring context
					ProcessorInt processor = (ProcessorInt)appContext.getBean(action.getAction());
					//process the logic and add the output to the list
					input.getMbcCommands().put(action.getAction(), processor.executeProcess(this.input));
				}
			}

			//process MZ events in sequence
			if(workflowDetails.getMZImpactIndicator() != -1)	//-1 indicates no action necessary
			{
				List<WorkflowAction> mzActionSeq = workflowDetails.getMZActionSequence();
				for(WorkflowAction action : mzActionSeq)
				{
					ProcessorInt processor = (ProcessorInt)appContext.getBean(action.getAction());
					input.getMzCommands().put(action.getAction(), processor.executeProcess(this.input));
				}
			}

			//process PCRF events in sequence
			if(workflowDetails.getPCRFImpactIndicator() != -1)	
			{
				List<WorkflowAction> pcrfActionSeq = workflowDetails.getPCRFActionSequence();
				for(WorkflowAction action : pcrfActionSeq)
				{
					ProcessorInt processor = (ProcessorInt)appContext.getBean(action.getAction());
					input.getPcrfCommands().put(action.getAction(), processor.executeProcess(this.input));
				}
			}
			
			//now write the outputs
			this.writeOutputs();
			
		}catch(DataWritingException dwe)
		{
			//do error handling with respect to data base errors
		}
		catch(Throwable t)	//do other error handlings
		{
			logger.warn("Exception while processing in parallel thread " + t.getMessage());
		}
		return null;
	}

	public IBPInputBean getInput() {
		return input;
	}

	public void setInput(IBPInputBean input) {
		this.input = input;
	}
	
	private void writeOutputs() throws DataWritingException
	{
		//the method is used to write all the outputs in one go
			//obtain a single connection to the Oracle 11 DB, and then use that connection to 
			//write to each table. At the end fire a single commit
	}
	
	private void goForActivation(IBPInputBean input) {
		/*String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
		try {
            
            Class.forName(jdbcDriver);
            
            
            
        } catch (ClassNotFoundException e) {
        	logger.error("Oracle driver class not found.",e);
        	 //return null;
        }*/
		
		//logger.info("JDBC driver loading done.");
		
		String sql = "SELECT * FROM IBP_PRODUCT_CONFIGURATION IPC WHERE IPC.TMCODE = ? AND   IPC.SNCODE = ?";
		PreparedStatement ps;
		try {
			ps = DBConnectionManager.getConnection().prepareStatement(sql);
			ps.setLong(1, input.getTmcode());
			ps.setLong(1, input.getSncode());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				
				return rs.getString(1);
			else
				logger.error("result not found for TMCODE"+input.getTmcode()+" and SNCODE"+input.getSncode());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	private void goForNormalAction(IBPInputBean input,String activity){
		String bscsAction=input.getBscs_action();
		
		
		
		determineWorkFlow()
		
	}
}


