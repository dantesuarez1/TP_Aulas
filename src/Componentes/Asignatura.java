package Componentes;

import java.time.*;

public class Asignatura {
    private String codigo;
    private String nombreAsignatura;
    private LocalDate cursadaInicio;
    private LocalDate cursadaFin;
    private DayOfWeek diaSemana; // Todas las asignaturas se dictan un solo dia a la semana
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int cantidadAlumnos;

    public Asignatura(String codigo, String Nombre, LocalDate cursadaI, LocalDate cursadaF, DayOfWeek dia,
                      LocalTime horaI, LocalTime horaF, int cantAlum) {
        this.codigo = codigo;
        nombreAsignatura = Nombre;
        cursadaInicio = cursadaI;
        cursadaFin = cursadaF;
        diaSemana = dia;
        horaInicio = horaI;
        horaFin = horaF;
        cantidadAlumnos = cantAlum;
    }
}