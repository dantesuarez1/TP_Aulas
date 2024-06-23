package Componentes;

/**
 * @author Dante Suarez
 */

public class Externo extends Evento{
    private String organizacon;
    private double alquiler;
    public Externo(String codigo, String descripcion, int maxParticipantes, String organizacon, double alquiler){
        super(codigo, descripcion, maxParticipantes);
        this.organizacon = organizacon;
        this.alquiler = alquiler;
    }

    public String getOrganizacon() {
        return organizacon;
    }

    @Override
    public double getCostoAlquiler() {
        return alquiler;
    }

    /**
     * Muestra los campos del Evento Externo
     */
    @Override
    public void muestra(){
        System.out.println("\nEvento Externo");
        System.out.println("Codigo: "  + getCodigo());
        System.out.println("Descripcion: " + getDescripcion());
        System.out.println("Maximo de Participantes: " + getMaxParticipantes());
        System.out.println("Organizacon: " + organizacon);
        System.out.println("Alquiler: $" + alquiler);
    }
}
