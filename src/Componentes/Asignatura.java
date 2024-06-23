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

  public void muestra(){
    System.out.println("\nAsignatura ");
    System.out.println("Codigo del Asignatura " + codigo);
    System.out.println("Nombre del Asignatura " + nombreAsignatura);
    System.out.println("Cursada Inicio " + cursadaInicio);
    System.out.println("Cursada Fin " + cursadaFin);
    System.out.println("Dia Semana " + diaSemana);
    System.out.println("Hora Inicio " + horaInicio);
    System.out.println("Hora Fin " + horaFin);
    System.out.println("Cantidad Alumnos " + cantidadAlumnos);
    System.out.println();
  }
}
