package com.ericsson.ibp.dao;

import java.sql.Connection;

import com.ericsson.ibp.exceptions.DataWritingException;

/**
 * @author ejyoban
 * An output entry for MBC
 */

public class MBCOutputEntry implements IBPOutputEntryInt {

	
	//TODO: Add properties and getters and setters for each of the columns for the
		//MBC order-line table

	@Override
	public void writeEntry(Connection connection, String statement) throws DataWritingException{
		// TODO : Write the logic, so that given a connection, a statement is executed
			//with the appropriate values from the getters and write to the MBC Database
			//if there is an exception throw the same as a DataWritingException
		
	}
}


