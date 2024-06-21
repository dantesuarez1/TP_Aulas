package Componentes;

import java.util.ArrayList;

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

    public void addReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }

    void muestraAula() {
        System.out.println("ID\tCapacidad Maxima");
        System.out.println(id + "\t" + capacidadMaxima);
        System.out.println("\nReservas:");
        for (Reserva r : reservas) {
            r.mostrarReserva();
        }
    }

    void muestraAula(String codigoReserva) {
        for (Reserva r : reservas) {
            if ( codigoReserva.equals(r.getCodigoReservador()) ) {
                System.out.println("\nID Aula: " + id);
                System.out.println("Capacidad Maxima: " + capacidadMaxima);
                r.mostrarReserva();
            }
        }
    }

    void cancelarReserva(int codReserva){
        int i=0;
        for(Reserva r : this.reservas){ // Busca la reserva
            if( r.getCodigo() == codReserva ) { // Elimina la reserva
                System.out.println("\nReserva Eliminada\n");
                System.out.println("Datos:");
                r.mostrarReserva();
                reservas.remove(i);
                return;
            }
            if(codReserva < r.getCodigo() ) { //La reserva no existe
                System.out.println("\nNo se encontro el codigo de Reserva.");
                return;
            }
            i++;
        } // La reserva no existe
        System.out.println("\nNo se encontro el codigo de Reserva.");
    }
}
