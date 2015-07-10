package com.ericsson.ibp.configuration.VO;

import java.io.Serializable;

public class IBPBSCSActions_Row implements Serializable{	
			  
	
	private Long actionId;
	private String bscsActionName;
	
	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public String getBscsActionName() {
		return bscsActionName;
	}

	public void setBscsActionName(String bscsActionName) {
		this.bscsActionName = bscsActionName;
	}

	public IBPBSCSActions_Row(){
		
	}

	public String toString()
	{
		return ("IBPBSCSActions_Row:"+"[BSCS Action Id="+this.actionId+",BSCS Action Name="+this.bscsActionName+"]");
	}

	public IBPBSCSActions_Row(Long actionId, String bscsActionName) {
		super();
		this.actionId = actionId;
		this.bscsActionName = bscsActionName;
	}
	
	

}
