package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import entidades.Producto;

public interface ProductoDAO extends CreacionTabla {
	public Producto getById(int idProducto) throws SQLException;
	public List<Producto> getAll() throws SQLException;
	public void insert(String nombre, double valor) throws SQLException;
	public Producto moreRaisedProduct() throws SQLException;
}
