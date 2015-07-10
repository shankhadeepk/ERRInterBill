package com.ericsson.ibp.dao;

import java.sql.Connection;

import com.ericsson.ibp.exceptions.DataWritingException;

/**
 * @author ejyoban
 * 
 */

public class IBPProdConsLinkDelete extends IBPProdConsLink implements
		IBPOutputEntryInt {

	//TODO: Here writeEntry will actuall delete the entry from the IBP_PROD_CONS table
	@Override
	public void writeEntry(Connection connection, String statement)
			throws DataWritingException {
		// TODO Auto-generated method stub

	}

}


