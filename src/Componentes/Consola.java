package Componentes;

import java.util.Locale;
import java.util.Scanner;

public class Consola {
  private Universidad universidad;

  public Consola(Universidad universidad) {
    this.universidad = universidad;
  }

  public void mostrarMenu() {
    Scanner sc = new Scanner(System.in);
    sc.useLocale(Locale.US);
    String opcion="0";
    while (!opcion.equals("6") ) {
      System.out.println();
      System.out.println("1. Registrar reserva");
      System.out.println("2. Cancelar reserva");
      System.out.println("3. Listar aulas");
      System.out.println("4. Generar reportes");
      System.out.println("5. Cargar desde Archivo JSON");
      System.out.println("6. Salir");

      try{
        opcion = sc.nextLine();
        switch (opcion) {
          case "1":
            universidad.registraReserva(sc);
            break;
          case "2":
            System.out.println("Ingrese número de aula:");
            int numeroAula = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese código de reserva (Alfanumerico):");
            String codigoReserva = sc.nextLine();
            universidad.cancelarReserva(numeroAula, codigoReserva);
            break;
          case "3":
            listarDatos();
            break;
          case "4":
            menuReportes(sc);
            break;
          case "5":
            try {
              System.out.println("Ingrese la direccion del archivo:");
              String direccion = sc.nextLine();
              universidad.cargarDatosDesdeJson(direccion, sc);
            } catch (Exception e) {
                System.out.println("\nError al cargar el archivo\n");
                System.out.println(e.getMessage());
            }
            break;
          case "6":
            System.out.println("Saliendo...");
            break;
          default:
            System.out.println("\nOpción no válida");
        }
      }catch(Exception e) {
        System.out.println(e.getMessage());
      }
    }
    sc.close();
  }

  public void menuReportes(Scanner scanner){
    System.out.println("\n\nGenerar Reportes");
    System.out.println("1. Reporte Recaudaciones");
    System.out.println("2. Reporte Aulas con Reservas Descendentes");
    int opcion = scanner.nextInt();
    scanner.nextLine();
    switch (opcion) {
      case 1:
        universidad.recaudaciones();
        break;
      case 2:
        universidad.resevasDescendente();
        break;
      default:
        System.out.println("Opcion no valida");
    }
  }

  public void listarDatos(){
    Scanner scanner = new Scanner(System.in);
    System.out.println("Ingrese la opcion: ");
    System.out.println("1. Listar datos por Piso ");
    System.out.println("2. Listar datos por Reservante ");
    int opcion = scanner.nextInt();
    scanner.nextLine();
    switch (opcion){
      case 1: // Datos por Piso
        System.out.println("Ingrese el piso: ");
        int piso = scanner.nextInt();
        scanner.nextLine();
        universidad.listarDatos(piso);
        break;
      case 2: // Datos por Reservante
        System.out.println("Ingrese el reservante: ");
        String reservante = scanner.nextLine();
        universidad.listarDatos(reservante);
        break;
      default:
        System.out.println("Opcion no valida");
    }
  }
}



