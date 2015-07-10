package com.ericsson.ibp.input.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

/**
 * @author ejyoban
 * 
 */

public class Reader {

	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		logger.debug("inside setter for datasource..");
		this.dataSource = dataSource;
		this.method();
	}
	
	private static Logger logger = Logger.getLogger(Reader.class.getName());
	
	public Reader()
	{
		logger.debug("Inside Reader constructor..");

	}
	
	public void method()
	{
		String sql = "SELECT * FROM CUSTOMER_ALL WHERE CUSTOMER_ID = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, 14);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				logger.debug("The value is....." + rs.getString("CUSTCODE"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
}


