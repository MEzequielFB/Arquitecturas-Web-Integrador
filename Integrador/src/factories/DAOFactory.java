package factories;

import dao.MysqlClienteDAO;
import dao.MysqlFacturaDAO;
import dao.MysqlFacturaProductoDAO;
import dao.MysqlProductoDAO;
import interfacesDAO.ClienteDAO;
import interfacesDAO.FacturaDAO;
import interfacesDAO.FacturaProductoDAO;
import interfacesDAO.ProductoDAO;

public abstract class DAOFactory {
	public static final int MYSQL_JDBC = 1;
	
	private static DAOFactory mysql;
	
	//Obtiene las entidades para realizar consultas
	public abstract ClienteDAO getClienteDAO();
	public abstract ProductoDAO getProductoDAO();
	public abstract FacturaDAO getFacturaDAO();
	public abstract FacturaProductoDAO getFacturaProductoDAO();
		
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
