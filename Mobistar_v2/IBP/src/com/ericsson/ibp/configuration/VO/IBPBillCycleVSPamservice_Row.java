package com.ericsson.ibp.configuration.VO;

import java.io.Serializable;

public class IBPBillCycleVSPamservice_Row implements Serializable{		  
	
	private Long ibpBillCyclePamServId;
	private Long billCycle;
	private Long pamServiceId;
	private Long pamClassId;
	private Long scheduleId;
	
	
	public IBPBillCycleVSPamservice_Row(){
		
	}
	
	

	public IBPBillCycleVSPamservice_Row(Long ibpBillCyclePamServId,
			Long billCycle, Long pamServiceId, Long pamClassId, Long scheduleId) {
		super();
		this.ibpBillCyclePamServId = ibpBillCyclePamServId;
		this.billCycle = billCycle;
		this.pamServiceId = pamServiceId;
		this.pamClassId = pamClassId;
		this.scheduleId = scheduleId;
	}



	public Long getIbpBillCyclePamServId() {
		return ibpBillCyclePamServId;
	}



	public void setIbpBillCyclePamServId(Long ibpBillCyclePamServId) {
		this.ibpBillCyclePamServId = ibpBillCyclePamServId;
	}



	public Long getBillCycle() {
		return billCycle;
	}



	public void setBillCycle(Long billCycle) {
		this.billCycle = billCycle;
	}



	public Long getPamServiceId() {
		return pamServiceId;
	}



	public void setPamServiceId(Long pamServiceId) {
		this.pamServiceId = pamServiceId;
	}



	public Long getPamClassId() {
		return pamClassId;
	}



	public void setPamClassId(Long pamClassId) {
		this.pamClassId = pamClassId;
	}



	public Long getScheduleId() {
		return scheduleId;
	}



	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}



	public String toString()
	{
		return ("IBPBillCycleVSPamservice_Row:"+"[Bill Cycle ="+this.billCycle+",Pam Class ="+this.pamClassId+",Schedule Id ="+this.scheduleId+"]");
	}
	
	

}
