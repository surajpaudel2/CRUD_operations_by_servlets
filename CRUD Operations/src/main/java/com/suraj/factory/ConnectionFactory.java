package com.suraj.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static Connection con;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
		}
		catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		return con;
	}
	public static void cleanUp() {
		try {
			con.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
