package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dto.DTOClienteMayorFacturacion;
import entidades.Cliente;
import helpers.ConexionHelper;
import interfacesDAO.ClienteDAO;

// Consultas de clientes
public class MysqlClienteDAO implements ClienteDAO {
	private static MysqlClienteDAO instancia;
	
	private MysqlClienteDAO(){}
	
	public static MysqlClienteDAO getInstancia() {
		if (instancia == null) {
			instancia = new MysqlClienteDAO();
		}
		return instancia;
	}

	public Cliente getClientById(int idCliente) throws SQLException {
		Connection conn = ConexionHelper.createConnection();
		String sql = "SELECT idcliente, nombre, email FROM cliente WHERE idcliente = ?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, idCliente);
		ResultSet resultSet = statement.executeQuery();
		Cliente cliente = null;
		if (resultSet.next()) {
			cliente = new Cliente(resultSet.getInt("idcliente"), resultSet.getString("nombre"),
					resultSet.getString("email"));
		}
		statement.close();
		ConexionHelper.closeConnection(conn);
		return cliente;
	}

	public List<Cliente> getAllClients() throws SQLException {
		Connection conn = ConexionHelper.createConnection();
		List<Cliente> clientes = new ArrayList<>();
		String sql = "SELECT idcliente, nombre, email FROM cliente";
		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Cliente cliente = new Cliente(resultSet.getInt("idcliente"), resultSet.getString("nombre"),
					resultSet.getString("email"));
			clientes.add(cliente);
		}
		statement.close();
		ConexionHelper.closeConnection(conn);
		return clientes;
	}

	public void insert(Cliente cliente) throws SQLException {
		Connection conn = ConexionHelper.createConnection();
		String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, cliente.getIdCliente());
		statement.setString(2, cliente.getNombre());
		statement.setString(3, cliente.getEmail());
		statement.executeUpdate();
		conn.commit();
		statement.close();
		ConexionHelper.closeConnection(conn);
	}
	
	public void insertAll(List<Cliente> clientes) throws SQLException {
		Connection conn = ConexionHelper.createConnection();
		String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		for (Cliente c : clientes) {
			statement.setInt(1, c.getIdCliente());
			statement.setString(2, c.getNombre());
			statement.setString(3, c.getEmail());
			
			statement.addBatch();
		}
		statement.executeBatch();
		conn.commit();
		statement.close();
		ConexionHelper.closeConnection(conn);
	}
	
	/*public List<Cliente> getClientsByBill() throws SQLException {
		Connection conn = ConexionHelper.createConnection();
		List<Cliente> clientes = new ArrayList<>();
		String sql = "SELECT c.idCliente, c.nombre, c.email, SUM(fp.cantidad * p.valor) AS facturacion "
				+ "FROM cliente c "
				+ "LEFT JOIN factura f "
				+ "	ON c.idCliente = f.idCliente "
				+ "JOIN factura_producto fp "
				+ "	ON f.idFactura = fp.idFactura "
				+ "JOIN producto p "
				+ "	ON fp.idProducto = p.idProducto "
				+ "GROUP BY c.idCliente, c.nombre, c.email "
				+ "ORDER BY facturacion DESC";
		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Cliente cliente = new Cliente(resultSet.getInt("idcliente"), resultSet.getString("nombre"),
					resultSet.getString("email"));
			clientes.add(cliente);
		}
		statement.close();
		ConexionHelper.closeConnection(conn);
		return clientes;
	}*/
	
	public List<DTOClienteMayorFacturacion> getClientsByBill() throws SQLException {
	    Connection conn = ConexionHelper.createConnection();
	    List<DTOClienteMayorFacturacion> clientes = new ArrayList<>();
	    String sql = "SELECT c.idCliente, c.nombre, c.email, SUM(fp.cantidad * p.valor) AS facturacion "
	            + "FROM cliente c "
	            + "LEFT JOIN factura f "
	            + "    ON c.idCliente = f.idCliente "
	            + "JOIN factura_producto fp "
	            + "    ON f.idFactura = fp.idFactura "
	            + "JOIN producto p "
	            + "    ON fp.idProducto = p.idProducto "
	            + "GROUP BY c.idCliente, c.nombre, c.email "
	            + "ORDER BY facturacion DESC";
	    PreparedStatement statement = conn.prepareStatement(sql);
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
	        int idCliente = resultSet.getInt("idCliente");
	        String nombre = resultSet.getString("nombre");
	        String email = resultSet.getString("email");
	        double facturacion = resultSet.getDouble("facturacion");
	        DTOClienteMayorFacturacion clienteDTO = new DTOClienteMayorFacturacion(idCliente, nombre, email, facturacion);
	        clientes.add(clienteDTO);
	    }
	    statement.close();
	    ConexionHelper.closeConnection(conn);
	    return clientes;
	}

}
