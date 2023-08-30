package interfacesDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface CreacionTabla {
	public void createTable() throws SQLException;
	public void poblateTable(String path) throws FileNotFoundException, IOException, SQLException;
}
