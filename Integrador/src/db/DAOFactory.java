package db;

import java.sql.SQLException;

public abstract class DAOFactory {
	public static final int MYSQL_JDBC = 1;
	
	//Crea el esquema - PREGUNTAR SI ESTA BIEN PONERLO ACA
	public abstract void createSchema() throws SQLException;
	
	//Obtiene las entidades para realizar consultas
	public abstract MysqlClienteDAO getClienteDAO();
	public abstract MysqlProductoDAO getProductoDAO();
	public abstract MysqlFacturaDAO getFacturaDAO();
	public abstract MysqlFacturaProductoDAO getFacturaProductoDAO();
		
	//Obtiene una base de datos para obtener las entidades de esta de forma
	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
			case MYSQL_JDBC:
				return new MysqlDAOFactory();
			default:
				return null;
		}
	}
}
