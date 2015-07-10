package com.ericsson.ibp.web_service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.ericsson.ibp.beans.ProviderConsumerLinkInfo;

/**
 * @author ejyoban
 * This class provides the (framework) for the web service.
 * The actual service logic is provided by a separate bean
 */

@WebService
public class CustomerInformationProvider {
	
	private static Logger logger = Logger.getLogger(CustomerInformationProvider.class);
	
	GetCustomerInformation customerInformationFetch;
	
	

	//setter for the bean. Not exposed as a web service
	@WebMethod(exclude=true)
	public void setCustomerInformationFetch(
			GetCustomerInformation customerInformationFetch) {
		this.customerInformationFetch = customerInformationFetch;
	}

	
	@WebMethod(operationName="GetProviderConsumerLink")
	@WebResult(name="consumer")
	public List<String> getCustomerInformation(@WebParam(name="providerMSISDN") String msisdn,
			                                   @WebParam(name="providerAccountOfferId") String offerId)
	{
		if(logger.isInfoEnabled())
			logger.info("Received a (LIST) request for MSISDN:" + msisdn + " and OFFER ID:" + offerId);
		
		WebServiceInput input = new WebServiceInput(  offerId, msisdn );
		return customerInformationFetch.getCustomerInformationAsList(input);
	}
	
	@WebMethod(operationName="InfoConso")
	@WebResult(name="providerConsumerInfo")
	public List<ProviderConsumerLinkInfo> getInfoConso(@WebParam(name="providerMSISDN") String providerMsisdn,
											@WebParam(name="consumerMSISDN") String consumerMsisdn,
			                                @WebParam(name="providerAccountOfferId") String providerOfferId)
	{
		
		return customerInformationFetch.getProviderConsumerLinkInfo( providerMsisdn, consumerMsisdn, providerOfferId);
	}
	
	
	
}


