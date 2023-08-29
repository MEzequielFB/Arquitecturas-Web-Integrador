package db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.FacturaProducto;

// Consultas de facturas de productos
public class MysqlFacturaProductoDAO {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/sql_integrador";
	private Connection conn;
	
    public MysqlFacturaProductoDAO() {
        try {
            conn = createConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public List<FacturaProducto> obtenerPorIdFactura(int idFactura) {
        List<FacturaProducto> facturaProductos = new ArrayList<>();
        String sql = "SELECT idFactura, idProducto, cantidad FROM factura_producto WHERE idFactura = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idFactura);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FacturaProducto facturaProducto = new FacturaProducto(resultSet.getInt("idFactura"),resultSet.getInt("idProducto"),resultSet.getInt("cantidad"));
                    facturaProductos.add(facturaProducto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturaProductos;
    }

    public void insertar(FacturaProducto facturaProducto) {
        String sql = "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, facturaProducto.getIdFactura());
            statement.setInt(2, facturaProducto.getIdProducto());
            statement.setInt(3, facturaProducto.getCantidad());
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
            	conn.rollback(); // En caso de error, realiza rollback para deshacer los cambios
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }

    public void actualizar(FacturaProducto facturaProducto) {
        String sql = "UPDATE factura_producto SET cantidad = ? WHERE idFactura = ? AND idProducto = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, facturaProducto.getCantidad());
            statement.setInt(2, facturaProducto.getIdFactura());
            statement.setInt(3, facturaProducto.getIdProducto());
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
            	conn.rollback(); // En caso de error, realiza rollback para deshacer los cambios
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }

    public void eliminar(int idFactura, int idProducto) {
        String sql = "DELETE FROM factura_producto WHERE idFactura = ? AND idProducto = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idFactura);
            statement.setInt(2, idProducto);
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
            	conn.rollback(); // En caso de error, realiza rollback para deshacer los cambios
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
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
			return DriverManager.getConnection(DBURL, "root", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
