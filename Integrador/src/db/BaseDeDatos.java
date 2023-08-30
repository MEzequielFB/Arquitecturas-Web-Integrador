package db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.MysqlClienteDAO;
import dao.MysqlFacturaDAO;
import dao.MysqlFacturaProductoDAO;
import dao.MysqlProductoDAO;
import entidades.Cliente;
import entidades.Factura;
import entidades.FacturaProducto;
import entidades.Producto;
import factories.DAOFactory;
import interfacesDAO.ClienteDAO;
import interfacesDAO.FacturaDAO;
import interfacesDAO.FacturaProductoDAO;
import interfacesDAO.ProductoDAO;

public class BaseDeDatos {

	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
		// Punto 1: Crea el esquema (mysql)

		DAOFactory mysql_dao_factory = DAOFactory.getDAOFactory(1);

		ClienteDAO cliente_dao = mysql_dao_factory.getClienteDAO();
		ProductoDAO producto_dao = mysql_dao_factory.getProductoDAO();

		FacturaDAO factura_dao = mysql_dao_factory.getFacturaDAO();
		FacturaProductoDAO factura_producto_dao = mysql_dao_factory.getFacturaProductoDAO();

		// Punto 2: Leer CSVs y cargar datos a la base de datos

		String path1 = "src/csv/clientes.csv";
		String path2 = "src/csv/productos.csv";
		String path3 = "src/csv/facturas.csv";
		String path4 = "src/csv/facturas-productos.csv";

		cliente_dao.createTable();
		producto_dao.createTable();
		factura_dao.createTable();
		factura_producto_dao.createTable();

		//cliente_dao.poblateTable(path1);
		//producto_dao.poblateTable(path2);
		//factura_dao.poblateTable(path3);
		//factura_producto_dao.poblateTable(path4);

		// Punto 3: Obtener el producto que mas cantidades vendio

		System.out.println(producto_dao.moreRaisedProduct() + "\n");

		// Punto 4: Lista de clientes a los que mas se les facturo

		List<Cliente> clientsByBill = cliente_dao.getClientsByBill();
		for (Cliente c : clientsByBill) {
			System.out.println(c);
		}

	}

}
