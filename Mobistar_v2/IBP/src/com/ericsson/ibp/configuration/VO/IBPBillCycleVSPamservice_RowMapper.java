package com.ericsson.ibp.configuration.VO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IBPBillCycleVSPamservice_RowMapper implements RowMapper<IBPBillCycleVSPamservice_Row>{

	public IBPBillCycleVSPamservice_Row mapRow(ResultSet rsltSet, int rowNum) throws SQLException {
		
		IBPBillCycleVSPamservice_Row objIbpBillCyclePamServ=new IBPBillCycleVSPamservice_Row();
		objIbpBillCyclePamServ.setIbpBillCyclePamServId(rsltSet.getLong("IBP_BILL_CYCLE_VS_PAM_SERV_ID"));
		objIbpBillCyclePamServ.setBillCycle(rsltSet.getLong("BILL_CYCLE"));
		objIbpBillCyclePamServ.setPamServiceId(rsltSet.getLong("PAM_SERVICE_ID"));
		objIbpBillCyclePamServ.setPamClassId(rsltSet.getLong("PAM_CLASS_ID"));
		objIbpBillCyclePamServ.setScheduleId(rsltSet.getLong("SCHEDULE_ID"));
		return objIbpBillCyclePamServ;		
	}

}
