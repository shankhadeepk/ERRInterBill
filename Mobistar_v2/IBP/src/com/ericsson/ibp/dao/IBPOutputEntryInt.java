package com.ericsson.ibp.dao;

import java.sql.Connection;

import com.ericsson.ibp.exceptions.DataWritingException;

/**
 * @author ejyoban
 * Interface to contain the common methods applicable for all the output DAOs
 */

public interface IBPOutputEntryInt {
	public void writeEntry(Connection connection, String statement) throws DataWritingException;
}


