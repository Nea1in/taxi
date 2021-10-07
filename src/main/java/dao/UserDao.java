package dao;

import java.util.List;

import entity.Users;

public interface UserDao {

	boolean checkEmail(Users entity);

	boolean createUser(Users user);

	Users getUserByPasswordAndLogin(String password, String login);
	
	 List<Users> getAllUsers();

}
