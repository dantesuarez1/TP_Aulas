package Componentes;

import java.time.*;

public class CursoExtension {
  private String codigo;
  private String descripcion;
  private int cantidadAlumnos;
  private LocalDate fecha;
  private int cantidadClases; // Se dicta una por semana
  private double costoPorAlumno;

  public CursoExtension(String codigo, String descripcion, int cantAlumnos, LocalDate fecha, int cantClases,
      double costoAlumno) {
    this.codigo = codigo;
    this.descripcion = descripcion;
    cantidadAlumnos = cantAlumnos;
    this.fecha = fecha;
    cantidadClases = cantClases;
    costoPorAlumno = costoAlumno;
  }
}
