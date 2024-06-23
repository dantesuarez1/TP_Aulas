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

    public String getCodigo() {
        return codigo;
    }

    public boolean getEsExterno() {
        return esExterno;
    }

    public double getCostoAlquiler() {
        return costoAlquiler;
    }

    public void muestra(){
        System.out.println("\nEvento");
        System.out.println("Codigo: " + codigo);
        System.out.println("Descripcion: " + descripcion);
        System.out.println("Max Participantes: " + maxParticipantes);
        System.out.println("EsExterno: " + esExterno);
        System.out.println("Organizacion: " + organizacion);
        System.out.println("Costo Alquiler: " + costoAlquiler);
        System.out.println();
    }
}