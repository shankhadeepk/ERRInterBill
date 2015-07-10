package com.ericsson.ibp.configurationDB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.josql.Query;
import org.josql.QueryExecutionException;
import org.josql.QueryParseException;

import com.ericsson.ibp.constants.Constants;


public class QueryTableLists {
	
	private static Logger logger = Logger.getLogger(QueryTableLists.class);
	private static String voPackageName="com.ericsson.ibp.configuration.VO";
	
	public List queryGivenTable(String tableName,Map<String,String> attributeMap)
	{
		if(logger.isInfoEnabled())
			logger.info("=============  Inside the queryGivenTable method ==================");
		
		Query queryObj=null;
		StringBuilder queryStatement=null;
		String classQualifiedName=null;
		Iterator itrAttributeMap=null;
		List queryForList=null;
		String finalQuery=null;
		List results=null;
		
		try {				
				queryStatement=new StringBuilder("");					
				/*
				 * To check the list name and locate the list
				 * */
				switch(tableName)
				{
					case Constants.IBP_PRODUCT_CONFIGURATION:
					{
						queryStatement.append("select * from ");
						queryForList=IBPLoadTables.IBP_PRODUCT_CONFIGURATION;
						classQualifiedName=voPackageName+".IBPProductConfiguration_Row";
						break;
					}
						
					case Constants.IBP_PRODUCT_TYPE_DEFINITION:
					{
						queryStatement.append("select * from ");
						queryForList=IBPLoadTables.IBP_PRODUCT_TYPE_DEFINITION;
						classQualifiedName=voPackageName+".IBPProductTypeDefinition_Row";
						break;
					}
						
					case Constants.IBP_BILL_CYCLE_VS_PAM_SERVICE:
					{
						queryStatement.append("select * from ");
						queryForList=IBPLoadTables.IBP_BILL_CYCLE_VS_PAM_SERVICE;
						classQualifiedName=voPackageName+".IBPBillCycleVSPamservice_Row";
						break;
					}
						
					case Constants.IBP_BSCS_ACTIONS:
					{
						queryStatement.append("select * from ");
						queryForList=IBPLoadTables.IBP_BSCS_ACTIONS;
						classQualifiedName=voPackageName+".IBPBSCSActions_Row";
						break;
					}					
				}
				
				if(classQualifiedName != null)
					queryStatement.append(classQualifiedName);
				
				/*
				 * To extract the attribute and values from the HashMap 
				 * 
				 * */
				
				itrAttributeMap=attributeMap.entrySet().iterator();
				
				if(itrAttributeMap.hasNext())
				{	
					if (!("".equals(queryStatement)))
						queryStatement.append(" where ");
				}
				
				if (!("".equals(queryStatement)))
				{
					while(itrAttributeMap.hasNext())				
						{
							Map.Entry<String, String> entry=(Map.Entry<String, String>) itrAttributeMap.next();					
							queryStatement.append(entry.getKey()+"=");
							queryStatement.append(entry.getValue());
							
							if(itrAttributeMap.hasNext())
								queryStatement.append(" and ");
						}
				}	
				
				finalQuery=queryStatement.toString();
				
				if(logger.isInfoEnabled())
					logger.info("********   Query configured after the logic ******** ::"+finalQuery);
				
				//Final query configured
				if (!("".equals(queryStatement)))
				{	
					queryObj=new Query();
					queryObj.parse(finalQuery);			
				
					//queryObj.parse("select name from com.sample.Student where grade < 400");
					
					System.out.println("queryObj="+queryObj+":: finalQuery="+finalQuery+":: queryForList="+queryForList);
					
					if(logger.isDebugEnabled())
						logger.debug("Start time --"+System.currentTimeMillis());
					
					//The result of the JOSQL query				
					results=(List)queryObj.execute(queryForList).getResults();
					
					if(logger.isDebugEnabled())
						logger.debug("End time --"+System.currentTimeMillis());					
				
					if(logger.isDebugEnabled())
						logger.debug("Results :::: "+ results);
				}						
				
				if(logger.isInfoEnabled())
					logger.info("=============  Leaving the queryGivenTable method ==================");
			
		} catch (QueryParseException e) {
			
			logger.error("Exception occured while Parsing query , please check the query");			
			e.printStackTrace();
			
		} catch (QueryExecutionException e) {	
			
			logger.error("Exception occured while executing the query , please check the query");	
			e.printStackTrace();
		}
		catch (Exception e) {	
			
			logger.error("Exception occured in queryGivenTable method");	
			e.printStackTrace();
		}
		
		return results;
	}

}
