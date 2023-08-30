package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import entidades.FacturaProducto;

public interface FacturaProductoDAO extends CreacionTabla {
	public FacturaProducto getBillById(int idFactura) throws SQLException;
	public List<FacturaProducto> getAll() throws SQLException;
	public void insert(int idFactura, int idProducto, int cantidad) throws SQLException;
}
