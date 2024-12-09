package es.uco.pw.data.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

// Crear la conexion con la BBDD

public class DBConnection {

	protected Connection connection = null;

	public Connection getConnection() {
		    
		try {
			
			Context ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			final String url = (String) env.lookup("url");
			final String user = (String) env.lookup("user");
			final String password = (String) env.lookup("password");
				
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = (Connection) DriverManager.getConnection(url, user, password);
			System.err.println("Database connection successfully opened!");
				
		} catch (NamingException e) {
			
			e.printStackTrace();
				
		}catch (SQLException e) {
				
			System.err.println("Connection to MySQL has failed!");
			e.printStackTrace();
				
		} catch (ClassNotFoundException e) {
		
			System.err.println("JDBC Driver not found.");
			e.printStackTrace();
		}
			
		return this.connection;
			
	}

	public void closeConnection() {
		try {
			if(this.connection != null && !this.connection.isClosed()) {
				this.connection.close();
				//System.out.println("		Database connection successfully closed!");
			}
		} catch (SQLException e) {
			System.err.println("		Error while trying to close the connection.");
			e.printStackTrace();
		}
	}
}