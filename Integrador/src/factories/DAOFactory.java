package factories;

import dao.MysqlClienteDAO;
import dao.MysqlFacturaDAO;
import dao.MysqlFacturaProductoDAO;
import dao.MysqlProductoDAO;

public abstract class DAOFactory {
	public static final int MYSQL_JDBC = 1;
	
	public static DAOFactory mysql;
	
	//Obtiene las entidades para realizar consultas
	public abstract MysqlClienteDAO getClienteDAO();
	public abstract MysqlProductoDAO getProductoDAO();
	public abstract MysqlFacturaDAO getFacturaDAO();
	public abstract MysqlFacturaProductoDAO getFacturaProductoDAO();
		
	//Obtiene una base de datos para obtener las entidades de esta de forma
	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
			case MYSQL_JDBC:
				return instanciarMySqL();
			default:
				return null;
		}
	}
	
	//Garantiza SINGLETON

	public static DAOFactory instanciarMySqL() {
		if (mysql == null) {
			mysql = new MysqlDAOFactory();
			return mysql;
		}
		return mysql;
	}
}
