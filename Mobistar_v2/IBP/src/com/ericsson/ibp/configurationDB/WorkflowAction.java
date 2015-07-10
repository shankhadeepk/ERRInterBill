package com.ericsson.ibp.configurationDB;

/**
 * @author ejyoban
 * Directly related to IBP_WORKFLOW_DEFINITION table
 * a row in the table corresponds to an object
 * 
 */

public class WorkflowAction {
	private String application;
	private Integer workflowId;
	private String action;				//the action names are supposed to match the logic processor classes
	private Integer sequence;
	
	public WorkflowAction(String application, Integer workflowId,
			String action, Integer sequence) {
		super();
		this.application = application;
		this.workflowId = workflowId;
		this.action = action;
		this.sequence = sequence;
	}

	public String getApplication() {
		return application;
	}

	public Integer getWorkflowId() {
		return workflowId;
	}

	public String getAction() {
		return action;
	}

	public Integer getSequence() {
		return sequence;
	}

//	public void setApplication(String application) {
//		this.application = application;
//	}
//
//	public void setWorkflowId(Integer workflowId) {
//		this.workflowId = workflowId;
//	}
//
//	public void setAction(String action) {
//		this.action = action;
//	}
//
//	public void setSequence(Integer sequence) {
//		this.sequence = sequence;
//	}
	
	
	
}


