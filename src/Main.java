import Componentes.Consola;
import Componentes.Universidad;

/**
 * @author Dante Suarez
 */

public class Main {
    /**
     * Comienzo del Programa
     * @param args
     */
    public static void main(String[] args) {
        Universidad universidad = new Universidad();
        Consola consola = new Consola(universidad);

        consola.mostrarMenu();
    }
}