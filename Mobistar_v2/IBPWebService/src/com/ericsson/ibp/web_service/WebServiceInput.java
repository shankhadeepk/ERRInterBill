package com.ericsson.ibp.web_service;

/**
 * @author ejyoban
 * 
 */

public class WebServiceInput {
	
	private String offerId;
	private String msisdn;
	
	public WebServiceInput( String offerId, String msisdn) {
		super();
		
		this.offerId = offerId;
		this.msisdn = msisdn;
	}
	
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
}


