package ar.edu.ungs.prog2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Espectaculo {
	private String nombre;
	private Map <String, Funcion> funciones; // Clave: fecha, valor: Funcion.
	private Map<String, Double> recaudacionPorSede; // Clave: nombre de la sede, valor: recaudación.

	
	Espectaculo(String nombre) {
		this.nombre = nombre;
		funciones = new HashMap<>();
		recaudacionPorSede = new HashMap<>();
	}
	
	void agregarFuncion(String fecha, Sede sede, double precioBase) {
	    if (funciones.containsKey(fecha)){
	        throw new RuntimeException("Ya existe una función con esa fecha.");
	    }

	    Funcion nueva = new Funcion(sede, fecha, precioBase);
	    funciones.put(fecha, nueva);
	}
	
	Funcion buscarFuncion(String fecha) {
		return funciones.get(fecha);
	}
	
	public Map<String, Funcion> getFunciones() {
	    return funciones;
	}
	
	public void validarFuncionRegistrada(String fecha) {
	    Funcion funcion = buscarFuncion(fecha);
	    if (funcion == null) {
	        throw new IllegalArgumentException("No hay función en la fecha especificada.");
	    }
	}
	
	public Entrada venderEntrada(String fecha, String mail) {
		Funcion func = funciones.get(fecha);
		Entrada entrada = func.venderAsiento(nombre, fecha, mail);
		actualizarRecaudacion(func);
		return entrada;
	}

	public Entrada venderEntrada(String fecha, String sector, int asiento, String mail) {
		Funcion func = funciones.get(fecha);
		Entrada entrada = func.venderAsiento(nombre, fecha, sector, asiento, mail);
		actualizarRecaudacion(func);
		return entrada;
	}
	
	public boolean anularEntrada(String codigo, String fecha) {
		Funcion funcion = buscarFuncion(fecha);
		return funcion.anularEntrada(codigo);
	}

	public double calcularPrecio(String fecha, String sector) {
		Funcion funcion = buscarFuncion(fecha);
		return funcion.calcularPrecio(sector);
	}

	public double calcularPrecio(String fecha) {
		Funcion funcion = buscarFuncion(fecha);
		return funcion.calcularPrecio();
	}

	public List<IEntrada> listarEntradas() {
		List<IEntrada> lista = new ArrayList<>();
	    for (Funcion funcion : funciones.values()) {
	    	for (Entrada entrada : funcion.getEntradas()) {
	    		lista.add(entrada);
	    	}
	    }
	    return lista;
	}

	private void actualizarRecaudacion(Funcion f) {
		String sede = f.getNombreSede();
		double total = 0;
		for (Funcion func : funciones.values()) {
			if (func.getNombreSede().equals(sede)) {
				total += func.recaudacionEntradas();
			}
		}
		recaudacionPorSede.put(sede, total);
	}

	public double recaudacionTotal() {
		double resultado = 0;
		for (Funcion f : funciones.values()) {
			resultado += f.recaudacionEntradas();
		}
		return resultado;
	}

	public double recaudacionPorSede(String sede) {
	    if (recaudacionPorSede.containsKey(sede)) {
	        return recaudacionPorSede.get(sede);
	    } else {
	        return 0;
	    }
	}
}
