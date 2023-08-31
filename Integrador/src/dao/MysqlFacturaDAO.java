package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Factura;
import helpers.ConexionHelper;
import interfacesDAO.FacturaDAO;

// Consultas de facturas
public class MysqlFacturaDAO implements FacturaDAO {
	private static MysqlFacturaDAO instancia;
	
	private MysqlFacturaDAO(){}
	
	public static MysqlFacturaDAO getInstancia() {
		if (instancia == null) {
			instancia = new MysqlFacturaDAO();
		}
		return instancia;
	}
    
    public Factura getBillById(int idFactura) throws SQLException {
    	Connection conn = ConexionHelper.createConnection();
        String sql = "SELECT idFactura, idCliente FROM factura WHERE idFactura = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idFactura);
        ResultSet resultSet = statement.executeQuery();
        Factura factura = null;
        if (resultSet.next()) {
            factura = new Factura(resultSet.getInt("idFactura"),resultSet.getInt("idCliente"));
        }
        statement.close();
        ConexionHelper.closeConnection(conn);
        return factura;
    }

    public List<Factura> getAll() throws SQLException {
    	Connection conn = ConexionHelper.createConnection();
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT idFactura, idCliente FROM factura";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Factura factura = new Factura(resultSet.getInt("idFactura"),resultSet.getInt("idCliente"));
            facturas.add(factura);
        }
        statement.close();
        ConexionHelper.closeConnection(conn);
        return facturas;
    }

    public void insert(Factura factura) throws SQLException {
    	Connection conn = ConexionHelper.createConnection();
        String sql = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
        	statement.setInt(1, factura.getIdFactura());
            statement.setInt(2, factura.getIdCliente());
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
        ConexionHelper.closeConnection(conn);
    }
    
    public void insertAll(List<Factura> facturas) throws SQLException {
		Connection conn = ConexionHelper.createConnection();
		String sql = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		for (Factura f : facturas) {
			statement.setInt(1, f.getIdFactura());
			statement.setInt(2, f.getIdCliente());
			
			statement.addBatch();
		}
		statement.executeBatch();
		conn.commit();
		statement.close();
		ConexionHelper.closeConnection(conn);
	}

}
