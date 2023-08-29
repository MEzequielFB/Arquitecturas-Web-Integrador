package dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface EntityDAO {
	public Connection createConnection() throws SQLException;
	public void closeConnection(Connection conn) throws SQLException;
	public void createTable() throws SQLException;
}
