package com.ericsson.ibp.configuration.VO;

import java.io.Serializable;

public class IBPProductTypeDefinition_Row implements Serializable{	
			  
	
	private Long productTypeId;
	private String productType;
	
	public IBPProductTypeDefinition_Row(){
		
	}
	
	public IBPProductTypeDefinition_Row(Long productTypeId, String productType) {
		super();
		this.productTypeId = productTypeId;
		this.productType = productType;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String toString()
	{
		return ("IBPProductTypeDefinition_Row:"+"[Product-Type="+this.productType+"]");
	}
	
	

}
