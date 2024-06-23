package Componentes;

import java.time.*;

public class CursoExtension {
  private String codigo;
  private String descripcion;
  private int cantidadAlumnos;
  private LocalDate fecha;
  private int cantidadClases; // Se dicta una por semana
  private double costoAlumnos;

  public CursoExtension(String codigo, String descripcion, int cantAlumnos, LocalDate fecha, int cantClases,
      double costoAlquiler) {
    this.codigo = codigo;
    this.descripcion = descripcion;
    cantidadAlumnos = cantAlumnos;
    this.fecha = fecha;
    cantidadClases = cantClases;
    this.costoAlumnos = costoAlquiler;
  }

  public String getCodigo() {
    return codigo;
  }

  public double getCostoAlumnos() {
    return costoAlumnos;
  }

  public int getCantidadAlumnos() {
    return cantidadAlumnos;
  }

  public void muestra(){
    System.out.println("\nCurso de Extension");
    System.out.println("Codigo: " + codigo);
    System.out.println("Descripcion: " + descripcion);
    System.out.println("Cantidad Alumnos: " + cantidadAlumnos);
    System.out.println("Fecha: " + fecha);
    System.out.println("Cantidad Clases: " + cantidadClases);
    System.out.println("Costo Alumnos: " + costoAlumnos);
    System.out.println();
  }
}
