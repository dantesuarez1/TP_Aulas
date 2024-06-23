import Componentes.Consola;
import Componentes.Universidad;

public class Main {
    public static void main(String[] args) {
        Universidad universidad = new Universidad();
        Consola consola = new Consola(universidad);

        consola.mostrarMenu();
    }
}