package com.chatsys.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection createConnection() {
		Connection con = null;
		
		String url = "jdbc:mysql://localhost/chatsystem"; //MySQL URL and followed by the database name
		String username = "mehul"; //MySQL username
		String password = "mehul"; //MySQL password
		
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver"); //loading mysql driver 
			} 
			catch (ClassNotFoundException e){
				e.printStackTrace();
			} 
			con = DriverManager.getConnection(url, username, password); //attempting to connect to MySQL database
			return con;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return con; 
	}
}
