package com.ericsson.ibp.processor.linkTable;

import java.util.List;

import com.ericsson.ibp.dao.IBPProdConsLinkCollection;
import com.ericsson.ibp.dao.IBPProdConsLinkInsert;
import com.ericsson.ibp.data.model.IBPInputBean;


/**
 * @author ejyoban
 * 
 */

public class ActivationPartiallySharedImplicit extends UpdateLinkTableAbst{


	@Override
	public IBPProdConsLinkCollection updateLink(IBPInputBean input) {
		// TODO Auto-generated method stub
			//first call newLinksForFullShared()
		input.getModifiedProdConsLinks().getNewLinksForInsert().addAll(this.newLinksForPartiallySharedImplicit());
		ActivationBaseProduct existingLinks = new ActivationBaseProduct();
		input.getModifiedProdConsLinks().getNewLinksForUpdate().addAll(existingLinks.updateForFullShared());
		input.getModifiedProdConsLinks().getNewLinksForUpdate().addAll(existingLinks.updateForImplicitMultiplexor());
		input.getModifiedProdConsLinks().getNewLinksForUpdate().addAll(existingLinks.updateForSumNShare());
		return input.getModifiedProdConsLinks();
	}
	
	//TODO: logic for FullShared product type.
			//to return the list of rows that are to be inserted
	private List<IBPProdConsLinkInsert> newLinksForPartiallySharedImplicit()
	{
		return null;
	}

}


