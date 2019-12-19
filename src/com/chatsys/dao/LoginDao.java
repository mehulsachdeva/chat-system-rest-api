package com.chatsys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.chatsys.util.DBConnection;

public class LoginDao {
	public boolean authenticateUser(String username) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		try {
			String login_query = "select * from user where username=?;"; 
			con = DBConnection.createConnection();
			ps = con.prepareStatement(login_query);
			ps.setString(1, username);
			result = ps.executeQuery();
			if(result.next()) {	
				System.out.println(result.getString(2));
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
}
