package factories;

import dao.MysqlClienteDAO;
import dao.MysqlFacturaDAO;
import dao.MysqlFacturaProductoDAO;
import dao.MysqlProductoDAO;

public class MysqlDAOFactory extends DAOFactory {
	private static MysqlDAOFactory instancia;
	
	private MysqlDAOFactory() {}
	
	public static MysqlDAOFactory getInstancia() {
		if (instancia == null) {
			instancia = new MysqlDAOFactory();
		}
		return instancia;
	}
	
	@Override
	public MysqlClienteDAO getClienteDAO() {
		return MysqlClienteDAO.getInstancia();
	}

	@Override
	public MysqlProductoDAO getProductoDAO() {
		return MysqlProductoDAO.getInstancia();
	}

	@Override
	public MysqlFacturaDAO getFacturaDAO() {
		return MysqlFacturaDAO.getInstancia();
	}

	@Override
	public MysqlFacturaProductoDAO getFacturaProductoDAO() {
		return MysqlFacturaProductoDAO.getInstancia();
	}
}
