package factories;

import dao.MysqlClienteDAO;
import dao.MysqlFacturaDAO;
import dao.MysqlFacturaProductoDAO;
import dao.MysqlProductoDAO;

public class MysqlDAOFactory extends DAOFactory {
	
	@Override
	public MysqlClienteDAO getClienteDAO() {
		return new MysqlClienteDAO();
	}

	@Override
	public MysqlProductoDAO getProductoDAO() {
		return new MysqlProductoDAO();
	}

	@Override
	public MysqlFacturaDAO getFacturaDAO() {
		return new MysqlFacturaDAO();
	}

	@Override
	public MysqlFacturaProductoDAO getFacturaProductoDAO() {
		return new MysqlFacturaProductoDAO();
	}
}
