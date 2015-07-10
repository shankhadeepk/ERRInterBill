package com.ericsson.ibp.processor.linkTable;

import java.util.List;

import com.ericsson.ibp.dao.IBPProdConsLinkCollection;
import com.ericsson.ibp.data.model.IBPInputBean;

/**
 * @author ejyoban
 * 
 */

public interface UpdateLinkTableInt {
	public IBPProdConsLinkCollection updateLink(IBPInputBean input);
}


