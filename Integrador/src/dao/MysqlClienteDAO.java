package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Cliente;
import factories.MysqlDAOFactory;

// Consultas de clientes
public class MysqlClienteDAO implements EntityDAO {
    
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
    	
    	String create_table = "CREATE TABLE cliente(" +
		"idCliente INT AUTO_INCREMENT," + // PK
		"nombre VARCHAR(500) NOT NULL," +
		"email VARCHAR(150) NOT NULL," +
		"PRIMARY KEY(idCliente))";
    	
    	conn.prepareStatement(create_table).execute();
    	conn.commit();
    	closeConnection(conn);
    }
    
    public Cliente getClientById(int idCliente) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "SELECT idcliente, nombre, email FROM cliente WHERE idcliente = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idCliente);
        ResultSet resultSet = statement.executeQuery();
        Cliente cliente = null;
        if (resultSet.next()) {
            cliente = new Cliente(resultSet.getInt("idcliente"),resultSet.getString("nombre"),resultSet.getString("email"));
        }
        statement.close();
        closeConnection(conn);
        return cliente;
    }

    public List<Cliente> getAllClients() throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT idcliente, nombre, email FROM cliente";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Cliente cliente = new Cliente(resultSet.getInt("idcliente"),resultSet.getString("nombre"),resultSet.getString("email"));
            clientes.add(cliente);
        }
        statement.close();
        closeConnection(conn);
        return clientes;
    }

    public void insert(Cliente cliente) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "INSERT INTO cliente (nombre, email) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, cliente.getNombre());
        statement.setString(2, cliente.getEmail());
        statement.executeUpdate();
        conn.commit();
        statement.close();
        closeConnection(conn);
    }

    public void update(Cliente cliente) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "UPDATE cliente SET nombre = ?, email = ? WHERE idcliente = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, cliente.getNombre());
        statement.setString(2, cliente.getEmail());
        statement.setInt(3, cliente.getIdCliente());
        statement.executeUpdate();
        conn.commit();
        statement.close();
        closeConnection(conn);
    }

    public void delete(int idCliente) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "DELETE FROM cliente WHERE idcliente = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idCliente);
        statement.executeUpdate();
        conn.commit();
        statement.close();
        closeConnection(conn);
    }
    
}
