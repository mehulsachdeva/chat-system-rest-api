package com.chatsys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.chatsys.util.DBConnection;

public class RegisterDao {
	public boolean registerUser(String username) {
		
		Connection con = null;
		PreparedStatement ps, pc = null;
		ResultSet result = null;
		
		try {
			String search_username = "select * from user where username=?;";
			String register_query = "insert into user(username) values (?);";
			con = DBConnection.createConnection();
			
			pc = con.prepareStatement(search_username);
			pc.setString(1, username);
			result = pc.executeQuery();
			if(result.next()) {
				return false;
			}else {
				ps = con.prepareStatement(register_query);
				ps.setString(1, username);
				ps.executeUpdate();
				return true;
			}
		}catch(Exception e) {
			return false;
		}
	}
}
