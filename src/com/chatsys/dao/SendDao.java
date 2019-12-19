package com.chatsys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

import com.chatsys.util.DBConnection;
import com.chatsys.util.Encryption;

public class SendDao {
	public boolean sendChat(String username, String tousername, String message) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		// secret key for AES encryption of messages
		final String secretKey = "chatsystem!";
		String seen = "false";
		
		Date date= new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		
		try {
			Encryption encryption = new Encryption();
			String encrypted_message = encryption.encrypt(message, secretKey);
			
			String chat_between_query = "insert into chat(fromid, toid, message, seen, timestamp) values (?,?,?,?,?);"; 
			con = DBConnection.createConnection();
			ps = con.prepareStatement(chat_between_query);
			ps.setString(1, username);
			ps.setString(2, tousername);
			ps.setString(3, encrypted_message);
			ps.setString(4, seen);
			ps.setString(5, ts.toString());
			ps.executeUpdate();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
