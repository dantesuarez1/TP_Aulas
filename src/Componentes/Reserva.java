package Componentes;

import java.time.*;

/**
 * @author Dante Suarez
 */

public class Reserva {
  private int codigo; // Numérico único que se genera automáticamente de manera incremental
  private LocalDate fecha;
  private LocalTime horaInicio;
  private LocalTime horaFinal;
  private String codigoReservador; // Asignatura | Evento | CursoDeExtension

  public Reserva(int codigo, LocalDate fecha, LocalTime horaInicio, LocalTime horaFinal, String codigoReservador) {
    this.codigo = codigo;
    this.fecha = fecha;
    this.horaInicio = horaInicio;
    this.horaFinal = horaFinal;
    this.codigoReservador = codigoReservador;
  }

  int getCodigo() {
    return codigo;
  }

  LocalDate getFecha() {
    return fecha;
  }

  LocalTime getInicio() {
    return horaInicio;
  }

  LocalTime getFin() {
    return horaFinal;
  }

  String getCodigoReservador() {
    return codigoReservador;
  }

  /**
   * Muestra todos los campos de la reserva
   */
  void mostrarReserva() {
    System.out.println("Codigo de Reserva: " + codigo+1);
    System.out.println("Fecha: " + fecha);
    System.out.println("Hora Inicio: " + horaInicio);
    System.out.println("Hora Final " + horaFinal);
    System.out.println("Codigo de Reservador (Asignatura|Evento|CursoDeExtension): " + codigoReservador);
  }
}
