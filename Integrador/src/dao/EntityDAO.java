package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface EntityDAO {
	public Connection createConnection() throws SQLException;
	public void closeConnection(Connection conn) throws SQLException;
	public void createTable() throws SQLException;
	public void poblateTable(String path) throws FileNotFoundException, IOException, SQLException;
}
