package ar.edu.ungs.prog2;

public class Estadio extends Sede{
	
	Estadio(String nombre, String direccion, int capacidadMax) {
		super(nombre, direccion, capacidadMax);
	}
	
    public boolean tieneSectores() {
        return false;
    }
    
	public double calcularCosto(String sector, double precioBase) {
	    return precioBase; // Sin recargo.
	}
}