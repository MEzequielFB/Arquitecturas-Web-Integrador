package db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Cliente;

// Consultas de clientes
public class MysqlClienteDAO {
		public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
		public static final String DBURL = "jdbc:mysql://localhost:3306/sql_integrador";
		private Connection conn;
		
	    public MysqlClienteDAO() {
	        try {
	            conn = createConnection();
	            conn.setAutoCommit(false);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public Cliente getClientById(int idCliente) {
	        String sql = "SELECT idcliente, nombre, email FROM cliente WHERE idcliente = ?";
	        try (PreparedStatement statement = conn.prepareStatement(sql)) {
	            statement.setInt(1, idCliente);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    Cliente cliente = new Cliente(resultSet.getInt("idcliente"),resultSet.getString("nombre"),resultSet.getString("email"));
	                    return cliente;
	                }
	                conn.commit();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public List<Cliente> getAllClients() {
	        List<Cliente> clientes = new ArrayList<>();
	        String sql = "SELECT idcliente, nombre, email FROM cliente";
	        try (PreparedStatement statement = conn.prepareStatement(sql);
	             ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Cliente cliente = new Cliente(resultSet.getInt("idcliente"),resultSet.getString("nombre"),resultSet.getString("email"));
	                clientes.add(cliente);
	            }
	            conn.commit();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return clientes;
	    }

	    public void insert(Cliente cliente) {
	        String sql = "INSERT INTO cliente (nombre, email) VALUES (?, ?)";
	        try (PreparedStatement statement = conn.prepareStatement(sql)) {
	            statement.setString(1, cliente.getNombre());
	            statement.setString(2, cliente.getEmail());
	            statement.executeUpdate();
	            conn.commit();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void update(Cliente cliente) {
	        String sql = "UPDATE cliente SET nombre = ?, email = ? WHERE idcliente = ?";
	        try (PreparedStatement statement = conn.prepareStatement(sql)) {
	            statement.setString(1, cliente.getNombre());
	            statement.setString(2, cliente.getEmail());
	            statement.setInt(3, cliente.getIdCliente());
	            statement.executeUpdate();
	            conn.commit();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void delete(int idCliente) {
	        String sql = "DELETE FROM cliente WHERE idcliente = ?";
	        try (PreparedStatement statement = conn.prepareStatement(sql)) {
	            statement.setInt(1, idCliente);
	            statement.executeUpdate();
	            conn.commit();
	        } catch (SQLException e) {
	            e.printStackTrace();
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
				return DriverManager.getConnection(DBURL, "root", "");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return null;
		}
}
