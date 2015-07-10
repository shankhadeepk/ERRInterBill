package com.ericsson.ibp.processor.linkTable;

import com.ericsson.ibp.dao.IBPProdConsLinkCollection;
import com.ericsson.ibp.dao.IBPProdConsLinkUpdate;
import com.ericsson.ibp.data.model.IBPInputBean;

/**
 * @author ejyoban
 * 
 */

public class MSISDNChange extends UpdateLinkTableAbst {

	@Override
	public IBPProdConsLinkCollection updateLink(IBPInputBean input) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected IBPProdConsLinkUpdate updateRowsWhereContractIsProvider(){
		return null;
	}
	
	//TODO: create a common method for MSISDN change as mentioned in LLD for point 3

}


