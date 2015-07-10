package com.ericsson.ibp.processor.linkTable;

import java.util.List;

import javax.sql.DataSource;

import com.ericsson.ibp.dao.IBPProdConsLink;
import com.ericsson.ibp.data.model.Contract;
import com.ericsson.ibp.data.model.IBPInputBean;

/**
 * @author ejyoban
 * The abstract class implements some common methods required for all
 */

public abstract class UpdateLinkTableAbst implements UpdateLinkTableInt {

	private DataSource datasource;
	
	
	//TODO: implementation pending, return list of all contracts from BSCS
	//given a contractId, it returns the list of contracts for the 
		//customer whose contract ID is passed
	private List<Contract> getExistingContracts(IBPInputBean input)
	{
		return null;
	}
	
	//TODO: return list of all existing producer consumer links from 
		//IBP_PRODUCER_CONSUMER_LIST table
	private List<IBPProdConsLink> getAllExistingLinks(IBPInputBean input)
	{
		//put a marker in input so that once the data is fetched from DB it is never 
		return null;
	}
	
	


	public DataSource getDatasource() {
		return datasource;
	}


	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	

}


