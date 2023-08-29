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
import factories.MysqlDAOFactory;

// Consultas de productos
public class MysqlProductoDAO implements EntityDAO {
	
	@Override
	public Connection createConnection() throws SQLException {
		return MysqlDAOFactory.createConnection();
	}

	@Override
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
    
    @Override
    public void createTable() throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
    	String create_table = "CREATE TABLE producto(" +
		"idProducto INT AUTO_INCREMENT," + // PK
		"nombre VARCHAR(45) NOT NULL," +
		"valor FLOAT(7,2) NOT NULL," + // 10000,00
		"PRIMARY KEY(idProducto))";
    	
    	conn.prepareStatement(create_table).execute();
    	conn.commit();
    	closeConnection(conn);
    }
    
	public void poblateTable(String path) throws FileNotFoundException, IOException, SQLException {
		Connection conn = createConnection();
		conn.setAutoCommit(false);

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
		closeConnection(conn);
	}
	
	
	public Producto getById(int idProducto) throws SQLException {
		Connection conn = createConnection();
    	conn.setAutoCommit(false);
		
        String sql = "SELECT idProducto, nombre, valor FROM producto WHERE idProducto = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idProducto);
        ResultSet resultSet = statement.executeQuery();
        Producto producto = null;
        if (resultSet.next()) {
            producto = new Producto(resultSet.getInt("idProducto"),resultSet.getString("nombre"),resultSet.getDouble("valor"));
        }
        statement.close();
        closeConnection(conn);
        return producto;
    }


    public List<Producto> getAll() throws SQLException {
    	Connection conn = createConnection();
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
        closeConnection(conn);
        return productos;
    }

    public void insert(Producto producto) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "INSERT INTO producto (nombre, valor) VALUES (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getValor());
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
        closeConnection(conn);
    }


    public void update(Producto producto) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "UPDATE producto SET nombre = ?, valor = ? WHERE idProducto = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getValor());
            statement.setInt(3, producto.getIdProducto());
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
        closeConnection(conn);
    }


    public void delete(int idProducto) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "DELETE FROM producto WHERE idProducto = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idProducto);
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
        closeConnection(conn);
    }
    
    public Producto moreRaisedProduct() throws SQLException{
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
        String sql = "SELECT P.idProducto,P.nombre AS nombreProducto, SUM(FP.cantidad * PR.valor) AS recaudacion FROM Factura_Producto FP JOIN Producto PR ON FP.idProducto = PR.idProducto JOIN Producto P ON PR.idProducto = P.idProducto GROUP BY P.idProducto, P.nombre ORDER BY recaudacion DESC LIMIT 1";
        Producto producto = null;
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
        	ResultSet resultSet = statement.executeQuery();
        	if (resultSet.next()) {
        		producto = new Producto(resultSet.getInt("idProducto"),resultSet.getString("nombreProducto"),resultSet.getDouble("recaudacion"));
        	}
            conn.commit();
            statement.close();
            closeConnection(conn);
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
