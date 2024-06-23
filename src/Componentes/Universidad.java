package Componentes;

import java.io.*;
import java.time.*;
import java.util.*;
import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


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

  //Listar todos las aulas
  public void mostrarAulas(){
    for (Aula aula : aulas){
      System.out.println(aula.getId());
    }
  }
  // Listar los datos por piso
  public void listarDatos(int piso) {
    for (Aula a : this.aulas) {
      if ((a.getId() / 100) == piso) {
        a.muestraAula();
      }
    }
  }

  // Listar las reservas correspondientes al codigo ( asignatura / curso / evento
  //
  public void listarDatos(String codigoReserva) {
    for (Aula a : aulas) {      a.muestraAula(codigoReserva);
    }
  }

  // Metodo para cancelar una reserva con aula correspondiente y su codigo de
  // reserva
  public void cancelarReserva(int aulaID, String codReserva) {
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
  public void registraReserva(Scanner scanner) {
    System.out.println("\nIngrese el tipo de reserva:");
    System.out.println("1. Asignatura");
    System.out.println("2. Curso");
    System.out.println("3. Evento");
    int opcion = scanner.nextInt();
    scanner.nextLine();
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
    scanner.nextLine();

    System.out.println("Ingrese el nombre de Asignatura:");
    nombre = scanner.nextLine();

    System.out.println("Ingrese el codigo de la Asignatura (Alfanumerico):");
    codigo = scanner.nextLine();

    System.out.println("Ingrese la cantidad de alumnos:");
    cantAlum = scanner.nextInt();
    scanner.nextLine();

    Aula aula = buscarAulaID(numeroAula);
    if(aula != null) {
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
    else{
      System.out.println("El aula no existe");
    }
  }

  public void crearAsignatura(LocalDate fechaInicio, LocalDate fechaFin, LocalTime inicio,
      LocalTime fin, String codigo, String nombre, int cantAlum, int aulaid) {

    Asignatura asignatura = new Asignatura(codigo, nombre, fechaInicio, fechaFin, fechaInicio.getDayOfWeek(), inicio,
        fin, cantAlum);

    asignaturas.add(asignatura);

    Aula aula = buscarAulaID(aulaid);
    while (!fechaInicio.isAfter(fechaFin)) {
      Reserva reserva = new Reserva(aula.getCantidadReservas(), fechaInicio, inicio, fin, codigo);
      aula.addReserva(reserva);
      fechaInicio = fechaInicio.plusWeeks(1);
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

    System.out.println("Ingrese el codigo del Curso de Extension (Alfanumerico): ");
    codigo = scanner.nextLine();

    System.out.println("Ingrese la fecha (YYYY-MM-DD): ");    
    fechaStr = scanner.nextLine();
    fecha = LocalDate.parse(fechaStr);

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
      Reserva reserva = new Reserva(aula.getCantidadReservas(), fecha, inicio, fin, codigo);
      aula.addReserva(reserva);
      fecha=fecha.plusWeeks(1);
    }
  }


  public void defineEvento(Scanner scanner){
    String descripcion, codigo, fechaStr, organizacion=null;
    LocalDate fecha;
    int maxParticipantes;
    boolean esExterno=false;
    double costoAlquiler=0.0;
    
    System.out.println("Ingrese el número de aula:");
    int numeroAula = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Ingrese la descripcion del Evento: ");
    descripcion = scanner.nextLine();

    System.out.println("Ingrese el codigo del Evento (Alfanumerico): ");
    codigo = scanner.nextLine();

    System.out.println("Ingrese la fecha (YYYY-MM-DD): ");    
    fechaStr = scanner.nextLine();
    fecha = LocalDate.parse(fechaStr);

    System.out.println("Ingrese la hora de inicio (HH:MM):");
    String inicioStr = scanner.next();
    LocalTime inicio = LocalTime.parse(inicioStr);

    System.out.println("Ingrese la hora de fin (HH:MM):");
    String finStr = scanner.next();
    LocalTime fin = LocalTime.parse(finStr);

    System.out.println("Ingrese la cantidad maxima de participantes: ");
    maxParticipantes = scanner.nextInt();
    scanner.nextLine();

    Aula aula = buscarAulaID(numeroAula);
    if (maxParticipantes > aula.getCapacidadMaxima()) {
      System.out.println("La cantidad maxima de participantes supera la capacidad maxima del aula.");
      return;
    }

    int externo=0;
    while(externo!= 1 && externo !=2){
      System.out.println("El evento es externo? (1. Si | 2. No): ");
      externo = scanner.nextInt();
      scanner.nextLine();
      if(externo!= 1 && externo !=2)
        System.out.println("Opcion no valida.");
    }
    if(externo==1){esExterno=true;}


    if(esExterno){
      System.out.println("Ingrese el Nombre de la Organización: ");
      organizacion = scanner.nextLine();

      try {
        System.out.println("Ingrese el Costo del Alquiler: ");
        costoAlquiler = scanner.nextDouble();
        scanner.nextLine();
      } catch (InputMismatchException e) {
        System.out.println("Error: Ingreso inválido. Debe ingresar un número decimal.");
      }
    }


    if(!verificarDisponibilidadAula(numeroAula, fecha, inicio, fin)){
      System.out.println("El aula no esta disponible en esa fecha.");
      return;
    }
    crearEvento(codigo, descripcion, organizacion, maxParticipantes, esExterno, costoAlquiler, fecha, inicio, fin, numeroAula);
    System.out.println("\nEl evento se creo y su reserva se creo correctamente.");
  }

  public void crearEvento(String codigo, String descripcion, String organicacion, int maxParticipantes, boolean esExterno, double costoAlquiler, LocalDate fecha, LocalTime inicio, LocalTime fin, int aulaid) {
    Evento evento = new Evento( codigo, descripcion, maxParticipantes, esExterno, organicacion, costoAlquiler);

    eventos.add(evento);
    Aula aula = buscarAulaID(aulaid);
    Reserva reserva = new Reserva(aula.getCantidadReservas(), fecha, inicio, fin, codigo);
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


  //Generar reportes

  //Monto recaudado por aula, por piso y total de la institución. Tener en cuenta que las
  //reservas de las asignaturas y eventos internos no generan ingresos.
  public void recaudaciones() {
      try {
        //Crear archivo
        BufferedWriter archivo = new BufferedWriter(new FileWriter("Recaudaciones.txt"));

        //Iterator para poder utilizar while
        Iterator<Aula> it = aulas.iterator();
        if(it.hasNext()){
          Aula aula = it.next();

          //Acumuladores y contadores
          int pisoActual=(aula.getId()/100);
          double recaudacion;
          double recaudacionesPiso;
          double recaudacionesTotal=0;

          //Primera linea
          System.out.println("\nRecaudaciones: ");
          archivo.write("Recaudaciones: \n");

          while(it.hasNext()){ //Mientras no sea el ultimo
            recaudacionesPiso=0;  //Inicializa el acumulador del piso actual
            while(aula.getId()/100 == pisoActual){ // Mientras este en el mismo piso
              recaudacion=aula.recaudacionesAula(eventos, cursosDeExtension);
              System.out.println("Aula " + aula.getId() +  ": $" + recaudacion);
              archivo.write("Aula " + aula.getId() +  ": $" + recaudacion + "\n");
              recaudacionesPiso+=recaudacion;
              aula = it.next(); //Siguiente aula
            }
            //Cambio de piso
            System.out.println("\nRecaudacion del Piso " + pisoActual + ": $" + recaudacionesPiso);
            archivo.write("Recaudacion del Piso " + pisoActual + ": $" + recaudacionesPiso + "\n");
            recaudacionesTotal+=recaudacionesPiso;
            pisoActual=(aula.getId()/100);
          }
          System.out.println("\nRecaudacion total: $" + recaudacionesTotal);
          archivo.write("Recaudacion total: $" + recaudacionesTotal + "\n");
        }else{
          System.out.println("\nNo existen aulas");
        }
        archivo.close();
      } catch (IOException ioe) {
        System.out.println("No se pudo crear el archivo Recaudaciones");
      }
  }


  //Listado completo de aulas ordenadas descendentemente por cantidad de reservas. Al
  //final del listado, informar cantidad de reservas promedio por aula
  public void resevasDescendente(){

    try {
      //Crear archivo
      BufferedWriter archivo = new BufferedWriter(new FileWriter("AulasDescendente.txt"));

      ArrayList<Aula> aux = aulas;
      ordenarAulasPorCantidadReservasDesc(aux);
      int cantAulas=0;
      int reservasTotal=0;
      int reservas;
      System.out.println("Aula \tCant. Reservas" );
      for(Aula aula : aux){
        cantAulas++;
        reservas=aula.getCantidadReservas();
        reservasTotal+=reservas;
        System.out.println("\n"+ aula.getId() + "\t" + reservas);
        archivo.write("\n" + aula.getId() + "\t" + reservas + "\n");
      }
      float promedio = (float) reservasTotal / cantAulas;
      System.out.println("\nReservas promedio: " + promedio);
      archivo.write("\nReservas promedio: " + promedio + "\n");
      archivo.close();
    } catch (IOException ioe) {
      System.out.println("No se pudo crear el archivo AulasDescendente");
    }

  }
  public void ordenarAulasPorCantidadReservasAsce(ArrayList<Aula> aulas){
    Collections.sort(aulas, new Comparator<Aula>() {
      @Override
      public int compare(Aula a1, Aula a2) {
        return Integer.compare(a1.getCantidadReservas(), a2.getCantidadReservas());
      }
    })
    ;
  }

  public void ordenarAulasPorCantidadReservasDesc(ArrayList<Aula> aulas) {
    Collections.sort(aulas, new Comparator<Aula>() {
      @Override
      public int compare(Aula a1, Aula a2) {
        return Integer.compare(a2.getCantidadReservas(), a1.getCantidadReservas());
      }
    })
    ;
  }


  //
  public void cargarDatosDesdeJson(String filePath, Scanner scanner) {
    Gson gson = new Gson();
    try (FileReader reader = new FileReader(filePath)) {
      Type dataType = new TypeToken<Map<String, List<Map<String, Object>>>>() {}.getType();
      Map<String, List<Map<String, Object>>> data = gson.fromJson(reader, dataType);

      // Cargar aulas
      List<Map<String, Object>> aulasData = data.get("aulas");
      for (Map<String, Object> aulaData : aulasData) {
        int numero = ((Double) aulaData.get("numero")).intValue();
        int capacidad = ((Double) aulaData.get("capacidad")).intValue();
        Aula aula = new Aula(numero, capacidad);
        aulas.add(aula);
      }

      // Cargar asignaturas
      List<Map<String, Object>> asignaturasData = data.get("asignaturas");
      for (Map<String, Object> asignaturaData : asignaturasData) {
        String codigo = (String) asignaturaData.get("codigo");
        String nombre = (String) asignaturaData.get("nombre");
        LocalDate fechaInicio = LocalDate.parse((String) asignaturaData.get("fechaInicio"));
        LocalDate fechaFin = LocalDate.parse((String) asignaturaData.get("fechaFin"));
        DayOfWeek diaDeLaSemana = DayOfWeek.valueOf((String) asignaturaData.get("diaDeLaSemana"));
        LocalTime horaInicio = LocalTime.parse((String) asignaturaData.get("horaInicio"));
        LocalTime horaFin = LocalTime.parse((String) asignaturaData.get("horaFin"));
        int inscriptos = ((Double) asignaturaData.get("inscriptos")).intValue();
        Asignatura asignatura = new Asignatura(codigo, nombre, fechaInicio, fechaFin, diaDeLaSemana, horaInicio, horaFin, inscriptos);
        asignaturas.add(asignatura);
      }

      // Cargar cursos
      List<Map<String, Object>> cursosData = data.get("cursos");
      for (Map<String, Object> cursoData : cursosData) {
        String codigo = (String) cursoData.get("codigo");
        String descripcion = (String) cursoData.get("descripcion");
        LocalDate fechaInicio = LocalDate.parse((String) cursoData.get("fechaInicio"));
        int inscriptos = ((Double) cursoData.get("inscriptos")).intValue();
        int cantidadClases = ((Double) cursoData.get("cantidadClases")).intValue();
        double costoPorAlumno = (Double) cursoData.get("costoPorAlumno");
        CursoExtension curso = new CursoExtension(codigo, descripcion, inscriptos, fechaInicio, cantidadClases, costoPorAlumno);
        cursosDeExtension.add(curso);
      }

      // Cargar eventos
      List<Map<String, Object>> eventosData = data.get("eventos");
      for (Map<String, Object> eventoData : eventosData) {
        String codigo = (String) eventoData.get("codigo");
        String descripcion = (String) eventoData.get("descripcion");
        LocalDate fecha = LocalDate.parse((String) eventoData.get("fecha"));
        LocalTime horaInicio = LocalTime.parse((String) eventoData.get("horaInicio"));
        LocalTime horaFin = LocalTime.parse((String) eventoData.get("horaFin"));
        int maxParticipantes = ((Double) eventoData.get("maxParticipantes")).intValue();
        boolean esExterno = (Boolean) eventoData.get("esExterno");
        String organizacion = esExterno ? (String) eventoData.get("organizacion") : null;
        double costoAlquiler = esExterno ? (Double) eventoData.get("costoAlquiler") : 0.0;
        Evento evento = new Evento(codigo, descripcion, maxParticipantes, esExterno, organizacion,costoAlquiler);
        eventos.add(evento);
      }

      // Cargar reservas
      List<Map<String, Object>> reservasData = data.get("reservas");
      for (Map<String, Object> reservaData : reservasData) {
        int aulaNumero = ((Double) reservaData.get("aulaNumero")).intValue();
        Map<String, Object> reservaInfo = (Map<String, Object>) reservaData.get("reserva");
        LocalDate fecha = LocalDate.parse((String) reservaInfo.get("fecha"));
        LocalTime horaInicio = LocalTime.parse((String) reservaInfo.get("horaInicio"));
        LocalTime horaFin = LocalTime.parse((String) reservaInfo.get("horaFin"));
        String reservador = (String) reservaInfo.get("reservador");

        Aula aula = buscarAulaID(aulaNumero);
        if (aula != null) {
          Reserva reserva = new Reserva(aula.getCantidadReservas(),fecha, horaInicio, horaFin, reservador);
          aula.addReserva(reserva);
        }
      }
      ordenarAulasPorCantidadReservasAsce(aulas);
      } catch (FileNotFoundException e) {
        System.out.println("No se encontro el archivo JSON");
        throw new RuntimeException(e);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
      System.out.println("El archivo de cargo correctamente.");

      System.out.println("Desea comprobar datos? ");
      System.out.println("1. Si");
      System.out.println("2. No");
      int opcion = scanner.nextInt();
      scanner.nextLine();
      if( opcion == 1)
        compruebaDatos();
    }

    public void compruebaDatos(){
      for(Aula aula : aulas){
        aula.muestraAula();
      }
      for(Asignatura asignatura : asignaturas){
        asignatura.muestra();
      }
      for(Evento evento : eventos){
        evento.muestra();
      }
      for(CursoExtension curso : cursosDeExtension){
        curso.muestra();
      }
    }
}
