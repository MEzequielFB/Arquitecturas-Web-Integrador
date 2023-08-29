package db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDAOFactory extends DAOFactory {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/sql_integrador";
	
	@Override
	public void createSchema() throws SQLException {
		Connection conn = createConnection();
		conn.setAutoCommit(false);
		
		//String create_schema = "CREATE SCHEMA IF NOT EXISTS integrador";
		
		String create_cliente = "CREATE TABLE cliente(" +
		"idCliente INT AUTO_INCREMENT," + // PK
		"nombre VARCHAR(500) NOT NULL," +
		"email VARCHAR(150) NOT NULL," +
		"PRIMARY KEY(idCliente))";
		
		String create_producto = "CREATE TABLE producto(" +
		"idProducto INT AUTO_INCREMENT," + // PK
		"nombre VARCHAR(45) NOT NULL," +
		"valor FLOAT(7,2) NOT NULL," + // 10000,00
		"PRIMARY KEY(idProducto))";
		
		String create_factura = "CREATE TABLE factura(" +
		"idFactura INT AUTO_INCREMENT," + // PK
		"idCliente INT," + // Puede ser null - FK
		"PRIMARY KEY(idFactura)," + 
		"FOREIGN KEY (idCliente) REFERENCES cliente(idCliente))";
		
		String create_factura_producto = "CREATE TABLE factura_producto(" +
		"idFactura INT," + // PK - FK
		"idProducto INT," + // PK - FK
		"cantidad INT NOT NULL," +
		"PRIMARY KEY(idFactura, idProducto)," + 
		"FOREIGN KEY (idFactura) REFERENCES factura(idFactura)," +
		"FOREIGN KEY (idProducto) REFERENCES producto(idProducto))";
		
		//conn.prepareStatement(create_schema).execute();
		conn.prepareStatement(create_cliente).execute();
		conn.prepareStatement(create_producto).execute();
		conn.prepareStatement(create_factura).execute();
		conn.prepareStatement(create_factura_producto).execute();
		conn.commit();
		conn.close();
	}
	
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
}
