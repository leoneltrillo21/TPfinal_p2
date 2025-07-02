package ar.edu.ungs.prog2;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Ticketek implements ITicketek {
	private Map <String, Usuario> usuarios; // Clave: email, Valor: Usuario.
	private Map <String, Espectaculo> espectaculos; // Clave: nombre, Valor: Espectaculo.
	private Map <String, Sede> sedes; // Clave: nombre, Valor: Sede.
	
    Ticketek() { // Constructor.
        this.usuarios = new HashMap<>();
        this.espectaculos = new HashMap<>();
        this.sedes = new HashMap<>();
    }
	
// Método toString()
    public String toString() {
        return "Sedes: " + this.sedes.size() + "\n" +
               "Usuarios: " + this.usuarios.size() + "\n" +
               "Espectáculos: " + this.espectaculos.size();
    }
	
// SEDES.   
    // Registro de estadios.
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
	    if (buscarSede(nombre) != null) {
	        throw new IllegalArgumentException("La sede ya está registrada.");
	    }
		Estadio estadio = new Estadio(nombre, direccion, capacidadMaxima);
		agregarSede(nombre, estadio);
	}

	// Registro de teatros.
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
	    if (buscarSede(nombre) != null) {
	        throw new IllegalArgumentException("La sede ya está registrada.");
	    }
	    Teatro teatro = new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
		agregarSede(nombre, teatro);
	}

	// Registro de miniestadios.
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
	    if (buscarSede(nombre) != null) {
	        throw new IllegalArgumentException("La sede ya está registrada.");
	    }
	    Miniestadio miniestadio = new Miniestadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional);
		agregarSede(nombre, miniestadio);
	}
	
	private void agregarSede(String nombre, Sede sede) {
		sedes.put(nombre, sede);
	}
	
	private Sede buscarSede(String nombre) {
		return sedes.get(nombre);
	}
	
// USUARIOS.

	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
	    if (buscarUsuario(email) != null) {
	        throw new IllegalArgumentException("El usuario con este email ya está registrado.");
	    }
		Usuario usuario = new Usuario(email, nombre, apellido, contrasenia);
		agregarUsuario(email, usuario);
	}

	private void agregarUsuario(String email, Usuario usuario) {
		usuarios.put(email, usuario);		
	}
	
	private Usuario buscarUsuario(String nombre) {
		return usuarios.get(nombre);
	}
	
	private void verificarContrasenia(String email, String contrasenia) {
		String contra = buscarUsuario(email).getContrasenia();
		if(!contra.equals(contrasenia)) {
	        throw new RuntimeException("La contraseña no es válida.");						
		}
	}
	
	private void autenticarUsuario(String email, String contrasenia) {
		Usuario usuario = buscarUsuario(email);
		validarUsuarioRegistrado(usuario);
		verificarContrasenia(email, contrasenia);
	}
	
	private void validarUsuarioRegistrado(Usuario usuario) {
	    if (usuario == null) {
	        throw new RuntimeException("El usuario no está registrado.");
	    }
	}
	
// ESPECTÁCULOS.
	
	public void registrarEspectaculo(String nombre) {
	    if (buscarEspectaculo(nombre) != null) {
	        throw new IllegalArgumentException("El espectaculo ya está registrado.");
	    }  
		Espectaculo espectaculo = new Espectaculo(nombre);
		agregarEspectaculo(nombre, espectaculo);
	}

	private void agregarEspectaculo(String nombre, Espectaculo espectaculo) {
		espectaculos.put(nombre, espectaculo);		
	}
	
	public void agregarFuncion(String nombreEspectaculo, String fecha, String nombreSede, double precioBase) {
		Espectaculo espectaculo = buscarEspectaculo(nombreEspectaculo);
		validarEspectaculoRegistrado(espectaculo);
		Sede sede = buscarSede(nombreSede);
		if(sede == null) {
	        throw new IllegalArgumentException("La sede no está registrada.");			
		}	
		espectaculo.agregarFuncion(fecha, sede, precioBase);
	}

	private Espectaculo buscarEspectaculo(String nombre) {
		return espectaculos.get(nombre);
	}
	
	private void validarEspectaculoRegistrado(Espectaculo espectaculo) {
	    if (espectaculo == null) {
	        throw new IllegalArgumentException("El espectáculo no está registrado.");
	    }
	}	

