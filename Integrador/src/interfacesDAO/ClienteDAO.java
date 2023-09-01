package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import dto.DTOClienteMayorFacturacion;
import entidades.Cliente;

public interface ClienteDAO {
	public Cliente getClientById(int idCliente) throws SQLException;
	public List<Cliente> getAllClients() throws SQLException;
	public void insert(Cliente cliente) throws SQLException;
	public void insertAll(List<Cliente> clientes) throws SQLException;
	public List<DTOClienteMayorFacturacion> getClientsByBill() throws SQLException;
}
