package com.ericsson.ibp.configurationDB;

import java.sql.SQLDataException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ericsson.ibp.configuration.VO.IBPBSCSActions_Row;
import com.ericsson.ibp.configuration.VO.IBPBSCSActions_RowMapper;
import com.ericsson.ibp.configuration.VO.IBPBillCycleVSPamservice_Row;
import com.ericsson.ibp.configuration.VO.IBPBillCycleVSPamservice_RowMapper;
import com.ericsson.ibp.configuration.VO.IBPProductConfiguration_Row;
import com.ericsson.ibp.configuration.VO.IBPProductConfiguration_RowMapper;
import com.ericsson.ibp.configuration.VO.IBPProductTypeDefinition_Row;
import com.ericsson.ibp.configuration.VO.IBPProductTypeDefinition_RowMapper;
import com.ericsson.ibp.constants.Constants;

public class IBPLoadTables {
	
	public static List<IBPProductConfiguration_Row> IBP_PRODUCT_CONFIGURATION;
	public static List<IBPProductTypeDefinition_Row> IBP_PRODUCT_TYPE_DEFINITION;
	public static List<IBPBillCycleVSPamservice_Row> IBP_BILL_CYCLE_VS_PAM_SERVICE;
	public static List<IBPBSCSActions_Row> IBP_BSCS_ACTIONS;
	
	private static JdbcTemplate jdbcTemplate;
	
	private static Logger logger = Logger.getLogger(IBPLoadTables.class);	
	
