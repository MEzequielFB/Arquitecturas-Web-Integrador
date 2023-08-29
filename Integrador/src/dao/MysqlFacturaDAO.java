package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import entidades.Factura;
import factories.MysqlDAOFactory;

// Consultas de facturas
public class MysqlFacturaDAO implements EntityDAO {
    
    @Override
	public Connection createConnection() throws SQLException {
		return MysqlDAOFactory.createConnection();
	}

	@Override
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
    
    @Override
    public void createTable() throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
    	String create_table = "CREATE TABLE factura(" +
		"idFactura INT AUTO_INCREMENT," + // PK
		"idCliente INT," + // Puede ser null - FK
		"PRIMARY KEY(idFactura)," + 
		"FOREIGN KEY (idCliente) REFERENCES cliente(idCliente))";
    	
    	conn.prepareStatement(create_table).execute();
    	conn.commit();
    }

	public void poblateTable(String path) throws FileNotFoundException, IOException, SQLException {
		Connection conn = createConnection();
		conn.setAutoCommit(false);

		CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(
				new FileReader(path));
		for (CSVRecord row : parser) {
			String sql = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, Integer.valueOf(row.get("idFactura")));
			statement.setInt(2, Integer.valueOf(row.get("idCliente")));
			statement.executeUpdate();
			conn.commit();
			statement.close();
		}
		closeConnection(conn);
	}
    
    public Factura getBillById(int idFactura) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "SELECT idFactura, idCliente FROM factura WHERE idFactura = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idFactura);
        ResultSet resultSet = statement.executeQuery();
        Factura factura = null;
        if (resultSet.next()) {
            factura = new Factura(resultSet.getInt("idFactura"),resultSet.getInt("idCliente"));
        }
        statement.close();
        closeConnection(conn);
        return factura;
    }

    public List<Factura> getAll() throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT idFactura, idCliente FROM factura";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Factura factura = new Factura(resultSet.getInt("idFactura"),resultSet.getInt("idCliente"));
            facturas.add(factura);
        }
        statement.close();
        closeConnection(conn);
        return facturas;
    }

    public void insert(Factura factura) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "INSERT INTO factura (idCliente) VALUES (?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, factura.getIdCliente());
            statement.executeUpdate();
            conn.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
            	conn.rollback(); // En caso de error, realiza rollback para deshacer los cambios
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
        closeConnection(conn);
    }

    public void update(Factura factura) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "UPDATE factura SET idCliente = ? WHERE idFactura = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, factura.getIdCliente());
            statement.setInt(2, factura.getIdFactura());
            statement.executeUpdate();
            conn.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
            	conn.rollback(); // En caso de error, realiza rollback para deshacer los cambios
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
        closeConnection(conn);
    }

    public void delete(int idFactura) throws SQLException {
    	Connection conn = createConnection();
    	conn.setAutoCommit(false);
    	
        String sql = "DELETE FROM factura WHERE idFactura = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idFactura);
            statement.executeUpdate();
            conn.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
            	conn.rollback(); // En caso de error, realiza rollback para deshacer los cambios
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
        closeConnection(conn);
    }
}
