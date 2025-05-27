package ar.edu.ungs.prog2;

public class Teatro extends Sede{
	private int asientosPorFila;
	private String[] sectores;
	private int[] capacidad;
	private int[] porcentajeAdicional;

	Teatro(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMaxima);
		this.asientosPorFila = asientosPorFila;
		this.sectores = sectores;
		this.capacidad = capacidad;
		this.porcentajeAdicional = porcentajeAdicional;
	}
	
	public double calcularCosto(String sector, double precioBase) {
	    for (int i = 0; i < sectores.length; i++) {
	        if (sectores[i].equals(sector)) {
	            double adicional = precioBase * porcentajeAdicional[i] / 100.0;
	            return precioBase + adicional;
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
