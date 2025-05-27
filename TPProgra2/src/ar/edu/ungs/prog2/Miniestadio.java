package ar.edu.ungs.prog2;

public class Miniestadio extends Sede{
	private int asientosPorFila;
	private int cantidadPuestos;
	private double precioConsumicion;
	private String[] sectores;
	private int[] capacidad;
	private int[] porcentajeAdicional;
	
	Miniestadio(String nombre, String direccion, int capacidadMax, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMax);
		this.asientosPorFila = asientosPorFila;
		this.cantidadPuestos = cantidadPuestos;
		this.precioConsumicion = precioConsumicion;
		this.sectores = sectores;
		this.capacidad = capacidad;
		this.porcentajeAdicional = porcentajeAdicional;
		}

	public double calcularCosto(String sector, double precioBase) {
	    for (int i = 0; i < sectores.length; i++) {
	        if (sectores[i].equalsIgnoreCase(sector)) {
	            double adicional = precioBase * porcentajeAdicional[i] / 100.0;
	            return precioBase + adicional + precioConsumicion;
	        }
	    }
	    throw new IllegalArgumentException("Sector no válido: " + sector);
	}
	
    public boolean tieneSectores() {
        return true;
    }

    public String[] getSectores() {
        return sectores;
    }

    public int[] getCapacidades() {
        return capacidad;
    }
}
