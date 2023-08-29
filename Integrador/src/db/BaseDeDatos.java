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
	//	MysqlProductoDAO producto_dao = mysql_dao_factory.getProductoDAO();
	
	//	MysqlFacturaDAO factura_dao = mysql_dao_factory.getFacturaDAO();
	//	MysqlFacturaProductoDAO factura_producto_dao = mysql_dao_factory.getFacturaProductoDAO();
		
	//	cliente_dao.createTable();
		
		cliente_dao.poblateTable();
		
		// Punto 2: Leer CSVs y cargar datos a la base de datos
		
		// Punto 3: Obtener el producto que mas cantidades vendio
		
		// Punto 4: Lista  de clientes a los que mas se les facturo
	}

}
