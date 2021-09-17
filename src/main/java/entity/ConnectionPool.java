package entity;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {
   
    private ConnectionPool(){
        //private constructor
    }

    private static ConnectionPool instance = null;
   
    public static ConnectionPool getInstance(){
        if (instance==null)
            instance = new ConnectionPool();
        return instance;
    }
   
    public static Connection getConnection(){

        
    	Connection connection = null;
        InitialContext initContext;
		try {
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/appname");
       
			connection = ds.getConnection();
		
		} catch ( NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e ) {
			e.printStackTrace();
		}
		return connection;
        
}
}