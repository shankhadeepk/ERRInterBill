package com.ericsson.ibp.dao;

import java.sql.Connection;

import com.ericsson.ibp.exceptions.DataWritingException;

/**
 * @author ejyoban
 * 
 */

public class IBPProdConsLinkInsert extends IBPProdConsLink implements
		IBPOutputEntryInt {

	//TODO: here write entry will actually insert a row in the IBP_PRODUCER_CONSUMER link table
	@Override
	public void writeEntry(Connection connection, String statement)
			throws DataWritingException {
		// TODO Auto-generated method stub

	}

}


