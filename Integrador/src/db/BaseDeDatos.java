package db;

import java.sql.SQLException;

public class BaseDeDatos {

	public static void main(String[] args) throws SQLException {
		// Punto 1: Crea el esquema (mysql)
		DAOFactory mysql_dao_factory = DAOFactory.getDAOFactory(1);
		mysql_dao_factory.createSchema();
		
		// Punto 2: Leer CSVs y cargar datos a la base de datos
		
		// Punto 3: Obtener el producto que mas cantidades vendio
		
		// Punto 4: Lista  de clientes a los que mas se les facturo
	}

}
