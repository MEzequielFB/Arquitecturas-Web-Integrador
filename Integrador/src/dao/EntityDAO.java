package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface EntityDAO {
	public void createTable() throws SQLException;
	public void poblateTable(String path) throws FileNotFoundException, IOException, SQLException;
}
