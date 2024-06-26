package Componentes;

import java.time.*;
import java.util.ArrayList;

/**
 * @author Dante Suarez
 */

public class Aula {
  private int id; // centena indica el piso
  private int capacidadMaxima;
  private ArrayList<Reserva> reservas;

  public Aula(int id, int capacidad) {
    this.id = id;
    this.capacidadMaxima = capacidad;
    this.reservas = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public int getCapacidadMaxima() {
    return capacidadMaxima;
  }

  public ArrayList<Reserva> getReservas() {
    return reservas;
  }

  public int getCantidadReservas() {
    return reservas.size();
  }

  public void addReserva(Reserva reserva) {
    reservas.add(reserva);
  }

  /**
   * Muestra en consola todos los datos del aula, incluyendo sus reservas
   */
  public void muestraAula() {
    System.out.println();
    System.out.println("ID\tCapacidad Maxima");
    System.out.println(id + "\t" + capacidadMaxima);
    System.out.println("\nReservas:");
    for (Reserva r : reservas) {
      r.mostrarReserva();
    }
  }

  /**
   * Muestra todas las reservas del Reservante
   * @param codigoReserva codigo del reservante
   */
  public void muestraAula(String codigoReserva) {
    for (Reserva r : reservas) {
      if (codigoReserva.equals(r.getCodigoReservador())) {
        System.out.println("\nID Aula: " + id);
        System.out.println("Capacidad Maxima: " + capacidadMaxima);
        r.mostrarReserva();
      }
    }
  }

  /**
   * Cancela la reserva correspondiente al codigo Reservante
   * @param codReserva codigo del reservante
   */
  public void cancelarReserva(String codReserva) {
    int i = 0;
    for (Reserva r : this.reservas) { // Busca la reserva
      if (r.getCodigoReservador().equals(codReserva)) { // Elimina la reserva
        System.out.println("\nReserva Eliminada\n");
        System.out.println("Datos:");
        r.mostrarReserva();
        reservas.remove(i);
        return;
      }
      if (codReserva.compareTo(r.getCodigoReservador())>0) { // La reserva no existe
        System.out.println("\nNo se encontro el codigo de Reserva.");
        return;
      }
      i++;
    } // La reserva no existe
    System.out.println("\nNo se encontro el codigo de Reserva.");
  }

  /**
   * Comprueba si el aula esta disponible en la fecha y horario determinado
   * @param fecha fecha en la cual se requiere el aula
   * @param inicio hora de inicio en el cual se requiere el aula
   * @param fin hora de finalizacion en la cual se requiere el aula
   * @return true si esta disponible, false de caso contrario
   */
  public boolean estaDisponible(LocalDate fecha, LocalTime inicio, LocalTime fin) {
    for (Reserva reserva : reservas) {
      if (reserva.getFecha().equals(fecha) && // Si el dia se superpone
          ((inicio.isBefore(reserva.getFin()) && //
              inicio.isAfter(reserva.getInicio())) // Si el inicio se superpone
              || //
              (fin.isBefore(reserva.getFin()) && // Si el fin se superpone
                  fin.isAfter(reserva.getInicio())) //
              || //
              (inicio.equals(reserva.getInicio()) // //Si son el mismo horario
                  || //
                  fin.equals(reserva.getFin())))) { //
        System.out.println("\nEl aula no esta disponible en ese horario.");
        return false; // No esta disponible
      }
    }
    return true; // Esta disponible
  }

  /**
   * Calcula la recaudacion total del aula
   * @param eventos lista de los eventos
   * @param cursos lista de los cursos
   * @return recaudacion total del aula
   */
  public double recaudacionesAula(ArrayList<Evento> eventos, ArrayList<CursoExtension> cursos){
    double recaudaciones = 0;
    for(Reserva r : reservas){
      String cod = r.getCodigoReservador();
      for(Evento evento : eventos){
        if( cod.equals(evento.getCodigo()) ) {
          recaudaciones += evento.getCostoAlquiler();
        }
      }
      for(CursoExtension curso : cursos){
        if( cod.equals(curso.getCodigo())){
          recaudaciones += ( curso.getCantidadAlumnos() * curso.getCostoAlumnos());
        }
      }
    }
    return recaudaciones;
  }
}
