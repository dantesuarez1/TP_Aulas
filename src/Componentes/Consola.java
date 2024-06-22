package Componentes;

import java.util.Scanner;

public class Consola {
  private Universidad universidad;

  public Consola(Universidad universidad) {
    this.universidad = universidad;
  }

  public void mostrarMenu() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("1. Registrar reserva");
      System.out.println("2. Cancelar reserva");
      System.out.println("3. Generar reporte");
      System.out.println("4. Salir");
      int opcion = scanner.nextInt();
      switch (opcion) {
        case 1:
          universidad.registraReserva();
          break;
        case 2:
          System.out.println("Ingrese número de aula:");
          int numeroAula = scanner.nextInt();
          System.out.println("Ingrese código de reserva:");
          int codigoReserva = scanner.nextInt();
          universidad.cancelarReserva(numeroAula, codigoReserva);
          break;
        case 3:
          // universidad.generarReporteRecaudacion();
          break;
        case 4:
          scanner.close();
          return;
        default:
          System.out.println("\nOpción no válida");
      }
    }
  }

}
