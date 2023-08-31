package helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import entidades.Cliente;
import entidades.Factura;
import entidades.FacturaProducto;
import entidades.Producto;
import factories.DAOFactory;
import interfacesDAO.ClienteDAO;
import interfacesDAO.FacturaDAO;
import interfacesDAO.FacturaProductoDAO;
import interfacesDAO.ProductoDAO;

public class CreacionTablasHelper {
	private static String path_cliente = "src/csv/clientes.csv";
	private static String path_producto = "src/csv/productos.csv";
	private static String path_factura = "src/csv/facturas.csv";
	private static String path_factura_producto = "src/csv/facturas-productos.csv";

	public static void createSchema() throws SQLException {
		Connection conn = ConexionHelper.createConnection();
		String create_cliente = "CREATE TABLE IF NOT EXISTS cliente(" + "idCliente INT," // PK
				+ "nombre VARCHAR(500) NOT NULL," + "email VARCHAR(150) NOT NULL," + "PRIMARY KEY(idCliente))";

		String create_producto = "CREATE TABLE IF NOT EXISTS producto(" + "idProducto INT," // PK
				+ "nombre VARCHAR(45) NOT NULL," + "valor FLOAT(7,2) NOT NULL," // 10000,00
				+ "PRIMARY KEY(idProducto))";

		String create_factura = "CREATE TABLE IF NOT EXISTS factura(" + "idFactura INT," // PK
				+ "idCliente INT," // Puede ser null - FK
				+ "PRIMARY KEY(idFactura)," + "FOREIGN KEY (idCliente) REFERENCES cliente(idCliente))";

		String create_factura_producto = "CREATE TABLE IF NOT EXISTS factura_producto(" + "idFactura INT," // PK - FK
				+ "idProducto INT," // PK - FK
				+ "cantidad INT NOT NULL," + "PRIMARY KEY(idFactura, idProducto),"
				+ "FOREIGN KEY (idFactura) REFERENCES factura(idFactura),"
				+ "FOREIGN KEY (idProducto) REFERENCES producto(idProducto))";

		conn.prepareStatement(create_cliente).execute();
		conn.prepareStatement(create_producto).execute();
		conn.prepareStatement(create_factura).execute();
		conn.prepareStatement(create_factura_producto).execute();
		conn.commit();
		ConexionHelper.closeConnection(conn);
	}

	public static void poblateTables() throws SQLException, FileNotFoundException, IOException {
		Connection conn = ConexionHelper.createConnection();
		CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path_cliente));
		List<Cliente> clientes = new ArrayList<>();
		for (CSVRecord row : parser) {
			Cliente c = new Cliente(Integer.valueOf(row.get("idCliente")), row.get("nombre"), row.get("email"));
			clientes.add(c);
		}
		DAOFactory mysql_dao_factory = DAOFactory.getDAOFactory(1);
		ClienteDAO cliente_dao = mysql_dao_factory.getClienteDAO();
		cliente_dao.insertAll(clientes);
		ConexionHelper.closeConnection(conn);

		parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path_producto));
		List<Producto> productos = new ArrayList<>();
		for (CSVRecord row : parser) {
			Producto p = new Producto(Integer.valueOf(row.get("idProducto")), row.get("nombre"),
					Integer.valueOf(row.get("valor")));
			productos.add(p);
		}
		ProductoDAO producto_dao = mysql_dao_factory.getProductoDAO();
		producto_dao.insertAll(productos);

		parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path_factura));
		List<Factura> facturas = new ArrayList<>();
		for (CSVRecord row : parser) {
			Factura f = new Factura(Integer.valueOf(row.get("idFactura")), Integer.valueOf(row.get("idCliente")));
			facturas.add(f);
		}
		FacturaDAO factura_dao = mysql_dao_factory.getFacturaDAO();
		factura_dao.insertAll(facturas);

		parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path_factura_producto));
		List<FacturaProducto> facturas_productos = new ArrayList<>();
		for (CSVRecord row : parser) {
			FacturaProducto fp = new FacturaProducto(Integer.valueOf(row.get("idFactura")),
					Integer.valueOf(row.get("idProducto")), Integer.valueOf(row.get("cantidad")));
			facturas_productos.add(fp);
		}
		FacturaProductoDAO facturaproducto_dao = mysql_dao_factory.getFacturaProductoDAO();
		facturaproducto_dao.insertAll(facturas_productos);

		ConexionHelper.closeConnection(conn);
	}
}
