package com.booker.dao;

import java.util.List;

import com.booker.DataStore;
import com.booker.entities.User;

public class UserDao {
	
	//This fetches data from the data source
	public List<User> getUser() {
		return DataStore.getUsers();
	}
}
