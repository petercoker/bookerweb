package com.booker.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import com.booker.DataStore;
import com.booker.constants.GenderEnum;
import com.booker.constants.UserTypeEnum;
import com.booker.entities.User;
import com.booker.managers.UserManager;
import com.booker.util.StringUtil;

public class UserDao {
	
	//This fetches data from the data source
	public List<User> getUser() {
		return DataStore.getUsers();
	}
	
	// Fetch a single row from data base
	public User getUser(long userId) {
		User user = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/booker?useSSL=false", "root",
				"vCEyuK6md6UuoM"); /* a) Database User Profile: root is who the user is b) Database user password */
				Statement stmt = conn.createStatement();) /* execute mysql queries */ {

			String query = "Select * from User where id = " + userId;
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				long id = rs.getLong("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				int gender_id = rs.getInt("gender_id");
				GenderEnum gender = GenderEnum.values()[gender_id];
				int user_type_id = rs.getInt("user_type_id");
				UserTypeEnum userType = UserTypeEnum.values()[user_type_id];
				
				user = UserManager.getInstance().createUser(id, email, password, firstName, lastName, gender, userType);
	    	}			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return user;
		
	}
	
	
//	public User createUser(String email, String password, String firstName, String lastName, GenderEnum gender,
//			UserTypeEnum userType) {
//		User user = null;
//		
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");	
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/booker?useSSL=false", "root",
//				"vCEyuK6md6UuoM"); /* a) Database User Profile: root is who the user is b) Database user password */
//				Statement stmt = conn.createStatement();) /* execute mysql queries */ {
//
//			String query = "INSERT INTO User (email, password, first_name, last_name, gender_id, user_type_id, created_date)\r\n" + 
//					"VALUES ("
//						  + " " + email + ","
//					      + " " + password + ","
//					      + " " + firstName + ","
//					      + " " + lastName + ","
//			              + " " + gender + ","
//			              + " " + userType   + ","
//			              + "NOW()"
//			              + ")";
//			              
//			System.out.println("query: " + query);
//			ResultSet rs = stmt.executeQuery(query);
//	    	
//			while (rs.next()) {
//				long id = rs.
//				
//				String email = rs.getString("email");
//				String password = rs.getString("password");
//				String firstName = rs.getString("first_name");
//				String lastName = rs.getString("last_name");
//				int gender_id = rs.getInt("gender_id");
//				GenderEnum gender = GenderEnum.values()[gender_id];
//				int user_type_id = rs.getInt("user_type_id");
//				UserTypeEnum userType = UserTypeEnum.values()[user_type_id];
//				
//				user = UserManager.getInstance().createUser(id, email, password, firstName, lastName, gender, userType);
//	    	}		
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}	
//		
//		return user;
//		
//	}
	
	

	public long authenticate(String email, String encodePassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/booker?useSSL=false", "root",
				"vCEyuK6md6UuoM"); /* a) Database User Profile: root is who the user is b) Database user password */
				Statement stmt = conn.createStatement();) /* execute mysql queries */ {	
			String query = "Select id from User where email = '" + email + "' and password = '" + encodePassword + "'";
			System.out.println("query: " + query);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				// if the user id is there get it
				return rs.getLong("id");				
	    	}			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		// if the user id not there return -1 (authority failed)
		return -1;
	}
	
//	public long authenticate(String email, String password) {
//		
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/booker?useSSL=false", "root",
//				"vCEyuK6md6UuoM"); /* a) Database User Profile: root is who the user is b) Database user password */
//				Statement stmt = conn.createStatement();) /* execute mysql queries */ {	
//			
//			String query = "Select id from User where email = '" + email + "' and password = '" + password + "'";
//			System.out.println("query: " + query);
//			ResultSet rs = stmt.executeQuery(query);
//			
//			
//			while (rs.next()) {
//				// if the user id is there get it
//				return rs.getLong("id");				
//	    	}			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}	
//		
//		// if the user id not there return -1 (authority failed)
//		return -1;
//	}
}
