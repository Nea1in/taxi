package dao;

import entity.Users;

public interface UserDao {
	boolean createUser(Users user);

	Users getUserByPasswordAndLogin(String password, String login);

}
