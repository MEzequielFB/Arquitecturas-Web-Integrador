package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import entidades.Cliente;

public interface ClienteDAO extends CreacionTabla {
	public Cliente getClientById(int idCliente) throws SQLException;
	public List<Cliente> getAllClients() throws SQLException;
	public void insert(String nombre, String email) throws SQLException;
	public List<Cliente> getClientsByBill() throws SQLException;
}
