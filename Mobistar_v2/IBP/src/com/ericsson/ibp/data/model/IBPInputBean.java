package com.ericsson.ibp.data.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ericsson.ibp.configurationDB.WorkflowDetails;
import com.ericsson.ibp.dao.IBPOutputEntryInt;
import com.ericsson.ibp.dao.IBPProdConsLink;
import com.ericsson.ibp.dao.IBPProdConsLinkCollection;

/**
 * @author ejyoban
 * The input bean shall represent a processable entry from the PROCESSING_QUEUE table
 * The bean shall contain all the information, both that are input and output
 * The bean shall also contain the processing logic applicable for the bean
 * The inputs shall be assigned by the input adapter (queue reader)
 * The processing logic shall be assigned by the thread at the start
 * The outputs shall be assigned while the thread executes the logic for the bean
 */

public class IBPInputBean {
	
	
	public IBPInputBean()
	{
		mbcCommands = new LinkedHashMap<String, List<IBPOutputEntryInt>>();
		pcrfCommands = new LinkedHashMap<String, List<IBPOutputEntryInt>>();
		mzCommands = new LinkedHashMap<String, List<IBPOutputEntryInt>>();
		existingProdConsLinks = new ArrayList<IBPProdConsLink>();
		modifiedProdConsLinks = new IBPProdConsLinkCollection();
	}
	
	private WorkflowDetails applicableWorkflow;
	
	//each BSCS action may lead to multiple internal actions. 
	//each actio may inturn generate multiple commands. So the actions
	//return a list of IBPOutputEntryInt objects
	//the key to the map is the action name that produced the command
	private Map<String, List<IBPOutputEntryInt>> mbcCommands;
	
	private Map<String, List<IBPOutputEntryInt>> pcrfCommands;
	
	private Map<String, List<IBPOutputEntryInt>> mzCommands;
	
	private List<IBPProdConsLink> existingProdConsLinks;
	
	private IBPProdConsLinkCollection modifiedProdConsLinks; 
	
	
	
	private long request_id;
	private long co_id;
	private long customer_id;
	private String bscs_action;
	private long tmcode;
	private long sncode;
	private String cs_stat_chng;
	private String cs_sparam1;
	private long old_resource_id;
	private long new_resource_id;
	private long billcycle;
	private String longDesc;
	private String shortDesc;
	
	
	public WorkflowDetails getApplicableWorkflow() {
		return applicableWorkflow;
	}
	public void setApplicableWorkflow(WorkflowDetails applicableWorkflow) {
		this.applicableWorkflow = applicableWorkflow;
	}
	public long getRequest_id() {
		return request_id;
	}
	public void setRequest_id(long request_id) {
		this.request_id = request_id;
	}
	public long getCo_id() {
		return co_id;
	}
	public void setCo_id(long co_id) {
		this.co_id = co_id;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public String getBscs_action() {
		return bscs_action;
	}
	public void setBscs_action(String bscs_action) {
		this.bscs_action = bscs_action;
	}
	public long getTmcode() {
		return tmcode;
	}
	public void setTmcode(long tmcode) {
		this.tmcode = tmcode;
	}
	public long getSncode() {
		return sncode;
	}
	public void setSncode(long sncode) {
		this.sncode = sncode;
	}
	public String getCs_stat_chng() {
		return cs_stat_chng;
	}
	public void setCs_stat_chng(String cs_stat_chng) {
		this.cs_stat_chng = cs_stat_chng;
	}
	public String getCs_sparam1() {
		return cs_sparam1;
	}
	public void setCs_sparam1(String cs_sparam1) {
		this.cs_sparam1 = cs_sparam1;
	}
	public long getOld_resource_id() {
		return old_resource_id;
	}
	public void setOld_resource_id(long old_resource_id) {
		this.old_resource_id = old_resource_id;
	}
	public long getNew_resource_id() {
		return new_resource_id;
	}
	public void setNew_resource_id(long new_resource_id) {
		this.new_resource_id = new_resource_id;
	}
	public long getBillcycle() {
		return billcycle;
	}
	public void setBillcycle(long billcycle) {
		this.billcycle = billcycle;
	}
	public String getLongDesc() {
		return longDesc;
	}
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public Map<String, List<IBPOutputEntryInt>> getMbcCommands() {
		return mbcCommands;
	}
	public void setMbcCommands(Map<String, List<IBPOutputEntryInt>> mbcCommands) {
		this.mbcCommands = mbcCommands;
	}
	public Map<String, List<IBPOutputEntryInt>> getPcrfCommands() {
		return pcrfCommands;
	}
	public void setPcrfCommands(Map<String, List<IBPOutputEntryInt>> pcrfCommands) {
		this.pcrfCommands = pcrfCommands;
	}
	public Map<String, List<IBPOutputEntryInt>> getMzCommands() {
		return mzCommands;
	}
	public void setMzCommands(Map<String, List<IBPOutputEntryInt>> mzCommands) {
		this.mzCommands = mzCommands;
	}
	public List<IBPProdConsLink> getExistingProdConsLinks() {
		return existingProdConsLinks;
	}
	public void setExistingProdConsLinks(List<IBPProdConsLink> existingProdConsLinks) {
		this.existingProdConsLinks = existingProdConsLinks;
	}
	public IBPProdConsLinkCollection getModifiedProdConsLinks() {
		return modifiedProdConsLinks;
	}
	public void setModifiedProdConsLinks(
			IBPProdConsLinkCollection modifiedProdConsLinks) {
		this.modifiedProdConsLinks = modifiedProdConsLinks;
	}
	
	
	
	
}


