package ar.edu.ungs.prog2;

public abstract class Sede {
	private String nombre;
	private String direccion;
	private int capacidadMax;

	Sede(String nombre, String direccion, int capacidadMax) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.capacidadMax = capacidadMax;
	}

	public abstract double calcularCosto(String sector, double precioBase);

	public String getNombre() {
		return nombre;
	}
	
    public abstract boolean tieneSectores();

    public String[] getSectores() {
        return null; 
    }

    public int[] getCapacidades() {
        return null;
    }

	public int getCapacidadMaxima() {
		return capacidadMax;
	}	
}
