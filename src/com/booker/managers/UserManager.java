package com.booker.managers;

import java.util.List;

import com.booker.dao.UserDao;
import com.booker.entities.User;
import com.booker.util.StringUtil;
import com.booker.constants.GenderEnum;
import com.booker.constants.UserTypeEnum;


public class UserManager {
	private static UserManager instance = new UserManager();
	private static UserDao dao = new UserDao();
	
	private UserManager() {
	}

	// This a global point of access
	// It public since one can't not create a instance user manager
	public static UserManager getInstance() {
		return instance;
		// When the client could says user manager
		// e.g .getInstance() and the very first time
		// it does that jvm loads the class i.e UserManager class
		// into memory and at the same time execute the first statement
		// and an instance will be created. This happens when this
		// method is invoked for the very first time
	}

	// method for instatiling user
	public User createUser(long id, String email, String password, String firstName, String lastName, GenderEnum gender,
			UserTypeEnum userType) {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setUserType(userType);

		return user;
	}
	
	//This will invoke by the controller layer
	
	public List<User> getUsers() {
		return dao.getUser();
	}

	public User getUser(long userId) {
		return dao.getUser(userId);
	}

	public long authenticate(String email, String password) {
		//return dao.authenticate(email, StringUtil.encodePassword(password));
		return dao.authenticate(email, password);
	}
}
