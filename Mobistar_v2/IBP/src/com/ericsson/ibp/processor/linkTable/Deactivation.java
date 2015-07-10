package com.ericsson.ibp.processor.linkTable;

import java.util.List;

import com.ericsson.ibp.dao.IBPProdConsLinkCollection;
import com.ericsson.ibp.dao.IBPProdConsLinkDelete;
import com.ericsson.ibp.data.model.IBPInputBean;

/**
 * @author ejyoban
 * 
 */

public class Deactivation extends UpdateLinkTableAbst{


	@Override
	public IBPProdConsLinkCollection updateLink(IBPInputBean input) {
		// TODO Auto-generated method stub
			//call the delete methods and return the lists that are to be deleted
		return null;
	}
	
	//TODO: 
	//to return the list of rows that are to be deleted where the contract is the consumer
	protected List<IBPProdConsLinkDelete> deleteLinksForContractAsConsumer()
	{
		return null;
	}
	
	//TODO: 
		//to return the list of rows that are to be deleted where the contract is the consumer
	protected List<IBPProdConsLinkDelete> deleteLinksForContractAsProducer()
	{
		return null;
	}

}


