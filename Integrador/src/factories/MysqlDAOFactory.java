package factories;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.MysqlClienteDAO;
import dao.MysqlFacturaDAO;
import dao.MysqlFacturaProductoDAO;
import dao.MysqlProductoDAO;

public class MysqlDAOFactory extends DAOFactory {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/sql_integrador";
	
	@Override
	public MysqlClienteDAO getClienteDAO() {
		return new MysqlClienteDAO();
	}

	@Override
	public MysqlProductoDAO getProductoDAO() {
		return new MysqlProductoDAO();
	}

	@Override
	public MysqlFacturaDAO getFacturaDAO() {
		return new MysqlFacturaDAO();
	}

	@Override
	public MysqlFacturaProductoDAO getFacturaProductoDAO() {
		return new MysqlFacturaProductoDAO();
	}
	
	public static Connection createConnection() {
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
			return DriverManager.getConnection(DBURL, "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
}
