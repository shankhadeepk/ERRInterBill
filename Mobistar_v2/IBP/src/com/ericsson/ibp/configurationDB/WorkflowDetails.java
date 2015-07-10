package com.ericsson.ibp.configurationDB;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ejyoban
 * This is a placeholder class that will be used to hold the workflow details for each
 * module, against any given BSCS Action
 * For example:
 * For BSCS Action = Contract Activated, the corresponding ImpactDetails will contain the following
 * MBCImpInd = 1
 * MZImpInd = 1;
 * PCRFImpInd = 1;
 */

public class WorkflowDetails {
	
	private Integer BSCS_ACTION = -1;
	private Integer PRODUCT_TYPE = -1;
	private Integer MBCImpactIndicator = -1;		//-1 indicates no action necessary
	private Integer MZImpactIndicator = -1;
	private Integer PCRFImpactIndicator = -1;
	
	private List<WorkflowAction> MBCActionSequence = new ArrayList<WorkflowAction>();
	private List<WorkflowAction> MZActionSequence = new ArrayList<WorkflowAction>();
	private List<WorkflowAction> PCRFActionSequence = new ArrayList<WorkflowAction>();
	
	public WorkflowDetails(int bscs_action, int product_type, int mbcImpact, int mzImpact, int pcrfImpact)
	{
		super();
		this.BSCS_ACTION = bscs_action;
		this.PRODUCT_TYPE = product_type;
		this.MBCImpactIndicator = mbcImpact;
		this.MZImpactIndicator = mzImpact;
		this.PCRFImpactIndicator = pcrfImpact;
		
		//TODO: read workflow_action table for each impactIndicator and initialize the list of WorkflowAction
	}

	//the key is a concat of the BSCS_ACTION and PRODUCT_TYPE
	public String getKey()
	{
		return (this.BSCS_ACTION + "-" + this.PRODUCT_TYPE);
	}

	public Integer getMBCImpactIndicator() {
		return MBCImpactIndicator;
	}

	public Integer getMZImpactIndicator() {
		return MZImpactIndicator;
	}

	public Integer getPCRFImpactIndicator() {
		return PCRFImpactIndicator;
	}

	public List<WorkflowAction> getMBCActionSequence() {
		return MBCActionSequence;
	}

	public void setMBCActionSequence(List<WorkflowAction> mBCActionSequence) {
		MBCActionSequence = mBCActionSequence;
	}

	public List<WorkflowAction> getMZActionSequence() {
		return MZActionSequence;
	}

	public void setMZActionSequence(List<WorkflowAction> mZctionSequence) {
		MZActionSequence = mZctionSequence;
	}

	public List<WorkflowAction> getPCRFActionSequence() {
		return PCRFActionSequence;
	}

	public void setPCRFActionSequence(List<WorkflowAction> pCRFActionSequence) {
		PCRFActionSequence = pCRFActionSequence;
	}
	
	public Integer getBSCS_ACTION() {
		return BSCS_ACTION;
	}
//
//
//
//	public void setBSCS_ACTION(Integer bSCS_ACTION) {
//		BSCS_ACTION = bSCS_ACTION;
//	}
//

	public Integer getPRODUCT_TYPE() {
		return PRODUCT_TYPE;
	}
//
//
//
//	public void setPRODUCT_TYPE(Integer pRODUCT_TYPE) {
//		PRODUCT_TYPE = pRODUCT_TYPE;
//	}

//	public void setBSCS_ACTION(String bSCS_ACTION) {
//		BSCS_ACTION = bSCS_ACTION;
//	}
//
//	public void setMBCImpactIndicator(Integer mBCImpactIndicator) {
//		MBCImpactIndicator = mBCImpactIndicator;
//	}
//
//	public void setMZImpactIndicator(Integer mZImpactIndicator) {
//		MZImpactIndicator = mZImpactIndicator;
//	}
//
//	public void setPCRFImpactIndicator(Integer pCRFImpactIndicator) {
//		PCRFImpactIndicator = pCRFImpactIndicator;
//	}
	
}


