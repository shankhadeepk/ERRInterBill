package com.ericsson.ibp.generic.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


///class for containing utility methods that are not thread safe

public class DynamicUtils {
	public void finalizeDBConnection(ResultSet rs,
			Statement st,
			Connection con)
{
	try{
		if(rs!= null)
			rs.close();
		if(st != null)
			st.close();
		if(con!= null)
			con.close();
	}catch(Exception ex){
		
	}
}
}