	public void setJdbcTemplate(DataSource dataSource) {
	    jdbcTemplate = new JdbcTemplate(dataSource);
	}	
	private static void setBscsActionsList() {
		
		try{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("ACTION_ID , ");
		sql.append("BSCS_ACTION_NAME ");
		sql.append("FROM ");
		sql.append(Constants.IBP_BSCS_ACTIONS);	
				
		IBP_BSCS_ACTIONS=jdbcTemplate.query(sql.toString(),new Object[]{},new IBPBSCSActions_RowMapper());
				
		if(logger.isDebugEnabled())
			logger.debug("+++++ The List of {ibpBSCSActions_Rows} is :+++++"+IBP_BSCS_ACTIONS);
		}
		catch(DataAccessException de)
		{
			logger.error("Error in setBscsActionsList() for the query");
			de.printStackTrace();
		}
		catch(Exception e)
		{
			logger.error("Error in setBscsActionsList()");
			e.printStackTrace();
		}
		
		
	}
	private static void setIbpBillCycleVsPamServList() {
		
		try{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");		
		sql.append("IBP_BILL_CYCLE_VS_PAM_SERV_ID ,"); 
		sql.append("BILL_CYCLE ,"); 
		sql.append("PAM_SERVICE_ID ,"); 
		sql.append("PAM_CLASS_ID,");
		sql.append("SCHEDULE_ID  ");
		sql.append("FROM ");
		sql.append(Constants.IBP_BILL_CYCLE_VS_PAM_SERVICE);	
				
		IBP_BILL_CYCLE_VS_PAM_SERVICE=jdbcTemplate.query(sql.toString(),new Object[]{},new IBPBillCycleVSPamservice_RowMapper());
		
		if(logger.isDebugEnabled())
			logger.debug("+++++ The List of  {ibpBillCycleVSPamservice_Rows} is : "+IBP_BILL_CYCLE_VS_PAM_SERVICE);
		}
		catch(DataAccessException de)
		{
			logger.error("Error in setIbpBillCycleVsPamServList() for the query");
			de.printStackTrace();
		}
		catch(Exception e)
		{
			logger.error("Error in setIbpBillCycleVsPamServList()");
			e.printStackTrace();
		}
		
	}
	private static void setIbpProductTypeDefList() {
		
		try{
			
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");		
		sql.append("PRODUCT_TYPE_ID ,"); 
		sql.append("PRODUCT_TYPE ");		
		sql.append("FROM ");
		sql.append(Constants.IBP_PRODUCT_TYPE_DEFINITION);
		
				
		IBP_PRODUCT_TYPE_DEFINITION=jdbcTemplate.query(sql.toString(),new Object[]{},new IBPProductTypeDefinition_RowMapper());
		
		if(logger.isDebugEnabled())
			logger.debug("+++++ The List of {ibPProductTypeDefinition_Rows} is : "+IBP_PRODUCT_TYPE_DEFINITION);
		}
		catch(DataAccessException de)
		{
			logger.error("Error in setIbpProductTypeDefList() for the query");
			de.printStackTrace();
		}
		catch(Exception e)
		{
			logger.error("Error in setIbpProductTypeDefList()");
			e.printStackTrace();
		}
		
	}
	private static void setIbpProductConfigList() {
		
		try{
		
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");		
			sql.append("IBPPROD.PRODUCT_CONFIG_ID PRODUCT_CONFIG_ID,"); 
			sql.append("IBPPROD.PROVIDER_OFFER_ID PROVIDER_OFFER_ID, ");
			sql.append("IBPPROD.CONSUMER_OFFER_ID CONSUMER_OFFER_ID, ");	
			sql.append("IBPPROD.OFFER_TYPE OFFER_TYPE, ");	
			sql.append("IBPPROD.PRODUCT_NAME PRODUCT_NAME, ");	
			sql.append("IBPPROD.TMCODE TMCODE, ");	
			sql.append("IBPPROD.SNCODE SNCODE, ");	
			sql.append("IBPPROD.PRODUCT_TYPE PRODUCT_TYPE, ");		
			sql.append("IBPPROD.CONTRIBUTING_TARIFF CONTRIBUTING_TARIFF, ");
			sql.append("IBPPROD.CONSUMING_TARIFF CONSUMING_TARIFF, ");	
			sql.append("IBPPROD.CONSUMING_SN CONSUMING_SN, ");
			sql.append("IBPPROD.DEFAULT_QOS DEFAULT_QOS, ");	
			sql.append("IBPPROD.UNTHROTTLE_QOS_LIST UNTHROTTLE_QOS_LIST, ");
			sql.append("IBPPRODTYPE.PRODUCT_TYPE PRODUCT_TYPE_NAME ");		
			sql.append("FROM ");
			sql.append("IBP_PRODUCT_CONFIGURATION IBPPROD,IBP_PRODUCT_TYPE_DEFINITION IBPPRODTYPE ");
			sql.append("WHERE ");
			sql.append("IBPPROD.PRODUCT_TYPE=IBPPRODTYPE.PRODUCT_TYPE_ID(+) ");
			
					
			IBP_PRODUCT_CONFIGURATION=jdbcTemplate.query(sql.toString(),new Object[]{},new IBPProductConfiguration_RowMapper());
			
			if(logger.isDebugEnabled())
				logger.debug("+++++ The List {ibpProductConfiguration_Rows} is : "+IBP_PRODUCT_CONFIGURATION);
		}
		catch(DataAccessException de)
		{
			logger.error("Error in setIbpProductConfigList() for the query");
			de.printStackTrace();
		}
		catch(Exception e)
		{
			logger.error("Error in setIbpProductConfigList()");
			e.printStackTrace();
		}
		
	}
	private static void loadTables()
	{
		if(logger.isInfoEnabled())
			logger.info("=============  Inside the IBPLoadTables ==================");
		/*
		 * For loading the table IBP_PRODUCT_CONFIGURATION
		 * 
		 * */
		
		setIbpProductConfigList();
		
		/*
		 * For loading the table IBP_PRODUCT_TYPE_DEFINITION
		 * 
		 * */
		setIbpProductTypeDefList();
		
		/*
		 * For loading the table IBP_BILL_CYCLE_VS_PAM_SERVICE
		 * 
		 * */
		setIbpBillCycleVsPamServList();
		
		/*
		 * For loading the table IBP_BSCS_ACTIONS
		 * 
		 * */
		setBscsActionsList();		
		
		if(logger.isInfoEnabled())
			logger.info("=============  Leaving the IBPLoadTables ==================");
		
	}	
}
