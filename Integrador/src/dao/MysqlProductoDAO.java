package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import entidades.Producto;
import helpers.ConexionHelper;

// Consultas de productos
public class MysqlProductoDAO implements EntityDAO {
	

  
    @Override
    public void createTable() throws SQLException {
    	Connection conn = ConexionHelper.createConnection();
    	
    	String create_table = "CREATE TABLE IF NOT EXISTS producto(" +
		"idProducto INT AUTO_INCREMENT," + // PK
		"nombre VARCHAR(45) NOT NULL," +
		"valor FLOAT(7,2) NOT NULL," + // 10000,00
		"PRIMARY KEY(idProducto))";
    	
    	conn.prepareStatement(create_table).execute();
    	conn.commit();
    	ConexionHelper.closeConnection(conn);
    }
    
    @Override
	public void poblateTable(String path) throws FileNotFoundException, IOException, SQLException {
		Connection conn = ConexionHelper.createConnection();

		CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(
				new FileReader(path));
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
		ConexionHelper.closeConnection(conn);
	}
	
	
	public Producto getById(int idProducto) throws SQLException {
		Connection conn = ConexionHelper.createConnection();
    	
		
        String sql = "SELECT idProducto, nombre, valor FROM producto WHERE idProducto = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idProducto);
        ResultSet resultSet = statement.executeQuery();
        Producto producto = null;
        if (resultSet.next()) {
            producto = new Producto(resultSet.getInt("idProducto"),resultSet.getString("nombre"),resultSet.getDouble("valor"));
        }
        statement.close();
        ConexionHelper.closeConnection(conn);
        return producto;
    }


    public List<Producto> getAll() throws SQLException {
    	Connection conn = ConexionHelper.createConnection();
    	conn.setAutoCommit(false);
    	
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT idProducto, nombre, valor FROM producto";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Producto producto = new Producto(resultSet.getInt("idProducto"),resultSet.getString("nombre"),resultSet.getDouble("valor"));
            productos.add(producto);
        }
        statement.close();
        ConexionHelper.closeConnection(conn);
        return productos;
    }

    public void insert(String nombre, double valor) throws SQLException {
    	Connection conn = ConexionHelper.createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "INSERT INTO producto (nombre, valor) VALUES (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nombre);
            statement.setDouble(2, valor);
            statement.executeUpdate();
            conn.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
            	conn.rollback(); // En caso de error, realiza rollback para deshacer los cambios
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
        ConexionHelper.closeConnection(conn);
    }

   
    public Producto moreRaisedProduct() throws SQLException{
    	Connection conn = ConexionHelper.createConnection();
    	conn.setAutoCommit(false);
        String sql = "SELECT P.idProducto, P.nombre AS nombreProducto, P.valor, SUM(FP.cantidad * P.valor) AS recaudacion "
        		+ "FROM factura_producto FP "
    			+ "JOIN producto P"
    			+ " ON FP.idProducto = P.idProducto "
    			+ "GROUP BY P.idProducto, P.nombre, P.valor "
    			+ "ORDER BY recaudacion DESC "
    			+ "LIMIT 1";
        Producto producto = null;
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
        	ResultSet resultSet = statement.executeQuery();
        	if (resultSet.next()) {
        		producto = new Producto(resultSet.getInt("idProducto"),resultSet.getString("nombreProducto"),resultSet.getDouble("valor"));
        	}
            conn.commit();
            statement.close();
            ConexionHelper.closeConnection(conn);
            return producto;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
            	conn.rollback(); // En caso de error, realiza rollback para deshacer los cambios
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
        return producto;
    }
    
    
}
