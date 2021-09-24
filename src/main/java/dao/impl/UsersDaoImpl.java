package dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import org.apache.commons.codec.digest.DigestUtils;

import entity.ConnectionPool;

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
			try {
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				System.out.println(rs);
				
				if (rs.next()) {
					System.out.println("find");
					System.out.println(rs.getInt(1));
					return user = createEntity(rs);
				}	
				System.out.println("no");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
