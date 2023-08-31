package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import entidades.Producto;

public interface ProductoDAO {
	public Producto getById(int idProducto) throws SQLException;
	public List<Producto> getAll() throws SQLException;
	public void insert(Producto producto) throws SQLException;
	public void insertAll(List<Producto> productos) throws SQLException;
	public Producto moreRaisedProduct() throws SQLException;
}
