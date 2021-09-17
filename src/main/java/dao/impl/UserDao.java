package dao.impl;

import entity.Users;


public interface UserDao {
boolean createUser(Users user);
	
	int editUser(Users user);
	
	Users getUserByPasswordAndLogin(String password, String login);

}
