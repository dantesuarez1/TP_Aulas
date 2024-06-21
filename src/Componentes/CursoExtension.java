package Componentes;

public class CursoExtension {
    private String codigo;
    private String descripcion;
    private int cantidadAlumnos;
    private int cantidadClases; // Se dicta una por semana
    private double costoPorAlumno;

    public CursoExtension(String codigo, String descripcion, int cantAlumnos, int cantClases, double costoAlumno) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        cantidadAlumnos = cantAlumnos;
        cantidadClases = cantClases;
        costoPorAlumno = costoAlumno;
    }
}