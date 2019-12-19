package com.chatsys.api;

import java.util.ArrayList;
import java.util.HashSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.chatsys.dao.LoginDao;
import com.chatsys.dao.RegisterDao;
import com.chatsys.dao.SearchDao;
import com.chatsys.dao.SendDao;
import com.chatsys.dao.UpdateDao;

@Path("/chatsystem")
public class ChatSystem {
	
	@GET
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	public String authenticate(@QueryParam("username") String username) {
		
		String result = null;
		
		try {
			LoginDao loginDao = new LoginDao();
			boolean status = loginDao.authenticateUser(username);
			if(status) {
				result = "success";
			}else {
				result = "invalid";
			}
		}catch(Exception e) {
			result = "error";
		}
		return result;
	}
	
	@GET
	@Path("/register")
	@Produces(MediaType.TEXT_HTML)
	public String register(@QueryParam("username") String username) {
		
		String result = null;
		
		try {
			RegisterDao registerDao = new RegisterDao();
			boolean status = registerDao.registerUser(username);
			if(status) {
				result = "success";
			}else {
				result = "error";  //username is primary key in table
			}
		}catch(Exception e) {
			result = "error";
		}
		return result;
	}
	
	@GET
	@Path("/findusername")
	@Produces(MediaType.APPLICATION_JSON)
	public String find(@QueryParam("username") String username) {
		
		String result = null;
		
		try {
			SearchDao searchDao = new SearchDao();
			boolean status = searchDao.findUsername(username);
			if(status) {
				result = "success";
			}else {
				result = "error";
			}
		}catch(Exception e) {
			result = "error";
		}
		return result;
	}	
	
	@GET
	@Path("/userlist")
	@Produces(MediaType.APPLICATION_JSON)
	public String getList(@QueryParam("for") String username) {
		
		String result = "";
		
		try {
			SearchDao searchDao = new SearchDao();
			HashSet<String> list = searchDao.getUserlist(username);
			
			if(list != null) {
				for(String element: list) {
					if(!element.equals(username)) {
						result += element + ",";
					}
				}
			}else {
				result = "nouser";
			}
		}catch(Exception e) {
			result = "error";
		}
		return result;
	}	
	
	@GET
	@Path("/unseenchats")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUseen(@QueryParam("from") String username, @QueryParam("to") String tousername) {
		
		String result = "";
		
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			SearchDao searchDao = new SearchDao();
			String unseen_chats = searchDao.getUnseenChats(username, tousername);
			String[] lastchat = searchDao.getLastChat(username, tousername).split(",");
			
			list.add('"' + unseen_chats + '"');
			list.add('"' + lastchat[0] + '"');
			list.add('"' + lastchat[1] + '"');
			
			if(list != null) {
				result = list.toString();
			}
			
		}catch(Exception e) {
			result = "error";
		}
		return result;
	}	
	
	@GET
	@Path("/getchats")
	@Produces(MediaType.TEXT_HTML)
	public String getList(@QueryParam("username") String username, @QueryParam("user1") String user1, @QueryParam("user2") String user2) {
		
		String result = "";
				
		try {
			SearchDao searchDao = new SearchDao();
			ArrayList<ArrayList<String>> list = searchDao.getUserchats(user1, user2, username);
		
			if(list != null) {
				result = list.toString();				
			}else {
				result = "nomsg";
			}
		}catch(Exception e) {
			result = "error";
		}
		return result;
	}	
	
	@GET
	@Path("/updateseen")
	@Produces(MediaType.TEXT_HTML)
	public String updateSeen(@QueryParam("from") String username, @QueryParam("to") String tousername) {
		
		String result = "";
				
		try {
			UpdateDao updateDao = new UpdateDao();
			boolean status = updateDao.updateSeen(username, tousername);
		
			if(status){
				result = "success";				
			}else {
				result = "error";
			}
		}catch(Exception e) {
			result = "error";
		}
		return result;
	}	
	
	@GET
	@Path("/sendchat")
	@Produces(MediaType.TEXT_HTML)
	public String send(@QueryParam("username") String username, @QueryParam("tousername") String tousername, @QueryParam("message") String message) {
		
		String result = "";
				
		try {
			SendDao sendDao = new SendDao();
			boolean status = sendDao.sendChat(username, tousername, message);
		
			if(status) {
				result = "success";				
			}else {
				result = "error";
			}
		}catch(Exception e) {
			result = "error";
		}
		return result;
	}	
	
}
