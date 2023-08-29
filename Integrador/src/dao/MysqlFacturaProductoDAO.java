package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.FacturaProducto;
import factories.MysqlDAOFactory;

// Consultas de facturas de productos
public class MysqlFacturaProductoDAO implements EntityDAO {
    
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
    	
    	String create_table = "CREATE TABLE factura_producto(" +
		"idFactura INT," + // PK - FK
		"idProducto INT," + // PK - FK
		"cantidad INT NOT NULL," +
		"PRIMARY KEY(idFactura, idProducto)," + 
		"FOREIGN KEY (idFactura) REFERENCES factura(idFactura)," +
		"FOREIGN KEY (idProducto) REFERENCES producto(idProducto))";
    	
    	conn.prepareStatement(create_table).execute();
    	conn.commit();
    	
    }
	
	public FacturaProducto /*List<FacturaProducto>*/ getBillById(int idFactura) throws SQLException {
		Connection conn = createConnection();
    	conn.setAutoCommit(false);
		
        //List<FacturaProducto> facturaProductos = new ArrayList<>();
        String sql = "SELECT idFactura, idProducto, cantidad FROM factura_producto WHERE idFactura = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idFactura);
        ResultSet resultSet = statement.executeQuery();
        FacturaProducto facturaProducto = null;
        while (resultSet.next()) {
            facturaProducto = new FacturaProducto(resultSet.getInt("idFactura"),resultSet.getInt("idProducto"),resultSet.getInt("cantidad"));
            //facturaProductos.add(facturaProducto);
        }
        //return facturaProductos;
        statement.close();
        closeConnection(conn);
        return facturaProducto;
    }
	
	public List<FacturaProducto> getAll() throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        List<FacturaProducto> facturas_productos = new ArrayList<>();
        String sql = "SELECT idFactura, idProducto, cantidad FROM factura_producto";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            FacturaProducto factura_producto = new FacturaProducto(resultSet.getInt("idFactura"),resultSet.getInt("idProducto"),resultSet.getInt("cantidad"));
            facturas_productos.add(factura_producto);
        }
        statement.close();
        closeConnection(conn);
        return facturas_productos;
    }

    public void insert(FacturaProducto facturaProducto) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, facturaProducto.getIdFactura());
            statement.setInt(2, facturaProducto.getIdProducto());
            statement.setInt(3, facturaProducto.getCantidad());
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

    public void update(FacturaProducto facturaProducto) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "UPDATE factura_producto SET cantidad = ? WHERE idFactura = ? AND idProducto = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, facturaProducto.getCantidad());
            statement.setInt(2, facturaProducto.getIdFactura());
            statement.setInt(3, facturaProducto.getIdProducto());
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

    public void delete(int idFactura, int idProducto) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "DELETE FROM factura_producto WHERE idFactura = ? AND idProducto = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idFactura);
            statement.setInt(2, idProducto);
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
