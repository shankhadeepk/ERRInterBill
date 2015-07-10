package com.ericsson.ibp.processor.linkTable;

import com.ericsson.ibp.dao.IBPProdConsLinkCollection;
import com.ericsson.ibp.data.model.IBPInputBean;

/**
 * @author ejyoban
 * 
 */

public class RatePlanChange extends UpdateLinkTableAbst {

	@Override
	public IBPProdConsLinkCollection updateLink(IBPInputBean input) {
		Deactivation deactivation = new Deactivation();
		deactivation.deleteLinksForContractAsConsumer();
		deactivation.deleteLinksForContractAsProducer();
		//TODO: implement additional logic as described in LLD for RatePlanChange 
		return null;
	}

}


