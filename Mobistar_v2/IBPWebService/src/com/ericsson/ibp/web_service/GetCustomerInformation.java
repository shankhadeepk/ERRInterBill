package com.ericsson.ibp.web_service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;


import org.apache.log4j.Logger;

import com.ericsson.ibp.web.listeners.SpringContextHolder;
import com.ericsson.ibp.beans.ProviderConsumerLinkInfo;


public class GetCustomerInformation {
	
	
	private static Logger logger = Logger.getLogger(GetCustomerInformation.class);
	
	private DataSource datasource;
	
	public String getMSISDNs( WebServiceInput input )
	{
		datasource = (DataSource)SpringContextHolder.getApplicationContext().getBean("BSCSdataSource");
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
			
		StringBuffer provConsLinkQuery = new StringBuffer();
		
		String retMSISDNString = new String();
		
		provConsLinkQuery.append("SELECT CONSUMER_MSISDN from IBP_PROVIDER_CONSUMER_LINK where ");
		provConsLinkQuery.append("PROVIDER_MSISDN = ");
		provConsLinkQuery.append(input.getMsisdn());
		provConsLinkQuery.append(" AND PROVIDER_ACCOUNT_OFFER_ID = ");
		provConsLinkQuery.append(input.getOfferId());
		
		if(logger.isInfoEnabled())
			logger.info( "GetCustomerInformation: " + provConsLinkQuery );
		
		try {
			connection = datasource.getConnection();
			statement = connection.prepareStatement(provConsLinkQuery.toString());
			
			resultSet = statement.executeQuery();

            while(resultSet.next())	 {
				
				if(logger.isInfoEnabled())
					logger.info("Current MSISDN in Result Set:: "+ resultSet.getInt( "CONSUMER_MSISDN" ));
				
				retMSISDNString = retMSISDNString +" ,"+ resultSet.getInt( "CONSUMER_MSISDN" );
				
				
			} 
      		
            if( resultSet != null ) { resultSet.close(); }
			if( statement != null ) { statement.close(); }
			if ( connection != null ) { connection.close(); }

		} catch (SQLException e) {
			e.printStackTrace();
			
			if(logger.isInfoEnabled())
				logger.info("SQL Exception: "+ e.getMessage());
			
		} finally {
			if(logger.isInfoEnabled())
				logger.info("SQL Exception: do nothing");
			
		}
		
		return retMSISDNString;
		
	}
	
	public List<ProviderConsumerLinkInfo> getProviderConsumerLinkInfo( 	String providerMSISDN,
												String consumerMSISDN,
												String providerOfferId )
	{ 
		datasource = (DataSource)SpringContextHolder.getApplicationContext().getBean("BSCSdataSource");
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
			
		StringBuffer provConsLinkQuery = new StringBuffer();
		
		List<ProviderConsumerLinkInfo> retProviderConsumerLinks = new ArrayList<ProviderConsumerLinkInfo>(); 
		
		provConsLinkQuery.append("SELECT PROVIDER_ACCOUNT_OFFER_ID, SHARED_ACCOUNT_OFFER_ID, ");
		provConsLinkQuery.append("CONSUMER_MSISDN, PROVIDER_MSISDN from IBP_PROVIDER_CONSUMER_LINK where ");
		provConsLinkQuery.append("PROVIDER_MSISDN = ");
		provConsLinkQuery.append(providerMSISDN);
		provConsLinkQuery.append("CONSUMER_MSISDN = ");
		provConsLinkQuery.append(consumerMSISDN);
		provConsLinkQuery.append(" AND PROVIDER_ACCOUNT_OFFER_ID = ");
		provConsLinkQuery.append(providerOfferId);
		
		if(logger.isInfoEnabled())
			logger.info( "getProviderConsumerLinkInfo from DB using SQL Query: " + provConsLinkQuery );
		
		try {
			connection = datasource.getConnection();
			statement = connection.prepareStatement(provConsLinkQuery.toString());
			
			resultSet = statement.executeQuery();

            while(resultSet.next())	 {
				
				if(logger.isInfoEnabled())
				{
					logger.info(" Query Result: ");
					logger.info( "PROVIDER_ACCOUNT_OFFER_ID = " + resultSet.getLong( "PROVIDER_ACCOUNT_OFFER_ID"));
					logger.info( "SHARED_ACCOUNT_OFFER_ID = " + resultSet.getLong( "SHARED_ACCOUNT_OFFER_ID"));
					logger.info(" PROVIDER_MSISDN = "+ resultSet.getInt( "PROVIDER_MSISDN" ));
					logger.info(" CONSUMER_MSISDN = "+ resultSet.getInt( "CONSUMER_MSISDN" ));
				}
				
				ProviderConsumerLinkInfo providerConsumerLinkInfo = new ProviderConsumerLinkInfo();
				providerConsumerLinkInfo.setProviderAccountOfferId(resultSet.getLong( "PROVIDER_ACCOUNT_OFFER_ID"));
				providerConsumerLinkInfo.setSharedAccountOfferId(resultSet.getLong( "SHARED_ACCOUNT_OFFER_ID"));
				providerConsumerLinkInfo.setProviderMSISDN(""+resultSet.getInt( "PROVIDER_MSISDN"));
				providerConsumerLinkInfo.setConsumerMSISDN(""+resultSet.getInt( "CONSUMER_MSISDN"));
				
				retProviderConsumerLinks.add ( providerConsumerLinkInfo);
				
				
			} 
      		
            if( resultSet != null ) { resultSet.close(); }
			if( statement != null ) { statement.close(); }
			if ( connection != null ) { connection.close(); }

		} catch (SQLException e) {
			e.printStackTrace();
			
			if(logger.isInfoEnabled())
				logger.info("SQL Exception: "+ e.getMessage());
			
		} finally {
			if(logger.isInfoEnabled())
				logger.info("SQL Exception: do nothing");
			
		}
		
		return retProviderConsumerLinks;
		
	}
	//return the required customer information as a csv String
	public String getCustomerInformationAsCSV(WebServiceInput input)
	{
		
		return getMSISDNs( input );
		
	}
	
	public List<String> getCustomerInformationAsList(WebServiceInput input)
	{
		if(logger.isInfoEnabled())
			logger.info( "GetCustomerInformation: getMSISDNs() returns " + getMSISDNs(input) );
		return Arrays.asList(getMSISDNs(input).split(","));
		
		
	}
	
	
	
}


