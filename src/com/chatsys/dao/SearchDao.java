package com.chatsys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;

import com.chatsys.util.DBConnection;
import com.chatsys.util.Encryption;

public class SearchDao {
	public boolean findUsername(String username) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		try {
			String search_query = "select * from user where username=?;"; 
			con = DBConnection.createConnection();
			ps = con.prepareStatement(search_query);
			ps.setString(1, username);
			result = ps.executeQuery();
			if(result.next()) {	
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	public HashSet<String> getUserlist(String username) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		HashSet<String> list = new HashSet<String>();
		
		try {
			String chat_between_query = "select fromid, toid from chat where fromid=? or toid=?"; 
			con = DBConnection.createConnection();
			ps = con.prepareStatement(chat_between_query);
			ps.setString(1, username);
			ps.setString(2, username);
			result = ps.executeQuery();
			while(result.next()) {
				list.add(result.getString(1));
				list.add(result.getString(2));
			}
			return list;
		}catch(Exception e) {
			return null;
		}
	}
	
	public ArrayList<ArrayList<String>> getUserchats(String user1, String user2, String username) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		// secret key for AES encryption of messages
		final String secretKey = "chatsystem!";
				
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		
		try {
			String chat_between_query = "select * from chat where (fromid=? and toid=?) or (fromid=? and toid=?)"; 
			con = DBConnection.createConnection();
			ps = con.prepareStatement(chat_between_query);
			ps.setString(1, user1);
			ps.setString(2, user2);
			ps.setString(3, user2);
			ps.setString(4, user1);
			result = ps.executeQuery();

			while(result.next()) {
				
				ArrayList<String> sublist = new ArrayList<String>();
				
				String encrypted_message = result.getString(4);
				Encryption encryption = new Encryption();
				String decrypted_message = encryption.decrypt(encrypted_message, secretKey);
				
				if(result.getString(2).equals(username)){
					sublist.add('"' + result.getString(2) + '"');
					sublist.add('"' + decrypted_message + '"');
					sublist.add('"' + "sent" + '"');
					sublist.add('"' + result.getString(5) + '"');
					sublist.add('"' + result.getString(6) + '"');
				}else if(result.getString(3).equals(username)){
					sublist.add('"' + result.getString(3) + '"');
					sublist.add('"' + decrypted_message + '"');
					sublist.add('"' + "received" + '"');
					sublist.add('"' + result.getString(5) + '"');
					sublist.add('"' + result.getString(6) + '"');
				}
				list.add(sublist);
			}
			return list;
		}catch(Exception e) {
			return null;
		}
	}
	
	public String getUnseenChats(String username, String tousername) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		String seen = "false";
		
		try {
			String chat_between_query = "select count(*) from chat where fromid=? and toid=? and seen=?"; 
			con = DBConnection.createConnection();
			ps = con.prepareStatement(chat_between_query);
			ps.setString(1, tousername);
			ps.setString(2, username);
			ps.setString(3, seen);
			result = ps.executeQuery();
			if(result.next()) {
				return result.getString(1);
			}
		}catch(Exception e) {
			return null;
		}
		return null;
	}
	
	public String getLastChat(String username, String tousername) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
				
		// secret key for AES encryption of messages
		final String secretKey = "chatsystem!";
				
		String output = "";
		try {
			String chat_between_query = "select fromid, toid, message from chat where (fromid=? and toid=?) or (fromid=? and toid=?) order by message_id desc limit 1"; 
			con = DBConnection.createConnection();
			ps = con.prepareStatement(chat_between_query);
			ps.setString(1, username);
			ps.setString(2, tousername);
			ps.setString(3, tousername);
			ps.setString(4, username);
			result = ps.executeQuery();
	
			if(result.next()) {
				String encrypted_message = result.getString(3);
				Encryption encryption = new Encryption();
				String decrypted_message = encryption.decrypt(encrypted_message, secretKey);
				
				output += decrypted_message + ",";
				
				if(result.getString(1).equals(username) && result.getString(2).equals(tousername)){
					output += "sent";
				}else if(result.getString(2).equals(username) && result.getString(1).equals(tousername)){
					output += "received";
				}

				return output;
			}
		}catch(Exception e) {
			return null;
		}
		return null;
	}
}
