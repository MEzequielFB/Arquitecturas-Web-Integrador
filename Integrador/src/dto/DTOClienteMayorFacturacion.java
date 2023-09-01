package dto;

public class DTOClienteMayorFacturacion {
	private int idCliente;
    private String nombre;
    private String email;
    private double facturacion;

    public DTOClienteMayorFacturacion(int idCliente, String nombre, String email, double facturacion) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.email = email;
        this.facturacion = facturacion;
    }

    

    public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public double getFacturacion() {
		return facturacion;
	}



	public void setFacturacion(double facturacion) {
		this.facturacion = facturacion;
	}



	public int getIdCliente() {
		return idCliente;
	}



	@Override
    public String toString() {
        return "ClienteFacturacionDTO{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", facturacion=" + facturacion +
                '}';
    }
}
