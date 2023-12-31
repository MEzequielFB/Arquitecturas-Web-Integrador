package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.DTOProductoMasVendido;
import entidades.Producto;
import helpers.ConexionHelper;
import interfacesDAO.ProductoDAO;

// Consultas de productos
public class MysqlProductoDAO implements ProductoDAO {
	private static MysqlProductoDAO instancia;
	
	private MysqlProductoDAO(){}
	
	public static MysqlProductoDAO getInstancia() {
		if (instancia == null) {
			instancia = new MysqlProductoDAO();
		}
		return instancia;
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

    public void insert(Producto producto) throws SQLException {
    	Connection conn = ConexionHelper.createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
        	statement.setInt(1, producto.getIdProducto());
            statement.setString(2, producto.getNombre());
            statement.setDouble(3, producto.getValor());
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
	public void insertAll(List<Producto> productos) throws SQLException {
    	Connection conn = ConexionHelper.createConnection();
		String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		for (Producto p : productos) {
			statement.setInt(1, p.getIdProducto());
			statement.setString(2, p.getNombre());
			statement.setDouble(3, p.getValor());
			
			statement.addBatch();
		}
		statement.executeBatch();
		conn.commit();
		statement.close();
		ConexionHelper.closeConnection(conn);
	}

   /* public Producto moreRaisedProduct() throws SQLException {
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
    } */
    
    public DTOProductoMasVendido moreRaisedProduct() throws SQLException {
        Connection conn = ConexionHelper.createConnection();
        conn.setAutoCommit(false);
        String sql = "SELECT P.idProducto, P.nombre AS nombreProducto, P.valor, SUM(FP.cantidad * P.valor) AS recaudacion "
                + "FROM factura_producto FP "
                + "JOIN producto P"
                + " ON FP.idProducto = P.idProducto "
                + "GROUP BY P.idProducto, P.nombre, P.valor "
                + "ORDER BY recaudacion DESC "
                + "LIMIT 1";
        DTOProductoMasVendido productoDTO = null;
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int idProducto = resultSet.getInt("idProducto");
                String nombreProducto = resultSet.getString("nombreProducto");
                double valor = resultSet.getDouble("valor");
                double recaudacion = resultSet.getDouble("recaudacion");

                productoDTO = new DTOProductoMasVendido(idProducto, nombreProducto, valor, recaudacion);
            }
            conn.commit();
            statement.close();
            ConexionHelper.closeConnection(conn);
            return productoDTO;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // En caso de error, realiza rollback para deshacer los cambios
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
        return productoDTO;
    }
    
}
