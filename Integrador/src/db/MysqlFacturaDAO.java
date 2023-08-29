package db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Factura;

// Consultas de facturas
public class MysqlFacturaDAO {
	 	
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/sql_integrador";
	private Connection conn;
	
    public MysqlFacturaDAO() {
        try {
            conn = createConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Factura obtenerPorId(int idFactura) {
        String sql = "SELECT idFactura, idCliente FROM factura WHERE idFactura = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idFactura);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Factura factura = new Factura(resultSet.getInt("idFactura"),resultSet.getInt("idCliente"));
                    return factura;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Factura> obtenerTodos() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT idFactura, idCliente FROM factura";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Factura factura = new Factura(resultSet.getInt("idFactura"),resultSet.getInt("idCliente"));
                facturas.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturas;
    }

    public void insertar(Factura factura) {
        String sql = "INSERT INTO factura (idCliente) VALUES (?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, factura.getIdCliente());
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

    public void actualizar(Factura factura) {
        String sql = "UPDATE factura SET idCliente = ? WHERE idFactura = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, factura.getIdCliente());
            statement.setInt(2, factura.getIdFactura());
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

    public void eliminar(int idFactura) {
        String sql = "DELETE FROM factura WHERE idFactura = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idFactura);
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
