package com.ericsson.ibp.configuration.VO;

import java.io.Serializable;

public class IBPProductConfiguration_Row implements Serializable{	
			  
	
	private Long productConfigId;
	private Integer providerOfferId;
	private Integer consumerOfferId;
	private Integer offerType;
	private String productName;
	private String tmcode;
	private String sncode;
	private Integer productType;
	private String productTypeName;	
	private Integer contributingTariff;
	private Integer consumingTariff;
	private Integer consumingSn;
	private Integer defaultQos;
	private String unthrottleQosList;
	
	public IBPProductConfiguration_Row(){
		
	}
	public IBPProductConfiguration_Row(Long productConfigId,
			Integer providerOfferId, Integer consumerOfferId,
			Integer offerType,String productName, String tmcode, String sncode,
			Integer productType, Integer contributingTariff,
			Integer consumingTariff, Integer consumingSn, Integer defaultQos,
			String unthrottleQosList) {
		super();
		this.productConfigId = productConfigId;
		this.providerOfferId = providerOfferId;
		this.consumerOfferId = consumerOfferId;
		this.offerType = offerType;
		this.productName=productName;
		this.tmcode = tmcode;
		this.sncode = sncode;
		this.productType = productType;
		this.contributingTariff = contributingTariff;
		this.consumingTariff = consumingTariff;
		this.consumingSn = consumingSn;
		this.defaultQos = defaultQos;
		this.unthrottleQosList = unthrottleQosList;
	}
	
	
	public Long getProductConfigId() {
		return productConfigId;
	}
	public void setProductConfigId(Long productConfigId) {
		this.productConfigId = productConfigId;
	}
	public Integer getProviderOfferId() {
		return providerOfferId;
	}
	public void setProviderOfferId(Integer providerOfferId) {
		this.providerOfferId = providerOfferId;
	}
	public Integer getConsumerOfferId() {
		return consumerOfferId;
	}
	public void setConsumerOfferId(Integer consumerOfferId) {
		this.consumerOfferId = consumerOfferId;
	}
	public Integer getOfferType() {
		return offerType;
	}
	public void setOfferType(Integer offerType) {
		this.offerType = offerType;
	}
	public String getTmcode() {
		return tmcode;
	}
	public void setTmcode(String tmcode) {
		this.tmcode = tmcode;
	}
	public String getSncode() {
		return sncode;
	}
	public void setSncode(String sncode) {
		this.sncode = sncode;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public Integer getContributingTariff() {
		return contributingTariff;
	}
	public void setContributingTariff(Integer contributingTariff) {
		this.contributingTariff = contributingTariff;
	}
	public Integer getConsumingTariff() {
		return consumingTariff;
	}
	public void setConsumingTariff(Integer consumingTariff) {
		this.consumingTariff = consumingTariff;
	}
	public Integer getConsumingSn() {
		return consumingSn;
	}
	public void setConsumingSn(Integer consumingSn) {
		this.consumingSn = consumingSn;
	}
	public Integer getDefaultQos() {
		return defaultQos;
	}
	public void setDefaultQos(Integer defaultQos) {
		this.defaultQos = defaultQos;
	}
	public String getUnthrottleQosList() {
		return unthrottleQosList;
	}
	public void setUnthrottleQosList(String unthrottleQosList) {
		this.unthrottleQosList = unthrottleQosList;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String toString()
	{
		return ("IBPProductConfiguration_Row:"+"[Product_Config="+this.productConfigId+",Product_Type_Name = "+this.productTypeName+"]");
	}
	
	

}
