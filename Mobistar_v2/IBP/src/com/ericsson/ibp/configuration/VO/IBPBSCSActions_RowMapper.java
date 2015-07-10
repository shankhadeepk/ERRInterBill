package com.ericsson.ibp.configuration.VO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IBPBSCSActions_RowMapper implements RowMapper<IBPBSCSActions_Row>{

	public IBPBSCSActions_Row mapRow(ResultSet rsltSet, int rowNum) throws SQLException {
		
		IBPBSCSActions_Row objIbpBSCSAction=new IBPBSCSActions_Row();
		objIbpBSCSAction.setActionId(rsltSet.getLong("ACTION_ID"));
		objIbpBSCSAction.setBscsActionName(rsltSet.getString("BSCS_ACTION_NAME"));
		
		return objIbpBSCSAction;		
	}

}
