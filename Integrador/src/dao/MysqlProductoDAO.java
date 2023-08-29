package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public Producto obtenerPorId(int idProducto) throws SQLException {
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


    public List<Producto> obtenerTodos() throws SQLException {
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

    public void insertar(Producto producto) throws SQLException {
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


    public void actualizar(Producto producto) throws SQLException {
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


    public void eliminar(int idProducto) throws SQLException {
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
}
