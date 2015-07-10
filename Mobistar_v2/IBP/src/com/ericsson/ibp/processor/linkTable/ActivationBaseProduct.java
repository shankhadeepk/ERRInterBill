package com.ericsson.ibp.processor.linkTable;

import java.util.List;

import com.ericsson.ibp.dao.IBPProdConsLinkCollection;
import com.ericsson.ibp.dao.IBPProdConsLinkInsert;
import com.ericsson.ibp.data.model.IBPInputBean;

/**
 * @author ejyoban
 * 
 */
public class ActivationBaseProduct extends UpdateLinkTableAbst {

	@Override
	public IBPProdConsLinkCollection updateLink(IBPInputBean input) {
		// TODO Auto-generated method stub
			//determine the product type and call the method accordingly
			//put the returned list in the Collection and return
		return null;
	}

	//TODO: logic for FullShared product type.
		//to return the list of rows that are to be inserted
	protected List<IBPProdConsLinkInsert> updateForFullShared()
	{
		return null;
	}
	
	//TODO:
	protected List<IBPProdConsLinkInsert> updateForImplicitMultiplexor()
	{
		return null;
	}
	
	//TODO:
	protected List<IBPProdConsLinkInsert> updateForSumNShare()
	{
		return null;
	}

}


