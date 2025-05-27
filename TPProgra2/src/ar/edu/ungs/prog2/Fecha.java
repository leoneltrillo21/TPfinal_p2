package ar.edu.ungs.prog2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Fecha {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yy");
    private LocalDate fecha;

    // Constructor.
    public Fecha(String fechaComoString) {
        try {
            this.fecha = LocalDate.parse(fechaComoString, FORMATO);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido: " + fechaComoString);
        }
    }
    
    // Verifica si la fecha es pasada.
    public boolean esPasada() {
        return fecha.isBefore(LocalDate.now());
    }

    // Verifica si la fecha es futura (o hoy).
    public boolean esFutura() {
        return !esPasada();
    }

}