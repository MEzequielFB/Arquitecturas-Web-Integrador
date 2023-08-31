package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.FacturaProducto;
import helpers.ConexionHelper;
import interfacesDAO.FacturaProductoDAO;

// Consultas de facturas de productos
public class MysqlFacturaProductoDAO implements FacturaProductoDAO {
	private static MysqlFacturaProductoDAO instancia;
	
	private MysqlFacturaProductoDAO(){}
	
	public static MysqlFacturaProductoDAO getInstancia() {
		if (instancia == null) {
			instancia = new MysqlFacturaProductoDAO();
		}
		return instancia;
	}
	
	public FacturaProducto getBillById(int idFactura) throws SQLException {
		Connection conn = ConexionHelper.createConnection();
        String sql = "SELECT idFactura, idProducto, cantidad FROM factura_producto WHERE idFactura = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idFactura);
        ResultSet resultSet = statement.executeQuery();
        FacturaProducto facturaProducto = null;
        while (resultSet.next()) {
            facturaProducto = new FacturaProducto(resultSet.getInt("idFactura"),resultSet.getInt("idProducto"),resultSet.getInt("cantidad"));
        }
        statement.close();
        ConexionHelper.closeConnection(conn);
        return facturaProducto;
    }
	
	public List<FacturaProducto> getAll() throws SQLException {
		Connection conn = ConexionHelper.createConnection();
    	
        List<FacturaProducto> facturas_productos = new ArrayList<>();
        String sql = "SELECT idFactura, idProducto, cantidad FROM factura_producto";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            FacturaProducto factura_producto = new FacturaProducto(resultSet.getInt("idFactura"),resultSet.getInt("idProducto"),resultSet.getInt("cantidad"));
            facturas_productos.add(factura_producto);
        }
        statement.close();
        ConexionHelper.closeConnection(conn);
        return facturas_productos;
    }

    public void insert(FacturaProducto factura_producto) throws SQLException {
    	Connection conn = ConexionHelper.createConnection();
    	
        String sql = "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, factura_producto.getIdFactura());
            statement.setInt(2, factura_producto.getIdProducto());
            statement.setInt(3, factura_producto.getCantidad());
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

	@Override
	public void insertAll(List<FacturaProducto> facturas_productos) throws SQLException {
		Connection conn = ConexionHelper.createConnection();
		String sql = "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		for (FacturaProducto fp : facturas_productos) {
			statement.setInt(1, fp.getIdFactura());
			statement.setInt(2, fp.getIdProducto());
			statement.setDouble(3, fp.getCantidad());
			
			statement.addBatch();
		}
		statement.executeBatch();
		conn.commit();
		statement.close();
		ConexionHelper.closeConnection(conn);
	}


}
