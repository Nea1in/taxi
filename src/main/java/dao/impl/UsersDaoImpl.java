package dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import entity.ConnectionPool;
import entity.Roles;
import entity.Users;

public class UsersDaoImpl implements UserDao {

	private Connection connection;
	private QueryExecutor executor = QueryExecutor.getInstance();
	private String salt;

	/**
	 * SQL queries
	 */

	private static final String FIND_BY_EMAIL_PASSWORD = "SELECT * FROM users WHERE users.email = ? AND users.password = ?";
	private static final String CREATE_USER = "INSERT INTO users (login, password, email, role_id) VALUES(?, ?, ?, ?)";
	private static final String UPDATE_USER = "UPDATE users SET password = ?, email = ? WHERE id = ?";



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
			pr = connection.prepareStatement("select * from users where email = ?");
			
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

		/*
		 * Object[] args = { entity.getLogin(), entity.getPassword(), entity.getEmail(),
		 * entity.getRoleId() }; return executor.executeStatement(CREATE_USER, args);
		 */

		// Connection con = getConnection();

		try {
			PreparedStatement pr = null;
			pr = connection.prepareStatement("insert into users(login, password, email) values(?,?,?)");
			
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

	@Override
	public int editUser(Users user) {
		// TODO Auto-generated method stub
		return 0;
	}
}
