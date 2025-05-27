package ar.edu.ungs.prog2;

public class Entrada implements IEntrada{
	private String codigo;
	private String nombreEspectaculo;
	private String email;
	private String fecha;
	private String sede;
	private String sector;
	private int asiento;
	private double precio;

	Entrada(String codigo, String nombreEspectaculo, String fecha, String sede, String mail, double precio) {
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.fecha = fecha;
		this.sede = sede;
		this.email = mail;
		this.precio = precio;
		this.sector = "CAMPO";
	}
	
	Entrada(String codigo, String nombreEspectaculo, String fecha, String sede, String sector, int asiento, String mail, double precio) {
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.fecha = fecha;
		this.sede = sede;
		this.email = mail;
		this.precio = precio;
		this.sector = sector;
		this.asiento = asiento;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public double precio() {
		return precio;
	}

	public String ubicacion() {
		return sector;
	}

	public String toString() {
	    StringBuilder resultado = new StringBuilder();
	    Fecha fechaEntrada = new Fecha(fecha);
	    
	    resultado.append("- ").append(nombreEspectaculo).append(" - ").append(fecha);
	    
	    if (fechaEntrada.esPasada()) {
	        resultado.append(" P");
	    }

	    resultado.append(" - ").append(sede).append(" - ").append(sector);

	    return resultado.toString();
	}
	
	public String getEmail() {
		return email;
	}

	public String getFechaString() {
		return fecha;
	}
	
	public Fecha getFecha() {
		Fecha f = new Fecha(fecha);
		return f;
	}
	
	public String getEspectaculo() {
		return nombreEspectaculo;
	}
	
}