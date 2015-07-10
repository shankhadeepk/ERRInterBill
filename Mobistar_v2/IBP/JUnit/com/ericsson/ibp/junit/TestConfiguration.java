package com.ericsson.ibp.junit;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;
import com.ericsson.ibp.configuration.VO.IBPProductConfiguration_Row;
import com.ericsson.ibp.configurationDB.QueryTableLists;
import com.ericsson.ibp.constants.Constants;

public class TestConfiguration {

	@Test
	public void testJoSQLQuery() {
		QueryTableLists queryList=new QueryTableLists();
		String tableName=Constants.IBP_PRODUCT_CONFIGURATION;
		Map<String,String> attributeMap=new HashMap<String, String>();
		attributeMap.put("productConfigId", "12");
		List result = queryList.queryGivenTable(tableName, attributeMap);
		assertTrue("Result size doesnot match", result.size() == 1);
	}

}
