package Componentes;

import java.util.Scanner;
import java.util.ArrayList;

public class Universidad {
    private ArrayList<Aula> aulas;
    private ArrayList<Asignatura> asignaturas;
    private ArrayList<CursoExtension> cursosDeExtension;
    private ArrayList<Evento> eventos;

    public Universidad() {
        this.aulas = new ArrayList<>();
        this.asignaturas = new ArrayList<>();
        this.cursosDeExtension = new ArrayList<>();
        this.eventos = new ArrayList<>();
    }

    public ArrayList<Aula> getAulas() {
        return aulas;
    }
    public void setAulas(ArrayList<Aula> aulas) {
        this.aulas = aulas;
    }
    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }
    public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
    public ArrayList<CursoExtension> getCursosDeExtension() {
        return cursosDeExtension;
    }
    public void setCursosDeExtension(ArrayList<CursoExtension> cursosDeExtension) {
        this.cursosDeExtension = cursosDeExtension;
    }
    public ArrayList<Evento> getEventos() {
        return eventos;
    }
    public void setEventos(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }


    /*
     * //Metodos necesarios:
     * // Métodos para listar datos       X
     * // Método para registrar reservas
     * // Método para cancelar reservas   X
     * // Método para generar reportes
     * // Método para cargar por Archivo
     */



    //Listar los datos por piso
    void listarDatos(int piso) {
        for (Aula a : this.aulas) {
            if ( (a.getId() / 100) == piso) {
                a.muestraAula();
            }
        }
    }

    //Listar las reservas correspondientes al codigo ( asignatura / curso / evento  )
    void listarDatos(String codigoReserva) {
        for (Aula a : aulas) {
            a.muestraAula(codigoReserva);
        }
    }

    //Metodo para cancelar una reserva con aula correspondiente y su codigo de reserva
    void cancelarReserva(int aulaID, int codReserva){
        for(Aula a : this.aulas) {
            if( a.getId() == aulaID ){ //Busca el aula
                a.cancelarReserva(codReserva); // Busca y elimina la reserva
                return; // Si encuentra el aula finaliza la funcion
            }
            if ( aulaID < a.getId() ){ // El aula no existe
                System.out.println("\nNo se encontro el codigo de Aula.");
                return;
            }
        } // El aula no existe
        System.out.println("\nNo se encontro el codigo de Aula.");
    }

    public void registraReserva() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nIngrese el ID del Aula: ");
        int aula = scanner.nextInt();


        System.out.println("\nIngrese el tipo de reserva:");
        System.out.println("1. Asignatura.");
        System.out.println("2. Curso.");
        System.out.println("3. Evento.");
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1: //  Asignatura
                ;
                break;
            case 2: //  Curso
                ;
                break;
            case 3: // Evento
                ;
                break;
            default:
                System.out.println("\nOpcion no valida.");
        }
    }
}