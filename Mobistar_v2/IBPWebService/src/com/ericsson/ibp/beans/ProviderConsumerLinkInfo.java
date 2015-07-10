package com.ericsson.ibp.beans;

public class ProviderConsumerLinkInfo {
	private String providerMSISDN;
	private String consumerMSISDN;
	private Long providerAccountOfferId;
	private Long sharedAccountOfferId;
	public String getProviderMSISDN() {
		return providerMSISDN;
	}
	public void setProviderMSISDN(String providerMSISDN) {
		this.providerMSISDN = providerMSISDN;
	}
	public String getConsumerMSISDN() {
		return consumerMSISDN;
	}
	public void setConsumerMSISDN(String consumerMSISDN) {
		this.consumerMSISDN = consumerMSISDN;
	}
	public Long getProviderAccountOfferId() {
		return providerAccountOfferId;
	}
	public void setProviderAccountOfferId(Long providerAccountOfferId) {
		this.providerAccountOfferId = providerAccountOfferId;
	}
	public Long getSharedAccountOfferId() {
		return sharedAccountOfferId;
	}
	public void setSharedAccountOfferId(Long sharedAccountOfferId) {
		this.sharedAccountOfferId = sharedAccountOfferId;
	}
}
