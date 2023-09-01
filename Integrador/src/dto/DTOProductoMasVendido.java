package dto;

public class DTOProductoMasVendido {
	private int idProducto;
    private String nombre;
    private double valor;
    private double recaudacion;

    public DTOProductoMasVendido(int idProducto, String nombre, double valor, double recaudacion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.valor = valor;
        this.recaudacion = recaudacion;
    }

    

    public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public double getValor() {
		return valor;
	}



	public void setValor(double valor) {
		this.valor = valor;
	}



	public double getRecaudacion() {
		return recaudacion;
	}



	public void setRecaudacion(double recaudacion) {
		this.recaudacion = recaudacion;
	}



	public int getIdProducto() {
		return idProducto;
	}



	@Override
    public String toString() {
        return "ProductoRecaudacionDTO{" +
                "idProducto=" + idProducto +
                ", nombreProducto='" + nombre + '\'' +
                ", valor=" + valor +
                ", recaudacion=" + recaudacion +
                '}';
    }
}
