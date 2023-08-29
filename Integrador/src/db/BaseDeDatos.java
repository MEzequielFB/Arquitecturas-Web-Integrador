package db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import dao.MysqlClienteDAO;
import dao.MysqlFacturaDAO;
import dao.MysqlFacturaProductoDAO;
import dao.MysqlProductoDAO;
import entidades.Cliente;
import entidades.Factura;
import entidades.FacturaProducto;
import entidades.Producto;
import factories.DAOFactory;

public class BaseDeDatos {

	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
		// Punto 1: Crea el esquema (mysql)
		
		DAOFactory mysql_dao_factory = DAOFactory.getDAOFactory(1);

		MysqlClienteDAO cliente_dao = mysql_dao_factory.getClienteDAO();
		MysqlProductoDAO producto_dao = mysql_dao_factory.getProductoDAO();

		MysqlFacturaDAO factura_dao = mysql_dao_factory.getFacturaDAO();
		MysqlFacturaProductoDAO factura_producto_dao = mysql_dao_factory.getFacturaProductoDAO();

		// Punto 2: Leer CSVs y cargar datos a la base de datos
		
		String path1 = "C:/eclipse-workspace/Clone 1/Arquitecturas-Web-Integrador/Integrador/clientes.csv";
		String path2 = "C:/eclipse-workspace/Clone 1/Arquitecturas-Web-Integrador/Integrador/productos.csv";
		String path3 = "C:/eclipse-workspace/Clone 1/Arquitecturas-Web-Integrador/Integrador/facturas.csv";
		String path4 = "C:/eclipse-workspace/Clone 1/Arquitecturas-Web-Integrador/Integrador/facturas-productos.csv";
/*
		cliente_dao.createTable();
		cliente_dao.poblateTable(path1);

		producto_dao.createTable();
		producto_dao.poblateTable(path2);

		factura_dao.createTable();
		factura_dao.poblateTable(path3);

		factura_producto_dao.createTable();
		factura_producto_dao.poblateTable(path4);
*/
		// Punto 3: Obtener el producto que mas cantidades vendio
		
		System.out.println(producto_dao.moreRaisedProduct());

		// Punto 4: Lista de clientes a los que mas se les facturo
		
		System.out.println(cliente_dao.getClientsByBill());
		
	}

}