// Venta de entradas:
	private void verificarFechaPasada(Fecha fecha) {
	    if(fecha.esPasada()) {
	        throw new IllegalArgumentException("La entrada está en el pasado.");
	    }
	}
	// Venta de entradas para un espectáculo con sede como estadio.
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			int cantidadEntradas) {
		List<IEntrada> entradas = new ArrayList<>();
		autenticarUsuario(email, contrasenia);
	    Espectaculo espectaculo = buscarEspectaculo(nombreEspectaculo);
		validarEspectaculoRegistrado(espectaculo);	    
	    Funcion funcion = espectaculo.buscarFuncion(fecha);
	    espectaculo.validarFuncionRegistrada(fecha);
	    if (funcion.getSede().tieneSectores()) {
	        throw new IllegalArgumentException("La sede de la función es numerada. Solo se permiten sedes NO numeradas.");
	    }
		for(int i = 0; i < cantidadEntradas; i++) {
			Entrada entrada = espectaculo.venderEntrada(fecha, email);
			agregarEntradaAUsuario(buscarUsuario(email), entrada);
			entradas.add(entrada);
		}
		return entradas;
	}

	// Venta de entradas para un espectáculo con sede como teatro o miniestadio. 
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			String sector, int[] asientos) {
		List<IEntrada> entradasNueva = new ArrayList<>();	
		autenticarUsuario(email, contrasenia);
	    Espectaculo espectaculo = buscarEspectaculo(nombreEspectaculo);
		validarEspectaculoRegistrado(espectaculo);
	    Funcion funcion = espectaculo.buscarFuncion(fecha);
	    espectaculo.validarFuncionRegistrada(fecha);
	    if (!funcion.getSede().tieneSectores()) {
	        throw new IllegalArgumentException("La sede de la función no es numerada. Solo se permiten sedes numeradas.");
	    }
		for(int asiento : asientos) {
			Entrada entrada = espectaculo.venderEntrada(fecha, sector, asiento, email);
			agregarEntradaAUsuario(buscarUsuario(email), entrada);
			entradasNueva.add(entrada);
		}
		return entradasNueva;
	}
	
	private void agregarEntradaAUsuario(Usuario usuario, Entrada entrada) {
		usuario.agregarEntrada(entrada);
	}
	
	public String listarFunciones(String nombreEspectaculo) {
	    Espectaculo espectaculo = buscarEspectaculo(nombreEspectaculo);
		validarEspectaculoRegistrado(espectaculo);
	    StringBuilder resultado = new StringBuilder(); //Ejemplo de StringBuilder.
	    for (Map.Entry<String, Funcion> entradaFuncion : espectaculo.getFunciones().entrySet()) {
	        String fecha = entradaFuncion.getKey();
	        Funcion funcion = entradaFuncion.getValue();
	        Sede sede = funcion.getSede();
	        resultado.append(" - (").append(fecha).append(") ").append(sede.getNombre()).append(" - ");
	        if (!sede.tieneSectores()) {
	        	// Es estadio.
	            int entradasVendidas = funcion.entradasVendidas();
	            resultado.append(entradasVendidas).append("/").append(sede.getCapacidadMaxima());
	        } else {
	        	// Es teatro o miniestadio.    
	            List<String> partes = funcion.listarEntradas();
	            resultado.append(String.join(" | ", partes)); // "String.join" une cada String del arreglo con " | ".
	        }
	        resultado.append("\n");
	    }
	    return resultado.toString();
	}

	
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
	    Espectaculo espectaculo = buscarEspectaculo(nombreEspectaculo);
		validarEspectaculoRegistrado(espectaculo);
		List<IEntrada> nuevaLista = espectaculo.listarEntradas();
	    return nuevaLista;
	}


	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		autenticarUsuario(email, contrasenia);
		Usuario usuario = buscarUsuario(email);
		List<IEntrada> futuras = usuario.listarEntradasFuturas();
		return futuras;
	}

	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
		autenticarUsuario(email, contrasenia);
		Usuario usuario = buscarUsuario(email);
		List<IEntrada> entradas = usuario.listarEntradas();
		return entradas;
	}

	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		if(entrada == null) throw new IllegalArgumentException("La entrada no existe.");
	        Entrada ent = (Entrada) entrada;
		autenticarUsuario(ent.getEmail(), contrasenia); //Verifica que la entrada pertenezca al usuario.
		verificarFechaPasada(ent.getFecha()); //Permite anular una entrada siempre que la fecha de la función no haya pasado.
	        Espectaculo espectaculo = buscarEspectaculo(ent.getEspectaculo());
	        Usuario usuario = buscarUsuario(ent.getEmail());
		//Se anula la entrada en Usuario y se delega al espectáculo correspondiente lo mismo, si alguno de los dos casos no es posible se lanza una excepción.
		if(!espectaculo.anularEntrada(ent.getCodigo(), ent.getFechaString()) || !usuario.anularEntrada(ent.getCodigo())) {
	            throw new IllegalArgumentException("La entrada ya fue anulada o no existe.");			
		}
		return true;
	}

	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento) {
		if(entrada == null) {
	        throw new IllegalArgumentException("La entrada no existe.");
		}
		Entrada ent = (Entrada) entrada;	
		String email = ent.getEmail();
		autenticarUsuario(email, contrasenia);
		verificarFechaPasada(ent.getFecha());
		List<IEntrada> entradaNueva = venderEntrada(ent.getEspectaculo(), fecha, email, contrasenia, sector, new int[] {asiento});
		if(entradaNueva.isEmpty()) {
	        throw new IllegalArgumentException("No fue posible cambiar la entrada.");			
		}
		anularEntrada(entrada, contrasenia);
		return entradaNueva.getFirst();
	}

	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha) {
		if(entrada == null) {
	        throw new IllegalArgumentException("La entrada no existe.");
		}
		Entrada ent = (Entrada) entrada;	
		String email = ent.getEmail();
		autenticarUsuario(email, contrasenia);
		verificarFechaPasada(ent.getFecha());
		List<IEntrada> entradaNueva = venderEntrada(ent.getEspectaculo(), fecha, email, contrasenia, 1);
		if(entradaNueva.isEmpty()) {
	        throw new IllegalArgumentException("No fue posible cambiar la entrada.");			
		}
		anularEntrada(entrada, contrasenia);
		return entradaNueva.getFirst();
	}

	public double costoEntrada(String nombreEspectaculo, String fecha) {
		Espectaculo espec = buscarEspectaculo(nombreEspectaculo);
		return espec.calcularPrecio(fecha);
	}


	public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
		Espectaculo espec = buscarEspectaculo(nombreEspectaculo);
		return espec.calcularPrecio(fecha, sector);
	}


	public double totalRecaudado(String nombreEspectaculo) {
		Espectaculo espec = buscarEspectaculo(nombreEspectaculo);
		return espec.recaudacionTotal();
	}


	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		Espectaculo espec = buscarEspectaculo(nombreEspectaculo);
		return espec.recaudacionPorSede(nombreSede);
	}
}
