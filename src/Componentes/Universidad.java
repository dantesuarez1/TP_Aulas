package Componentes;

import java.io.*;
import java.time.*;
import java.util.*;
import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *  @author Dante Suarez
 *  Clase universidad para el manejo de aulas, asignaturas, cursos de extension y eventos
 */

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

  /**
   * Listar todos las aulas (Solo numero de Aula)
   */

  public void mostrarAulas(){
    for (Aula aula : aulas){
      System.out.println(aula.getId());
    }
  }

  /**
   * Listar los datos completos de las aulas del piso
   * @param piso el numero de piso
   */
  public void listarDatos(int piso) {
    for (Aula a : this.aulas) {
      if ((a.getId() / 100) == piso) {
        a.muestraAula();
      }
    }
  }


  /**
   * Listar los datos completos de todas las reservas con el codigo del reservador
   * @param codigoReserva codigo de aquel que hizo la reserva
   */
  public void listarDatos(String codigoReserva) {
    for (Aula a : aulas) {      a.muestraAula(codigoReserva);
    }
  }

  /**
   * Cancelar una reserva mediante el numero de aula y el codigo del reservador
   * @param aulaID el numero del aula en el que se encuentra la reserva
   * @param codReservador el codigo de aquel que hizo la reserva
   */
  public void cancelarReserva(int aulaID, String codReservador) {
    for (Aula a : this.aulas) {
      if (a.getId() == aulaID) { // Busca el aula
        a.cancelarReserva(codReservador); // Busca y elimina la reserva
        return; // Si encuentra el aula finaliza la funcion
      }
      if (aulaID < a.getId()) { // El aula no existe
        System.out.println("\nNo se encontro el codigo de Aula.");
        return;
      }
    } // El aula no existe
    System.out.println("\nNo se encontro el codigo de Aula.");
  }

  /**
   * Funcion para determinar el tipo de reserva y redireccionar a la funcion correspondiente
   * @param scanner Scanner para leer el input
   */
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

  /**
   * Completa los campos necesarios para crear un objeto Asignatura
   * @param scanner Scanner para input
   */
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

  /**
   * Crea un objeto asignatura y una reserva con la informacion correspondiente
   * @param fechaInicio fecha de inicio de la asignatura
   * @param fechaFin fecha de finalizacion de la asignatura
   * @param inicio horario en el cual inicia la asignatura
   * @param fin horario en el cual finaliza la asignatura
   * @param codigo el codigo de la asignatura
   * @param nombre nombre de la asignatura
   * @param cantAlum cantidad de alumnos de la asignatura
   * @param aulaid numero de aula para la reserva
   */
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

  /**
   * Completa los campos necesarios para crear un objeto Cruso De Extension
   * @param scanner Scanner para input
   */
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

  /**
   * Crea un objeto Curso de Extension y una reserva con la informacion correspondiente
   * @param codigo codigo del curso
   * @param descripcion descripcion del curso
   * @param cantidadAlumnos cantidad de alumnos del curso
   * @param fecha fecha de inicio del curso
   * @param cantidadClases cantidad de clases que durara el curso
   * @param costoAlumno costo por cada alumno del curso
   * @param aulaid numero de aula para generar la reserva
   * @param inicio hora de inicio del curso para generar la reserva
   * @param fin hora de finalizacion del curso parar generar la reserva
   */
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

  /**
   * Completa los campos necesarios para crear un objeto Evento
   * @param scanner Scanner para el ingreso de datso
   */
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

  /**
   * Crea un objeto Evento (Externo o Interno) y una reserva con la informacion correspondiente
   * @param codigo codigo del evento
   * @param descripcion descripcion del evento
   * @param organicacion organizacion que realiza la reserva en caso de ser externo
   * @param maxParticipantes cantidad maxima de participantes en el evento
   * @param esExterno evento externo o interno
   * @param costoAlquiler costo del alquiler en caso de ser externo
   * @param fecha fecha del evento
   * @param inicio horario de inicio del evento para la reserva
   * @param fin horario de finalizacion del evento para la reserva
   * @param aulaid numero de aula para la reserva
   */
  public void crearEvento(String codigo, String descripcion, String organicacion, int maxParticipantes, boolean esExterno, double costoAlquiler, LocalDate fecha, LocalTime inicio, LocalTime fin, int aulaid) {
    Evento evento;
    if(esExterno){
        evento = new Externo(codigo, descripcion, maxParticipantes, organicacion, costoAlquiler);
    }else{
        evento = new Interno(codigo, descripcion, maxParticipantes);
    }

    eventos.add(evento);
    Aula aula = buscarAulaID(aulaid);
    Reserva reserva = new Reserva(aula.getCantidadReservas(), fecha, inicio, fin, codigo);
    aula.addReserva(reserva);
  }


  /**
   * Busca un aula en la lista de aulas mediante su numero
   * @param id numero del aula que se busca
   * @return aula buscada o null en caso de que no existe
   */
  public Aula buscarAulaID(int id) {
    for (Aula aula : aulas) {
      if (aula.getId() == id)
        return aula;
    }
    return null;
  }

  /**
   * Busca el aula mediante el numero y llama a la funcion estaDisponible() para determinar si esta disponible en esa fecha y horario
   * @param id numero de aula
   * @param fecha fecha a comprobar si esta disponible
   * @param inicio horario de inicio a comprobar si esta disponible
   * @param fin horario de finalizacion a comprobar si esta disponible
   * @return true si esta disponible, false en caso contrario
   */
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

  /**
   * Generar reportes de recaudaciones por aula, piso y total de la institucion
   */
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

  /**
   * Listado completo de aulas ordenadas descendentemente por cantidad de reservas y cantidad de reservas promedio por aula
   */
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

  /**
   * Resive una lista de aulas y la ordena de manera ascendiente por numero de reservas
   * @param aulas lista de aula
   */
  public void ordenarAulasPorCantidadReservasAsce(ArrayList<Aula> aulas){
    Collections.sort(aulas, new Comparator<Aula>() {
      @Override
      public int compare(Aula a1, Aula a2) {
        return Integer.compare(a1.getCantidadReservas(), a2.getCantidadReservas());
      }
    })
    ;
  }
  /**
   * Resive una lista de aulas y la ordena de manera descendiente por numero de reservas
   * @param aulas lista de aula
   */
  public void ordenarAulasPorCantidadReservasDesc(ArrayList<Aula> aulas) {
    Collections.sort(aulas, new Comparator<Aula>() {
      @Override
      public int compare(Aula a1, Aula a2) {
        return Integer.compare(a2.getCantidadReservas(), a1.getCantidadReservas());
      }
    })
    ;
  }


  /**
   * Carga los datos desde un archivo JSON a las listas correspondientes
   * @param filePath Ruta del archivo JSON
   * @param scanner Scanner para ingreso de datos
   */
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
        Evento evento;
        if(esExterno){
          evento = new Externo(codigo, descripcion, maxParticipantes, organizacion, costoAlquiler);
        }else{
          evento = new Interno(codigo, descripcion, maxParticipantes);
        }
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

  /**
   *Lista los datos que han sido cargados hasta el momento en todas las listas
   */
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

  /**
   * Generar presistencia de los objetos
   */
  public void persistencia() throws IOException {
    FileOutputStream fos = new FileOutputStream("persistencia.txt");
    ObjectOutputStream oos = new ObjectOutputStream(fos);

    for(Aula aula : aulas){
      oos.writeObject(aula);
    }
    for(Asignatura asignatura : asignaturas){
      oos.writeObject(asignatura);
    }
    for(CursoExtension curso : cursosDeExtension){
      oos.writeObject(curso);
    }
    for(Evento evento : eventos){
      oos.writeObject(evento);
    }
    oos.close();
  }
}
