package com.ericsson.ibp.dao;

import java.sql.Connection;

import com.ericsson.ibp.exceptions.DataWritingException;

/**
 * @author ejyoban
 * 
 */

public class IBPProdConsLinkUpdate extends IBPProdConsLink implements
		IBPOutputEntryInt {

	//TODO: Here writeEntry will actually update entries in the database
	@Override
	public void writeEntry(Connection connection, String statement)
			throws DataWritingException {
		// TODO Auto-generated method stub

	}

}


