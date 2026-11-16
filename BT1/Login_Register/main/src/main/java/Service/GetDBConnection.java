package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.*;

public class GetDBConnection {
	public static Connection getconn()throws Exception{
		String url = "jdbc:mysql://localhost:3306/Application";
		String user = "root";
		String password = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
