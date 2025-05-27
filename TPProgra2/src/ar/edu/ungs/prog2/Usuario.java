package ar.edu.ungs.prog2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {
	private String mail;
	private String nombre;
	private String apellido;
	private String contrasena;
	private Map <String, Entrada> entradas; // Clave: código, Valor: Entrada
	
	public Usuario(String mail, String nombre, String apellido, String contrasena) {
		this.mail = mail;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasena = contrasena;
		this.entradas = new HashMap<>();
	}	
	
    public void agregarEntrada(Entrada e) {
        entradas.put(e.getCodigo(), e);
    }
    
    public boolean anularEntrada(String codigo) {
        return entradas.remove(codigo) != null;
    }

	public List<IEntrada> listarEntradas() {
	    List<IEntrada> lista = new ArrayList<>();
	    for (Entrada entrada : entradas.values()) {
	        lista.add((IEntrada) entrada);
	    }
	    return lista;
	}

	public List<IEntrada> listarEntradasFuturas() {
	    List<IEntrada> listaEntradas = new ArrayList<>();
	    for (Entrada entrada : entradas.values()) {
	        Fecha fechaEntrada = new Fecha(entrada.getFechaString());
	        if (fechaEntrada.esFutura()) {
	            listaEntradas.add((IEntrada) entrada);
	        }
	    }
	    return listaEntradas;
	}

	public String getContrasenia() {
		return contrasena;
	}
}
