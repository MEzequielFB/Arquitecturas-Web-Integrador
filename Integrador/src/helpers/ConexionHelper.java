package helpers;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionHelper {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/sql_integrador";
	private static Connection conn;

	public static Connection createConnection() throws SQLException {
		if (conn == null || conn.isClosed()) {
			try {
				Class.forName(DRIVER).getDeclaredConstructor().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			try {
				conn = DriverManager.getConnection(DBURL, "root", "");
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		return conn;
	}

	
	public static void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

}
