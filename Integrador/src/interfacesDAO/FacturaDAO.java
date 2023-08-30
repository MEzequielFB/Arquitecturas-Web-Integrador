package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import entidades.Factura;

public interface FacturaDAO extends CreacionTabla {
	public Factura getBillById(int idFactura) throws SQLException;
	public List<Factura> getAll() throws SQLException;
	public void insert(Factura factura) throws SQLException;
	public void insertAll(List<Factura> facturas) throws SQLException;
}
