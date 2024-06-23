package Componentes;

/**
 * @author Dante Suarez
 */

public class Interno extends Evento{
    public Interno(String codigo, String descripcion, int maxParticipantes){
        super(codigo, descripcion, maxParticipantes);
    }

    /**
     * Muestra los campos del Evento Interno
     */
    @Override
    public void muestra(){
        System.out.println("\nEvento Interno");
        System.out.println("Codigo: "  + getCodigo());
        System.out.println("Descripcion: " + getDescripcion());
        System.out.println("Maximo de Participantes: " + getMaxParticipantes());
    }
}
