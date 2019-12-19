package com.chatsys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.chatsys.util.DBConnection;

public class RegisterDao {
	public boolean registerUser(String username) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			String register_query = "insert into user(username) values (?);";
			con = DBConnection.createConnection();
			ps = con.prepareStatement(register_query);
			ps.setString(1, username);
			ps.executeUpdate();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
