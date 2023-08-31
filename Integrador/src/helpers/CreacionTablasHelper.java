package helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CreacionTablasHelper {
	private static String path_cliente = "src/csv/clientes.csv";
	private static String path_producto = "src/csv/productos.csv";
	private static String path_factura = "src/csv/facturas.csv";
	private static String path_factura_producto = "src/csv/facturas-productos.csv";
	
	public static void createSchema() throws SQLException {
		Connection conn = ConexionHelper.createConnection();
		String create_cliente = "CREATE TABLE IF NOT EXISTS cliente("
				+ "idCliente INT," // PK
				+ "nombre VARCHAR(500) NOT NULL,"
				+ "email VARCHAR(150) NOT NULL,"
				+ "PRIMARY KEY(idCliente))";
		
		String create_producto = "CREATE TABLE IF NOT EXISTS producto("
    			+ "idProducto INT," // PK
    			+ "nombre VARCHAR(45) NOT NULL,"
    			+ "valor FLOAT(7,2) NOT NULL," // 10000,00
    			+ "PRIMARY KEY(idProducto))";
		
    	String create_factura = "CREATE TABLE IF NOT EXISTS factura("
    			+ "idFactura INT," // PK
    			+ "idCliente INT," // Puede ser null - FK
    			+ "PRIMARY KEY(idFactura)," 
    			+ "FOREIGN KEY (idCliente) REFERENCES cliente(idCliente))";
    	
    	String create_factura_producto = "CREATE TABLE IF NOT EXISTS factura_producto("
    			+ "idFactura INT," // PK - FK
    			+ "idProducto INT," // PK - FK
    			+ "cantidad INT NOT NULL,"
    			+ "PRIMARY KEY(idFactura, idProducto)," 
    			+ "FOREIGN KEY (idFactura) REFERENCES factura(idFactura),"
    			+ "FOREIGN KEY (idProducto) REFERENCES producto(idProducto))";

		conn.prepareStatement(create_cliente).execute();
		conn.prepareStatement(create_producto).execute();
		conn.prepareStatement(create_factura).execute();
		conn.prepareStatement(create_factura_producto).execute();
		conn.commit();
		ConexionHelper.closeConnection(conn);
	}
	
	public static void poblateTables() throws SQLException, FileNotFoundException, IOException {
		Connection conn = ConexionHelper.createConnection();
		
		CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(
				new FileReader(path_cliente));
		for (CSVRecord row : parser) {
			String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, Integer.valueOf(row.get("idCliente")));
			statement.setString(2, row.get("nombre"));
			statement.setString(3, row.get("email"));
			statement.executeUpdate();
			conn.commit();
			statement.close();
		}
		
		parser = CSVFormat.DEFAULT.withHeader().parse(
				new FileReader(path_producto));
		for (CSVRecord row : parser) {
			String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, Integer.valueOf(row.get("idProducto")));
			statement.setString(2, row.get("nombre"));
			statement.setFloat(3, Float.valueOf(row.get("valor")));
			statement.executeUpdate();
			conn.commit();
			statement.close();
		}
		
		parser = CSVFormat.DEFAULT.withHeader().parse(
				new FileReader(path_factura));
		for (CSVRecord row : parser) {
			String sql = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, Integer.valueOf(row.get("idFactura")));
			statement.setInt(2, Integer.valueOf(row.get("idCliente")));
			statement.executeUpdate();
			conn.commit();
			statement.close();
		}
		
		parser = CSVFormat.DEFAULT.withHeader().parse(
				new FileReader(path_factura_producto));
		for (CSVRecord row : parser) {
			String sql = "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, Integer.valueOf(row.get("idFactura")));
			statement.setInt(2, Integer.valueOf(row.get("idProducto")));
			statement.setInt(3, Integer.valueOf(row.get("cantidad")));
			statement.executeUpdate();
			conn.commit();
			statement.close();
		}
		
		
		ConexionHelper.closeConnection(conn);
	}
}
