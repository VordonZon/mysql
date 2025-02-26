package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import projects.exception.DbException;

public class DbConnection {
	private static final String SCHEMA = "project";
	private static final String USER = "project";
	private static final String PASSWORD = "project";
	private static final String HOST = "localhost";
	private static final int PORT = 3306;

	//Connects to database with URL
	public static Connection getConnection() {
		String url = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false", HOST, PORT, SCHEMA, USER, PASSWORD);
		try {
			Connection conn = DriverManager.getConnection(url);
			System.out.println("Successfully obtained connection!");
			return conn;
		} catch (SQLException e) {
			//throws error if it doesn't connect to database
			System.out.println("Error getting exception.");
			throw new DbException(e);
		}
	}
}
