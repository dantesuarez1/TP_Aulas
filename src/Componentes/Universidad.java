package Componentes;

import java.time.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Universidad {
  private ArrayList<Aula> aulas;
  private ArrayList<Asignatura> asignaturas;
  private ArrayList<CursoExtension> cursosDeExtension;
  private ArrayList<Evento> eventos;

  public Universidad() {
    this.aulas = new ArrayList<>();
    this.asignaturas = new ArrayList<>();
    this.cursosDeExtension = new ArrayList<>();
    this.eventos = new ArrayList<>();
  }

  public ArrayList<Aula> getAulas() {
    return aulas;
  }

  public void setAulas(ArrayList<Aula> aulas) {
    this.aulas = aulas;
  }

  public ArrayList<Asignatura> getAsignaturas() {
    return asignaturas;
  }

  public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
    this.asignaturas = asignaturas;
  }

  public ArrayList<CursoExtension> getCursosDeExtension() {
    return cursosDeExtension;
  }

  public void setCursosDeExtension(ArrayList<CursoExtension> cursosDeExtension) {
    this.cursosDeExtension = cursosDeExtension;
  }

  public ArrayList<Evento> getEventos() {
    return eventos;
  }

  public void setEventos(ArrayList<Evento> eventos) {
    this.eventos = eventos;
  }

  /*
   * //Metodos necesarios:
   * // Métodos para listar datos X
   * // Método para registrar reservas X
   * // Método para cancelar reservas X
   * // Método para generar reportes 
   * // Método para cargar por Archivo
   */

  // Listar los datos por piso
  void listarDatos(int piso) {
    for (Aula a : this.aulas) {
      if ((a.getId() / 100) == piso) {
        a.muestraAula();
      }
    }
  }

  // Listar las reservas correspondientes al codigo ( asignatura / curso / evento
  // )
  void listarDatos(String codigoReserva) {
    for (Aula a : aulas) {
      a.muestraAula(codigoReserva);
    }
  }

  // Metodo para cancelar una reserva con aula correspondiente y su codigo de
  // reserva
  void cancelarReserva(int aulaID, int codReserva) {
    for (Aula a : this.aulas) {
      if (a.getId() == aulaID) { // Busca el aula
        a.cancelarReserva(codReserva); // Busca y elimina la reserva
        return; // Si encuentra el aula finaliza la funcion
      }
      if (aulaID < a.getId()) { // El aula no existe
        System.out.println("\nNo se encontro el codigo de Aula.");
        return;
      }
    } // El aula no existe
    System.out.println("\nNo se encontro el codigo de Aula.");
  }

  // Registrar Reservas
  public void registraReserva() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("\nIngrese el tipo de reserva:");
    System.out.println("1. Asignatura.");
    System.out.println("2. Curso.");
    System.out.println("3. Evento.");
    int opcion = scanner.nextInt();
    switch (opcion) {
      case 1: // Asignatura
        defineAsignatura(scanner);
        break;
      case 2: // Curso
        defineCurso(scanner);
        break;
      case 3: // Evento
        defineEvento(scanner);
        break;
      default:
        System.out.println("\nOpcion no valida.");
    }
    scanner.close();
  }

  // Crear objeto y agregar a la lista
  public void defineAsignatura(Scanner scanner) {

    String codigo, nombre;
    int cantAlum;

    System.out.println("Ingrese el número de aula:");
    int numeroAula = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Ingrese la fecha de inicio (YYYY-MM-DD):");
    String fechaStr = scanner.next();
    LocalDate fechaInicio = LocalDate.parse(fechaStr);

    System.out.println("Ingrese la fecha de finalizacion (YYYY-MM-DD):");
    fechaStr = scanner.next();
    LocalDate fechaFin = LocalDate.parse(fechaStr);

    System.out.println("Ingrese la hora de inicio (HH:MM):");
    String inicioStr = scanner.next();
    LocalTime inicio = LocalTime.parse(inicioStr);

    System.out.println("Ingrese la hora de fin (HH:MM):");
    String finStr = scanner.next();
    LocalTime fin = LocalTime.parse(finStr);

    System.out.println("Ingrese el nombre de Asignatura:");
    nombre = scanner.nextLine();

    System.out.println("Ingrese el codigo de la Asignatura (ABC123):");
    codigo = scanner.nextLine();

    System.out.println("Ingrese la cantidad de alumnos:");
    cantAlum = scanner.nextInt();
    scanner.nextLine();

    Aula aula = buscarAulaID(numeroAula);
    if (cantAlum > aula.getCapacidadMaxima()) {
      System.out.println("La cantidad de alumnos supera la capacidad maxima del aula.");
      return;
    }

    LocalDate fecha = fechaInicio;

    while (!fecha.isAfter(fechaFin)) {
      if (!verificarDisponibilidadAula(numeroAula, fecha, inicio, fin)) {
        System.out.println("El aula no esta disponible en el dia " + fecha);
        return;
      }
      fecha = fecha.plusWeeks(1);
    }

    crearAsignatura(fechaInicio, fechaFin, inicio, fin, codigo, nombre, cantAlum, numeroAula);
    System.out.println("\nLa asignatura y sus reservas fueron creadas correctamente.");
  }

  public void crearAsignatura(LocalDate fechaInicio, LocalDate fechaFin, LocalTime inicio,
      LocalTime fin, String codigo, String nombre, int cantAlum, int aulaid) {

    Asignatura asignatura = new Asignatura(codigo, nombre, fechaInicio, fechaFin, fechaInicio.getDayOfWeek(), inicio,
        fin, cantAlum);

    asignaturas.add(asignatura);

    Aula aula = buscarAulaID(aulaid);
    while (!fechaInicio.isAfter(fechaFin)) {
      Reserva reserva = new Reserva(aula.getLargoReservas(), fechaInicio, inicio, fin, codigo);
      aula.addReserva(reserva);
    }
  }


  public void defineCurso(Scanner scanner){
    String codigo, descripcion, fechaStr;
    int cantidadAlumnos, cantidadClases;
    double costoAlumno;
    LocalDate fecha;

    System.out.println("Ingrese el número de aula:");
    int numeroAula = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Ingrese la descripcion del Curso de Extension: ");
    descripcion = scanner.nextLine();

    System.out.println("Ingrese el codigo del Curso de Extension: ");
    codigo = scanner.nextLine();

    System.out.println("Ingrese la fecha (YYYY-MM-DD): ");    
    fechaStr = scanner.nextLine();
    fecha = LocalDate.parse(fechaStr);
    scanner.nextLine();

    System.out.println("Ingrese la hora de inicio (HH:MM):");
    String inicioStr = scanner.next();
    LocalTime inicio = LocalTime.parse(inicioStr);

    System.out.println("Ingrese la hora de fin (HH:MM):");
    String finStr = scanner.next();
    LocalTime fin = LocalTime.parse(finStr);

    System.out.println("Ingrese la cantidad de clases: ");
    cantidadClases = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Ingrese la cantidad de Alumnos: ");
    cantidadAlumnos = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Ingrese el costo por Alumno: ");
    costoAlumno = scanner.nextDouble();
    scanner.nextLine();

    Aula aula = buscarAulaID(numeroAula);
    if (cantidadAlumnos > aula.getCapacidadMaxima()) {
      System.out.println("La cantidad de alumnos supera la capacidad maxima del aula.");
      return;
    }

    LocalDate fechaAux = fecha;
    for(int i=0; i<cantidadClases; i++){
      if(!verificarDisponibilidadAula(numeroAula, fechaAux, inicio, fin)){
        System.out.println("\nEl aula no esta disponible el dia " + fecha.plusWeeks(i));
        return;
      }
    }

    crearCurso(codigo, descripcion, cantidadAlumnos, fecha, cantidadClases, costoAlumno, numeroAula, inicio, fin);
    System.out.println("\nEl curso de extension y sus reservas fueron creados correctamente.");
  }

  public void crearCurso(String codigo, String descripcion, int cantidadAlumnos, LocalDate fecha, int cantidadClases, double costoAlumno, int aulaid, LocalTime inicio, LocalTime fin) {
    CursoExtension curso = new CursoExtension(codigo, descripcion, cantidadAlumnos, fecha, cantidadClases, costoAlumno);
    cursosDeExtension.add(curso);

    Aula aula = buscarAulaID(aulaid);
    for(int i=0; i<cantidadClases; i++){
      Reserva reserva = new Reserva(aula.getLargoReservas(), fecha, inicio, fin, codigo);
      aula.addReserva(reserva);
      fecha=fecha.plusWeeks(1);
    }
  }

  public void defineEvento(Scanner scanner){
    String descripcion, codigo, fechaStr, organizacion;
    LocalDate fecha;
    int maxParticipantes;
    boolean esExterno=false;
    double costoAlquiler=0;
    
    System.out.println("Ingrese el número de aula:");
    int numeroAula = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Ingrese la descripcion del Evento: ");
    descripcion = scanner.nextLine();

    System.out.println("Ingrese el codigo del Evento: ");
    codigo = scanner.nextLine();

    System.out.println("Ingrese el nombre de la organizacion: ");
    organizacion = scanner.nextLine();


    System.out.println("Ingrese la fecha (YYYY-MM-DD): ");    
    fechaStr = scanner.nextLine();
    fecha = LocalDate.parse(fechaStr);
    scanner.nextLine();

    System.out.println("Ingrese la hora de inicio (HH:MM):");
    String inicioStr = scanner.next();
    LocalTime inicio = LocalTime.parse(inicioStr);

    System.out.println("Ingrese la hora de fin (HH:MM):");
    String finStr = scanner.next();
    LocalTime fin = LocalTime.parse(finStr);

    int externo=0;
    while(externo!= 1 && externo !=2){
      System.out.println("El evento es externo? (1. Si | 2. No): ");
      externo = scanner.nextInt();
      scanner.nextLine();
      if(externo!= 1 && externo !=2)
        System.out.println("Opcion no valida.");
    }
    if(externo==1){esExterno=true;}


    System.out.println("Ingrese la cantidad maxima de participantes: ");
    maxParticipantes = scanner.nextInt();
    scanner.nextLine();

    if(esExterno){
      System.out.println("Ingrese el Costo del Alquiler: ");
      costoAlquiler= scanner.nextDouble();
      scanner.nextLine();
    }

    if(!verificarDisponibilidadAula(numeroAula, fecha, inicio, fin)){
      System.out.println("El aula no esta disponible en esa fecha.");
      return;
    }

    Aula aula = buscarAulaID(numeroAula);
    if (maxParticipantes > aula.getCapacidadMaxima()) {
      System.out.println("La cantidad maxima de participantes supera la capacidad maxima del aula.");
      return;
    }

    crearEvento(codigo, descripcion, organizacion, maxParticipantes, esExterno, costoAlquiler, fecha, inicio, fin, numeroAula);

  }
  public void crearEvento(String codigo, String descripcion, String organicacion, int maxParticipantes, boolean esExterno, double costoAlquiler, LocalDate fecha, LocalTime inicio, LocalTime fin, int aulaid) {
    Evento evento = new Evento( codigo, descripcion, maxParticipantes, esExterno, organicacion, costoAlquiler);

    eventos.add(evento);
    Aula aula = buscarAulaID(aulaid);
    Reserva reserva = new Reserva(aula.getLargoReservas(), fecha, inicio, fin, codigo);
    aula.addReserva(reserva);
  }

  // Disponibilidad Aula
  public Aula buscarAulaID(int id) {
    for (Aula aula : aulas) {
      if (aula.getId() == id)
        return aula;
    }
    return null;
  }

  public boolean verificarDisponibilidadAula(int id, LocalDate fecha, LocalTime inicio,
      LocalTime fin) {
    Aula aula = buscarAulaID(id);
    if (aula == null) {
      System.out.println("\nEl numero de Aula no existe");
      return false;
    }
    return aula.estaDisponible(fecha, inicio, fin);
  }
}
