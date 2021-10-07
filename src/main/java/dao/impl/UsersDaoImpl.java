package dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import entity.ConnectionPool;
import entity.Orders;
import entity.Users;
import dao.UserDao;

public class UsersDaoImpl implements UserDao {

	private Connection connection;
	private String salt;

	/**
	 * SQL queries
	 */

	private static final String FIND_BY_EMAIL_PASSWORD = "SELECT * FROM users WHERE users.email = ? AND users.password = ?";
	private static final String CREATE_USER = "insert into users(login, password, email) values(?,?,?)";
	private static final String ALL_EMAIL = "select * from users where email = ?";

	private static final String ALL_USERS = "select * from users where users.role_id = 2;";
	public UsersDaoImpl() {

        try {

        	connection =  ConnectionPool.getConnection();
        	salt = "qwerty";
        } catch (Exception e) {
            e.getMessage();
        }
	}
	
	public boolean checkEmail(Users entity) {
		try {
			PreparedStatement pr = null;
			ResultSet rs = null;
			pr = connection.prepareStatement(ALL_EMAIL);
			
			pr.setString(1, entity.getEmail());
			
			rs = pr.executeQuery();
			
			if (rs.next()) {
				return false;
			} else {				
				return true;
			}			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return true;
		}
		
		
		
	}

	public boolean createUser(Users entity) {

		try {
			PreparedStatement pr = null;
			pr = connection.prepareStatement(CREATE_USER);
			
			pr.setString(1, entity.getLogin());
			pr.setString(2, DigestUtils.md5Hex(entity.getPassword()));
			pr.setString(3, entity.getEmail());
			
			pr.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;

		}
		
		return true;

	}

	public Users getUserByPasswordAndLogin(String password, String email) {
		Users user = null;
		if (email != null && password != null) {
			
			PreparedStatement pr = null;
			ResultSet rs = null;
			try {
				pr = connection.prepareStatement(FIND_BY_EMAIL_PASSWORD);
				pr.setString(1, email );
				pr.setString(2, DigestUtils.md5Hex(password));
				
				rs = pr.executeQuery();
				if (rs.next()) {
					return user = createEntity(rs);
				}	
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return null;
			
	}
	
	
	/**
	 * Creates entity from result set
	 * 
	 * @param rs
	 * @return entity
	 */
	
	
	public List<Users> getAllUsers() {
		List<Users> users = new ArrayList<Users>();
		Users user = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(ALL_USERS);
			while (rs.next()) {
				user = createEntity(rs);
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return users;
	}
	
	
	private Users createEntity(ResultSet rs) {
		Users user = new Users();
		
		try {
			user.setId(rs.getInt(1));
			user.setLogin(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setEmail(rs.getString(4));
			user.setRoleId(rs.getInt(5));
		} catch (SQLException e) {
			// LOGGER.error("SQL exception1 " + e.getMessage());
			e.printStackTrace();
		}
		return user;
	}

}
