package db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dto.DTOClienteMayorFacturacion;
import entidades.Cliente;
import factories.DAOFactory;
import helpers.CreacionTablasHelper;
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
		
		CreacionTablasHelper.createSchema();
		CreacionTablasHelper.poblateTables();

		// Punto 3: Obtener el producto que mas cantidades vendio

		System.out.println(producto_dao.moreRaisedProduct() + "\n");

		// Punto 4: Lista de clientes a los que mas se les facturo

		List<DTOClienteMayorFacturacion> clientsByBill = cliente_dao.getClientsByBill();
		for (DTOClienteMayorFacturacion c : clientsByBill) {
			System.out.println(c);
		}

	}

}
