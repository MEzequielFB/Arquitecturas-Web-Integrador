package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import entidades.FacturaProducto;

public interface FacturaProductoDAO {
	public FacturaProducto getBillById(int idFactura) throws SQLException;
	public List<FacturaProducto> getAll() throws SQLException;
	public void insert(FacturaProducto factura) throws SQLException;
	public void insertAll(List<FacturaProducto> facturas_productos) throws SQLException;
}
