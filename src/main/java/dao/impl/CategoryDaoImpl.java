package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Categories;
import entity.ConnectionPool;

public class CategoryDaoImpl implements CategoryDao {
//	private QueryExecutor executor = QueryExecutor.getInstance();
	private Connection connection;
	
	
	public CategoryDaoImpl() {
		 try {

			 connection =  ConnectionPool.getConnection();
        } catch (Exception e) {
            e.getMessage();
        }
	}
	
	public List<Categories> getAllCategories()  {
		List<Categories> categories = new ArrayList<Categories>();
		Categories category = null;		
		try {
			System.out.println(connection);
			Statement statement= connection.createStatement();
	        ResultSet resultSet= statement.executeQuery("SELECT * FROM categories");
	        //System.out.println(resultSet);
	        while (resultSet.next()){
	            category = createEntity(resultSet);
				categories.add(category);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return categories;
	}
	
	/**
	 * Creates entity from result set
	 * 
	 * @param rs
	 * @return entity
	 */
	private Categories createEntity(ResultSet rs) {
		Categories category = new Categories();
		try {
			category.setId(rs.getInt(1));
			category.setName(rs.getString(2));
			category.setCapacity(rs.getInt(3));
		} catch (SQLException e) {
		//	LOGGER.error("SQL exception1 " + e.getMessage());
			e.printStackTrace();
		}
		return category;
	}

}
