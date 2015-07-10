package com.ericsson.ibp.configuration.VO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IBPProductTypeDefinition_RowMapper implements RowMapper<IBPProductTypeDefinition_Row>{

	public IBPProductTypeDefinition_Row mapRow(ResultSet rsltSet, int rowNum) throws SQLException {
		
		IBPProductTypeDefinition_Row objIbpProdTypeDef=new IBPProductTypeDefinition_Row();
		objIbpProdTypeDef.setProductTypeId(rsltSet.getLong("PRODUCT_TYPE_ID"));
		objIbpProdTypeDef.setProductType(rsltSet.getString("PRODUCT_TYPE"));
		
		return objIbpProdTypeDef;		
	}

}
