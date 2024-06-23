package Componentes;

/**
 * @author Dante Suarez
 */

public class Evento {
    private String codigo;
    private String descripcion;
    private int maxParticipantes;

    public Evento(String codigo, String descripcion, int participantes) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.maxParticipantes = participantes;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getCostoAlquiler() {
        return 0;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public int getMaxParticipantes() {
        return maxParticipantes;
    }

    public void muestra(){
        ;
    }
}