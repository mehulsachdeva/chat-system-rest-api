package com.chatsys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.chatsys.util.DBConnection;

public class UpdateDao {
	public boolean updateSeen(String username, String tousername) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String seen = "true";
		String old_seen = "false";
		
		try {
			String chat_between_query = "update chat set seen=? where fromid=? and toid=? and seen=?"; 
			con = DBConnection.createConnection();
			ps = con.prepareStatement(chat_between_query);
			ps.setString(1, seen);
			ps.setString(2, username);
			ps.setString(3, tousername);
			ps.setString(4, old_seen);
			ps.executeUpdate();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
