package com.ericsson.ibp.configurationDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.ericsson.ibp.constants.Constants;
import com.ericsson.ibp.data.model.IBPInputBean;
import com.ericsson.ibp.generic.utils.DynamicUtils;

/**
 * @author ejyoban
 * The workflowMap corresponds to IBP_WORKFLOW_DEFINITION
 * For a given combination of BSCS_ACTION and PRODUCT_ID the corresponding workflow is mapped
 */

public class Workflow{

	private static Logger logger = Logger.getLogger(Workflow.class);

	private Map<String, WorkflowDetails> workflowMap = new HashMap<String, WorkflowDetails>();

	private DataSource datasource;

	private DynamicUtils utils;


	// used to read the contents of IBP_WORKFLOW_CONFIGURATION
	private void initializeAllWorkflows()
	{
		if(logger.isDebugEnabled())
			logger.debug("About to load the Workflow configuration from database..");

		String readWorkflowConfiguration = "select * from " + Constants.IBP_WORKFLOW_CONFIGURATION ;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		WorkflowDetails workflowDetails = null;
		try{
			connection = datasource.getConnection();
			statement = connection.prepareStatement(readWorkflowConfiguration);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				workflowDetails = new WorkflowDetails(resultSet.getInt("ACTION_ID"),
						resultSet.getInt("PRODUCT_TYPE_ID"),
						resultSet.getInt("MBC_WORKFLOW_ID"),
						resultSet.getInt("MZ_WORKFLOW_ID"),
						resultSet.getInt("PCRF_WORKFLOW_ID"));
				workflowMap.put(workflowDetails.getKey(), workflowDetails);
			}

		}catch(Exception ex){
			logger.error("Error while reading the Workflow configuration", ex);
			//TODO:: Throw custom exception so that the application acts accordingly
		}finally{
			utils.finalizeDBConnection(resultSet, statement, connection);
		}
		this.initializeSubflows();
		if(logger.isDebugEnabled())
			logger.debug("The workflow details are loaded as :" + this);
			System.out.println(this);
	}

	//initialize the details of each workflow
	public void initializeSubflows()
	{
		String readWorkflowDefinition = "select * from " + Constants.IBP_WORKFLOW_DEFINITION + 
				" where workflow_id in (?,?,?) order by application_name,seq";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try{
			connection = datasource.getConnection();
			statement = connection.prepareStatement(readWorkflowDefinition);
			WorkflowAction action = null;
			for(Entry<String, WorkflowDetails> entry: workflowMap.entrySet()){
				statement.setInt(1, entry.getValue().getPCRFImpactIndicator());
				statement.setInt(2, entry.getValue().getMBCImpactIndicator());
				statement.setInt(3, entry.getValue().getMZImpactIndicator());
				resultSet = statement.executeQuery();
				while(resultSet.next()){
					action = new WorkflowAction(resultSet.getString("APPLICATION_NAME"), 
							resultSet.getInt("WORKFLOW_ID"), 
							resultSet.getString("NE_ACTIONS"), 
							resultSet.getInt("SEQ"));
					if(action.getApplication() != null)
						if(action.getApplication().compareToIgnoreCase("MBC") == 0)
							entry.getValue().getMBCActionSequence().add(action);
						else if(action.getApplication().compareToIgnoreCase("PCRF") == 0)
							entry.getValue().getPCRFActionSequence().add(action);
						else if(action.getApplication().compareTo("MZ") == 0)
							entry.getValue().getMZActionSequence().add(action);
				}
			}
		}catch(Exception ex){
			logger.error("Error while reading the Workflow configuration", ex);
			//TODO:: Throw custom exception so that the application acts accordingly
		}finally{
			utils.finalizeDBConnection(resultSet, statement, connection);
		}
	}


	public WorkflowDetails getWorkflow(IBPInputBean input)
	{
		//TODO: check the map and retrun the WorkFlowDetails object
		return null;
	}

	public Map<String, WorkflowDetails> getWorkflowMap() {
		return workflowMap;
	}

	public void setWorkflowMap(Map<String, WorkflowDetails> workflowMap) {
		this.workflowMap = workflowMap;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public DynamicUtils getUtils() {
		return utils;
	}

	public void setUtils(DynamicUtils utils) {
		this.utils = utils;
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		for(Entry<String,WorkflowDetails> entry : workflowMap.entrySet()){
			buffer.append("Key:" + entry.getKey() + " - BSCSAction:" + entry.getValue().getBSCS_ACTION() + " - Product Type:" + entry.getValue().getPRODUCT_TYPE());
			buffer.append("\n");
			buffer.append("---MBCActions:");
			for(WorkflowAction action:entry.getValue().getMBCActionSequence()){
				buffer.append("\n");
				buffer.append("---Seq :" + action.getSequence() + ", Action:" + action.getAction());
			}
			buffer.append("\n");
			buffer.append("---PCRFActions:");
			for(WorkflowAction action:entry.getValue().getPCRFActionSequence()){
				buffer.append("\n");
				buffer.append("---Seq :" + action.getSequence() + ", Action:" + action.getAction());
			}
			buffer.append("\n");
			buffer.append("---MZActions:");
			for(WorkflowAction action:entry.getValue().getMZActionSequence()){
				buffer.append("\n");
				buffer.append("---Seq :" + action.getSequence() + ", Action:" + action.getAction());
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}

}


