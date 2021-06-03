package connect;

import java.sql.*;

public class dbCon {
		static String DRIVER = "com.mysql.jdbc.Driver";
		static String CONNECTION_URL = "jdbc:mysql://localhost:3306/ooplecture?verifyServerCertificate=false&useSSL=true";
		static String USERNAME = "root";
		static String PASSWORD = ""; //password for your mysql

		public static Connection getConnection() {
			Connection con = null;
			try {
				Class.forName(DRIVER);
				con = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
			} catch (Exception e) {
				System.out.println(e);
			}
			return con;
		}
	}

