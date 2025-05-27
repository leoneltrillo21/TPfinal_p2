package ar.edu.ungs.prog2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Funcion {
	private Sede sede;
	private String fecha;
	private double precioBase;
	private Map<String, Entrada> entradas; // Clave: código, Valor: Entrada
	private int contadorEntradas; // Se usa para formar el código de la entrada junto a la fecha del espectáculo.
	private int entradasDisponibles;
	private double recaudacion;
	
	Funcion(Sede sede, String fecha, double precioBase) {
		this.sede = sede;
		this.fecha = fecha;
		this.precioBase = precioBase;
		this.contadorEntradas = 0;
		this.entradasDisponibles = sede.getCapacidadMaxima();
		this.entradas = new HashMap<>();
	}
	
	public Entrada venderAsiento(String nombre, String fecha, String mail) {
		if(entradasVendidas() == entradasDisponibles) {
	        throw new IllegalArgumentException("No hay entradas disponibles.");						
		}
		String codigo = contadorEntradas + fecha;
		Entrada entrada = new Entrada(codigo, nombre, fecha, sede.getNombre(), mail, precioBase);
		contadorEntradas++;
		entradasDisponibles--;
		entradas.put(codigo, entrada);
		recaudacion += precioBase;
		return entrada;
	}

	public Entrada venderAsiento(String nombre, String fecha, String sector, int asiento, String mail) {
		if(entradasVendidas() == entradasDisponibles) {
	        throw new IllegalArgumentException("No hay entradas disponibles.");						
		}
		if(vendidasPorSector(sector) >= capacidadPorSector(sector)) {
	        throw new IllegalArgumentException("No hay entradas disponibles para este sector.");									
		}
		String codigo = contadorEntradas + fecha;
		double precio = calcularPrecio(sector);
		Entrada entrada = new Entrada(codigo, nombre, fecha, sede.getNombre(), sector, asiento, mail, precio);
		contadorEntradas++;
		entradasDisponibles--;
		entradas.put(codigo, entrada);
		recaudacion += calcularPrecio(sector);
		return entrada;
	}
	
    public List<Entrada> getEntradas() {
    	List<Entrada> lista = new ArrayList<>();
    	for(Entrada e : entradas.values()) {
    		lista.add(e);
    	}
        return lista;
    }


	public int entradasVendidas() {
		return entradas.size();
	}

	public boolean anularEntrada(String codigo) {
        return entradas.remove(codigo) != null;
	}
	
	private int vendidasPorSector(String sector) {
		int vendidasPorSector = 0;
        for (Entrada e : getEntradas()) {
            String ubicacion = e.ubicacion();
            if (ubicacion == sector) {
                vendidasPorSector++;
            }
        }
        return vendidasPorSector;
	}
	
	public List<String> listarEntradas() {
		List<String> partes = new ArrayList<>();
		String[] sectores = sede.getSectores();
        
        for (int i = 0; i < sectores.length; i++) {
        	String sector = sectores[i];
        	int vendidas = vendidasPorSector(sector);
        	int capacidad = capacidadPorSector(sector);
        	partes.add(sector + ": " + vendidas + "/" + capacidad);
        }
		return partes;
	}
	
	private int capacidadPorSector(String sector) {
		String[] sectores = sede.getSectores();
        int[] capacidades = sede.getCapacidades();

		for(int i = 0; i < sectores.length; i++) {
        	String sectorAux = sectores[i];
        	if(sector == sectorAux) {
        		return capacidades[i];
        	}
		}
		return 0;
	}
    
    public double calcularPrecio(String sector) {
        return sede.calcularCosto(sector, precioBase);
    }
    
    public double calcularPrecio() {
    	return precioBase;
    }

	public double recaudacionEntradas() {
		return recaudacion;
	}

	public String getNombreSede() {
		return sede.getNombre();
	}

	public Sede getSede() {
		return sede;
	}
}
