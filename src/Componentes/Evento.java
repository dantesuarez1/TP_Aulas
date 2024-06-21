package Componentes;

public class Evento {
    private String codigo;
    private String descripcion;
    private int maxParticipantes;
    private boolean esExterno;
    private String organizacion;
    private double costoAlquiler;

    public Evento(String codigo, String descripcion, int participantes, boolean externo, String organizacion, double costoAlquiler) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.maxParticipantes = participantes;
        this.esExterno = externo;
        this.organizacion = organizacion;
        this.costoAlquiler = costoAlquiler;
    }
}