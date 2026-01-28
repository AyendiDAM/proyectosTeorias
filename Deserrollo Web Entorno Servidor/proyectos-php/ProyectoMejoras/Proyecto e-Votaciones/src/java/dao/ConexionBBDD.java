/*

Clase para realizar todas las operaciones con la bbdd
 */
package dao;


import java.io.File;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import modelos.Candidato;
import modelos.Comunidad_autonoma;
import modelos.Eleccion;
import modelos.Localidad;

import modelos.Partido_politico;
import modelos.Usuario;
import modelos.Voto;

public class ConexionBBDD {

    private String url;
    private String usuario;
    private String contraseña;
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;

    //creamos la conección
    public ConexionBBDD() throws ClassNotFoundException, SQLException {
        this.usuario = "admin";
        this.contraseña = "1234";
        this.url = "jdbc:mysql://localhost:3306/bbdd_votaciones_ayendi?useUnicode=true&characterEncoding=UTF-8";//no se copia la url como tal:
        //se pone el puerto del xammp y jdbc 

        //cargar el droier para que java y mysql se entienda
        Class.forName("com.mysql.cj.jdbc.Driver");

        //se obtiene una conexion con la base de datos
        this.conexion = DriverManager.getConnection(this.url, this.usuario, this.contraseña);

        //se cra un Statement,para realizar las acciones
        this.sentencia = this.conexion.createStatement();

    }
    // 1. Validar si hay alguna elección ACTIVA (Abierta o Cerrada)
    // Regla: Solo puede haber 1 proceso electoral activo a la vez para no liar al votante.

    public boolean hayEleccionActiva() throws Exception {
        String sql = "SELECT COUNT(*) FROM eleccion WHERE estado != 'FINALIZADA'";
        PreparedStatement pst = conexion.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    // 2. Crear Elección (Nace CERRADA por defecto según tu columna Enum)
    public boolean crearEleccion(Eleccion e) throws Exception {
        String sql = "INSERT INTO eleccion (nombre, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, 'CERRADA')";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, e.getNombre());
        pst.setDate(2, e.getFechaInicio());
        pst.setDate(3, e.getFechaFinal());
        return pst.executeUpdate() > 0;
    }

    // 3. Listar elecciones
    public ArrayList<Eleccion> obtenerElecciones() throws Exception {
        ArrayList<Eleccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM eleccion ORDER BY id_eleccion DESC";
        PreparedStatement pst = conexion.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Eleccion e = new Eleccion();
            e.setId(rs.getInt("id_eleccion"));
            e.setNombre(rs.getString("nombre"));
            e.setFechaInicio(rs.getDate("fecha_inicio"));
            e.setFechaFinal(rs.getDate("fecha_fin"));
            e.setEstado(rs.getString("estado"));
            lista.add(e);
        }
        return lista;
    }

    // 4. Cambiar Estado (Para ABRIR o FINALIZAR)
    public boolean actualizarEstado(int idEleccion, String nuevoEstado) throws Exception {
        String sql = "UPDATE eleccion SET estado = ? WHERE id_eleccion = ?";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, nuevoEstado);
        pst.setInt(2, idEleccion);
        return pst.executeUpdate() > 0;
    }


    
    // 1. Validar que el DNI existe en el censo (Requisito Rúbrica)
    public boolean existeEnCenso(String dni) throws Exception {
        String sql = "SELECT dni FROM persona_censo WHERE dni = ?";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, dni);
        return pst.executeQuery().next();
    }


    
    // 1. Validar si ya es candidato (AHORA SÍ FUNCIONARÁ porque existe la columna dni)
    public boolean yaEsCandidato(String dni) throws Exception {
        String sql = "SELECT id_candidato FROM candidato WHERE dni = ?";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, dni);
        return pst.executeQuery().next();
    }

// 2. Registrar Candidato (Adaptado a tus columnas: dni, nombre, apellidos, numero_lista)
    public boolean registrarCandidato(Candidato c) throws Exception {
        // Primero: Recuperamos nombre y apellidos del censo para no dejar esos campos vacíos
        String sqlDatos = "SELECT nombre, apellidos FROM persona_censo WHERE dni = ?";
        PreparedStatement pstDatos = conexion.prepareStatement(sqlDatos);
        pstDatos.setString(1, c.getDni());
        ResultSet rs = pstDatos.executeQuery();

        String nombre = "";
        String apellidos = "";

        if (rs.next()) {
            nombre = rs.getString("nombre");
            apellidos = rs.getString("apellidos");
        }

        // Segundo: Insertamos en la tabla candidato
        // FÍJATE: Uso 'numero_lista' que es como se llama tu columna en la foto
        String sqlInsert = "INSERT INTO candidato (dni, nombre, apellidos, id_partido, numero_lista) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = conexion.prepareStatement(sqlInsert);

        pst.setString(1, c.getDni());         // El DNI que acabamos de crear
        pst.setString(2, nombre);             // Nombre sacado del censo
        pst.setString(3, apellidos);          // Apellidos sacados del censo
        pst.setInt(4, c.getIdPartido());
        pst.setInt(5, c.getOrden());          // Tu variable 'orden' va a la columna 'numero_lista'

        return pst.executeUpdate() > 0;
    }
    // 1. Registrar nuevo partido
    public boolean registrarPartido(Partido_politico p) throws Exception {
        // Asumo que tu tabla tiene: nombre, siglas, logo. (El id es autoincremental)
        String sql = "INSERT INTO partido_politico (nombre, siglas, logo) VALUES (?, ?, ?)";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, p.getNombre());
        pst.setString(2, p.getSiglas());
        pst.setString(3, p.getLogo()); // Aquí guardaremos el NOMBRE del archivo (ej: "pp.png")

        return pst.executeUpdate() > 0;
    }

    // 2. Obtener lista de partidos (Lo necesitaremos para el select de candidatos después)
    public ArrayList<Partido_politico> obtenerPartidos() throws Exception {
        ArrayList<Partido_politico> lista = new ArrayList<>();
        String sql = "SELECT * FROM partido_politico";
        PreparedStatement pst = conexion.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
         while (rs.next()) {
           
            
             Partido_politico p = new Partido_politico();
            p.setId(rs.getInt("id_partido")); // Ajusta el nombre de columna si es diferente
            p.setNombre(rs.getString("nombre"));
            p.setSiglas(rs.getString("siglas"));
            p.setLogo(rs.getString("logo"));
            lista.add(p);
        }
        return lista;
    
    }
    // Método para validar el Login
public Usuario login(String dni, String passwordEnMd5) throws Exception {
    Usuario usuario = null;
    
    // Hacemos JOIN para obtener el nombre de la tabla persona_censo
    // y los datos de cuenta de la tabla usuario
    String sql = "SELECT u.id_usuario, u.dni, u.rol, u.ha_votado, p.nombre, p.apellidos " +
                 "FROM usuario u " +
                 "JOIN persona_censo p ON u.dni = p.dni " +
                 "WHERE u.dni = ? AND u.password_md5 = ?";
                 
    PreparedStatement pst = conexion.prepareStatement(sql);
    pst.setString(1, dni);
    pst.setString(2, passwordEnMd5); // Recibe la pass YA encriptada
    
    ResultSet rs = pst.executeQuery();
    
    if (rs.next()) {
        usuario = new Usuario();
        usuario.setId_usuario(rs.getInt("id_usuario"));
        usuario.setDni(rs.getString("dni"));
        usuario.setRol(rs.getString("rol")); // IMPORTANTE: 'ADMIN' o 'VOTANTE'
        usuario.setVoto(rs.getInt("ha_votado"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellidos"));
    }
    
    return usuario;
}

    public ArrayList<String> getSeccion() throws SQLException {
        String orden = "SELECT dni From USUARIO";
        ArrayList<String> nombre = new ArrayList<>();
        this.resultado = this.sentencia.executeQuery(orden);
        while (this.resultado.next()) {
            String guardaNombre = this.resultado.getString("dni");
            nombre.add(guardaNombre);
        }

        return nombre;

    }

  public Usuario buscarEnCenso(String dni) throws Exception {
    // Buscamos en la tabla del CENSO, no en usuario
    String sql = "SELECT nombre, apellidos, fecha_nacimiento FROM persona_censo WHERE dni = ?";
    PreparedStatement pst = conexion.prepareStatement(sql);
    pst.setString(1, dni);
    ResultSet rs = pst.executeQuery();

    if (rs.next()) {
        Usuario u = new Usuario();
        u.setDni(dni);
        u.setNombre(rs.getString("nombre")); // Lo guardamos en el objeto java (aunque no vaya a la tabla usuario)
        u.setApellido(rs.getString("apellidos")); // Asegúrate de tener este campo en tu modelo Usuario
        u.setFechaNacimiento(rs.getDate("fecha_nacimiento")); // Necesitarás añadir este campo a tu modelo temporalmente o devolverlo aparte
        return u;
    } else {
        return null; // No existe en el censo
    }
}

// Y corregimos el método de registrar para que coincida con TU tabla (sin nombre)
public boolean registrarUsuario(Usuario u) throws Exception {
    // Fíjate que quité el campo 'nombre' del INSERT porque tu tabla 'usuario' no lo tiene
    String sql = "INSERT INTO usuario (dni, password_md5, rol, ha_votado) VALUES (?, ?, ?, 0)";
    PreparedStatement pst = conexion.prepareStatement(sql);
    pst.setString(1, u.getDni());
    pst.setString(2, u.getPassword()); // La contraseña ya viene encriptada del controlador
    pst.setString(3, "VOTANTE"); // O el rol que uses (string o int, según tu BBDD)
    
    return pst.executeUpdate() > 0;
}

// 2. Comprobar si el usuario YA está registrado en la app
    public boolean existeUsuarioRegistrado(String dni) throws Exception {
        String sql = "SELECT id_usuario FROM usuario WHERE dni = ?";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, dni);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }
    
    public String inicioCorrecto(String dni) throws SQLException {
        String sql = "SELECT dni FROM usuario WHERE dni = ?";
        String dniB = "";

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, dni); // ← asignamos el parámetro
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dniB = rs.getString("dni");
                }
            }
        }

        return dniB;
    }

    public int insertarUsuario(String dni, String contrasena) throws SQLException {
        String sql = "INSERT INTO USUARIO (dni, password_md5,rol,ha_votado) VALUES (?,?,?,?)";
        int filasAfectadas = 0;

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.setString(2, contrasena);
            ps.setString(3, "VOTANTE");
            ps.setBoolean(4, false);
            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

    public int personaCenso(String dni, String nombre, String apellido, String fecha, int localidad) throws SQLException {
        String sql = "INSERT INTO persona_censo (dni,nombre,apellidos,fecha_nacimiento,id_localidad) VALUES (?,?,?,?,?)";
        int filasAfectadas = 0;

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, fecha);
            ps.setInt(5, localidad);
            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

    public boolean buscarResgistro(String dni) throws SQLException {
        String sql = "SELECT dni FROM USUARIO WHERE dni = ?";
        String dni2 = "";
        boolean existe = false;
        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, dni); // ← asignamos el parámetro
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dni2 = rs.getString("dni");
                    if (dni2.equalsIgnoreCase(dni)) {
                        existe = true;
                    }
                }
            }
        }

        return existe;
    }
    
    //comprueba edad
    
    
    public boolean compruebaEdad(String dni) throws SQLException {
        String sql = "SELECT fecha_nacimiento FROM persona_censo WHERE dni = ?";
        String dni2 = "";
        boolean existe = false;
     
        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, dni); // ← asignamos el parámetro
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dni2 = rs.getString("dni");
                         // Formato esperado: dd/MM/yyyy
                         
      java.sql.Date dbDate = rs.getDate("fecha_nacimiento");
String fecha = (dbDate != null) ? dbDate.toString() : null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                 // Convertir String a LocalDate
                LocalDate fechaNacimiento = LocalDate.parse(fecha, formatter);
              LocalDate hoy = LocalDate.now();
              


            // Calcular la edad
            Period periodo = Period.between(fechaNacimiento, hoy);
            int edad = periodo.getYears();
               if(edad>=18){
                   existe = true;
               }
                
                }
            }
        }

        return existe;
    }

    public String compruebaRol(String dni) throws SQLException {
        String sql = "SELECT rol FROM USUARIO WHERE dni = ?";
        String dni2 = "";
        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, dni); // ← asignamos el parámetro
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dni2 = rs.getString("rol");
              
                }
            }
        }

        return dni2;
    }

    public String nombreCookie(String dni) throws SQLException {
        String sql = "SELECT nombre FROM persona_censo WHERE dni = ?";
        String dni2 = "";

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, dni); // ← asignamos el parámetro
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dni2 = rs.getString("nombre");

                }
            }
        }

        return dni2;
    }

    public int obtenerLocalidadUsuarioVotado(String dni) throws SQLException {
        String sql = "SELECT id_localidad  FROM persona_censo WHERE dni = ?";
        int localidad = 0;

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, dni); // ← asignamos el parámetro
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    localidad = rs.getInt("id_localidad");

                }
            }
        }

        return localidad;
    }

    public String eleccionNombre() throws SQLException {
        String sql = "SELECT nombre FROM eleccion ";
        String nombreEleccion = "";

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nombreEleccion = rs.getString("nombre");

                }
            }
        }

        return nombreEleccion;
    }

    public int eleccionNombreId(String nombre) throws SQLException {
        String sql = "SELECT id_eleccion FROM eleccion where nombre=?";
        int nombreEleccion = 0;

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, nombre); // ← asignamos el parámetro
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nombreEleccion = rs.getInt("id_eleccion");

                }
            }
        }

        return nombreEleccion;
    }

    ///acciones admin
///
///
///
///
///
///
///
///
public String updateEleccionAdmin(String nombre, String nuevoEstado) throws SQLException {
        // Consulta SQL con parámetros
        ///   String sql = "UPDATE usuario SET rol = ? WHERE dni = ?;";
    String sql = "UPDATE eleccion SET estado = ? WHERE nombre = ?;";
        int filasAfectadas;
        String nombreElecion = nuevoEstado;
        // Usar try-with-resources para cerrar automáticamente el PreparedStatement

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {

            // Asignar valores a los parámetros (sin comillas manuales)
            ps.setString(1, nuevoEstado);
            ps.setString(2, nombre);

            // Ejecutar la actualización
            filasAfectadas = ps.executeUpdate();

        }
        return nombreElecion;
    }

    //metodo para introducir partidos
    public int partidoPolitico(String nombre, String sigla, String imagen)
            throws SQLException, IOException {

        String sql = "INSERT INTO partido_politico (nombre, siglas,logo) VALUES (?, ?,?)";
        int filasAfectadas = 0;
        // Validar que el archivo existe
        File archivo = new File(imagen);
        if (!archivo.exists() || !archivo.isFile()) {
            System.err.println("Error: El archivo no existe o no es válido.");

        }
        try (PreparedStatement ps = this.conexion.prepareStatement(sql); FileInputStream fis = new FileInputStream(archivo)) {
            ps.setString(1, nombre);
            ps.setString(2, sigla);
            ps.setBinaryStream(3, fis, (int) archivo.length());
            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

    //metodo para introducir partidos
    public int insertarCandidato(String nombre, String apellidos, int numeroLista, int idPartido)
            throws SQLException, IOException {

        String sql = "INSERT INTO candidato (nombre, apellidos,numero_lista,id_partido) VALUES (?,?,?,?)";
        int filasAfectadas = 0;
        // File file = new File(logo);

        /*if (!file.exists() || !file.isFile()) {
        throw new IOException("El archivo de logo no existe o no es un fichero: " + logo);
    }

    byte[] bytes = Files.readAllBytes(file.toPath());*/
        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setInt(3, numeroLista);
            ps.setInt(4, idPartido);
            // ps.setBytes(3, bytes); // setBytes maneja el tamaño internamente
            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

    public int insertarEleccion(String nombre, String fechaIncio, String fechaFin, String estado) throws SQLException {
        String sql = "INSERT INTO eleccion (nombre,fecha_inicio,fecha_fin,estado) VALUES (?,?,?,?)";
        int filasAfectadas = 0;

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, fechaIncio);
            ps.setString(3, fechaFin);
            ps.setString(4, estado);
            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

//metodo para darme a elegir todos los partidos
    //metodo para introducir partidos
    public ArrayList<Partido_politico> verTodoPartido() throws SQLException {
        String sql = "SELECT id_partido,nombre,siglas,logo FROM partido_politico ";
        ArrayList<Partido_politico> lista = new ArrayList<>();
        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            this.resultado = ps.executeQuery();

            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {
                String nombre = this.resultado.getString("nombre");
                String siglas = this.resultado.getString("siglas");
                String logo = this.resultado.getString("logo");

                Partido_politico p = new Partido_politico(nombre, siglas, logo);
                lista.add(p);
            }
        }

        return lista;
    }

    public ArrayList<Eleccion> eleccionesTodas() throws SQLException {
        String sql = "SELECT id_eleccion,nombre,fecha_inicio,fecha_fin,estado FROM eleccion ";
        ArrayList<Eleccion> lista = new ArrayList<>();
        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            this.resultado = ps.executeQuery();

            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {
                String nombre = this.resultado.getString("nombre");
                int id = this.resultado.getInt("id_eleccion");
                Date fecha_ini = this.resultado.getDate("fecha_inicio");
                Date fecha_fin = this.resultado.getDate("fecha_fin");
                String estado = this.resultado.getString("estado");

                Eleccion eleccion = new Eleccion(id, nombre, fecha_ini, fecha_fin, estado);
                lista.add(eleccion);
            }
        }

        return lista;
    }

    ///insertar votos
///
public int insertarVoto(int idEleccion, int idPartido, int idLocalidad) throws SQLException {

        String sql = "INSERT INTO voto (id_eleccion, id_partido, id_localidad) VALUES (?, ?, ?)";

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setInt(1, idEleccion);
            ps.setInt(2, idPartido);
            ps.setInt(3, idLocalidad);

            return ps.executeUpdate();
        }
    }

//obtener idelecciones
    public int eleccionId(String nombre) throws SQLException {
        String sql = "SELECT id_eleccion FROM eleccion WHERE nombre = ?";
        int nombreId = 0;

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            this.resultado = ps.executeQuery();

            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {
                nombreId = this.resultado.getInt("id_eleccion");

            }
        }

        return nombreId;
    }

//obtener idPartido
    public String id_partido(String nombre) throws SQLException {
        String sql = "SELECT id_partido FROM partido_politico WHERE siglas = ?";
        String nombreId = "";

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            this.resultado = ps.executeQuery();

            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {

                nombreId = this.resultado.getString("id_partido");

            }
        }

        return nombreId;
    }

//obtener idSuario
    public String id_Usuario(int nombre) throws SQLException {
        String sql = "SELECT id_usuario FROM usuario WHERE dni = ?";
        String nombreId = "";

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            this.resultado = ps.executeQuery();

            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {
                nombreId = this.resultado.getString("id_usuario");

            }
        }

        return nombreId;
    }

    public int updateUsuarioRol(String dni, String rol) throws SQLException {
        // Consulta SQL con parámetros
        String sql = "UPDATE usuario SET rol = ? WHERE dni = ?;";
        int filasAfectadas;
        String nuevoRol = rol;
        // Usar try-with-resources para cerrar automáticamente el PreparedStatement

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {

            // Asignar valores a los parámetros (sin comillas manuales)
            ps.setString(1, rol);
            ps.setString(2, dni);

            // Ejecutar la actualización
            filasAfectadas = ps.executeUpdate();

        }
        return filasAfectadas;
    }

//actualizar voto
    public int updateVoto(int voto, String dni) throws SQLException {
        // Consulta SQL con parámetros
        String sql = "UPDATE usuario SET ha_votado = ? WHERE dni = ?;";
        int filasAfectadas;
        int votoUsuario = voto;
        // Usar try-with-resources para cerrar automáticamente el PreparedStatement

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {

            // Asignar valores a los parámetros (sin comillas manuales)
            ps.setInt(1, votoUsuario);
            ps.setString(2, dni);

            // Ejecutar la actualización
            filasAfectadas = ps.executeUpdate();

        }
        return filasAfectadas;
    }

//obtener idSuario
    public int devuelveVotoUsuario(String dni) throws SQLException {
        String sql = "SELECT ha_votado FROM usuario WHERE dni = ?";
        int voto = 0;

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {

            ps.setString(1, dni);
            this.resultado = ps.executeQuery();
            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {
                voto = this.resultado.getInt("ha_votado");

            }
        }

        return voto;
    }
//metodo para darme todas las elecciones
//metodo para darme a elegir todos los partidos
    //metodo para introducir partidos

    public ArrayList<Eleccion> verTodoEleccionFinalizada() throws SQLException {
        String sql = "SELECT id_eleccion,nombre,fecha_inicio,fecha_fin,estado FROM eleccion ";
        ArrayList<Eleccion> lista = new ArrayList<>();
        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            this.resultado = ps.executeQuery();

            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {
                int id = this.resultado.getInt("id_eleccion");
                String nombre = this.resultado.getString("nombre");
                Date fechaInicio = this.resultado.getDate("fecha_inicio");
                Date fechaFinal = this.resultado.getDate("fecha_fin");
                String estado = this.resultado.getString("estado");

//    public Eleccion(int id, String nombre, Date fechaInicio, Date fechaFinal,String estado) {
                if (estado.equalsIgnoreCase("FINALIZADA")) {
                    Eleccion eleccion = new Eleccion(id, nombre, fechaInicio, fechaFinal, estado);
                    lista.add(eleccion);
                }
            }
        }

        return lista;
    }

    ///votacciones resultados generales:
public ArrayList<Voto> verTodoVotos() throws SQLException {
    // SQL: Incluye todas las IDs y nombres necesarios, con GROUP BY para evitar errores en algunas BBDD.
    String sql = "SELECT\n"
            + "    CA.nombre AS nombre_comunidad,\n"
            + "    L.nombre AS nombre_localidad,\n"
            + "    V.id_voto, V.id_eleccion, V.id_partido, V.fecha_voto, V.id_localidad, \n"
            + "    COUNT(V.id_voto) AS total_votos\n"
            + "FROM\n"
            + "    voto V\n"
            + "JOIN\n"
            + "    localidad L ON V.id_localidad = L.id_localidad\n"
            + "JOIN\n"
            + "    comunidad_autonoma CA ON L.id_comunidad = CA.id_comunidad\n"
            + "GROUP BY\n"
            + "    CA.nombre, L.nombre, V.id_voto, V.id_eleccion, V.id_partido, V.fecha_voto, V.id_localidad\n"
            + "ORDER BY\n"
            + "    nombre_comunidad,\n"
            + "    total_votos DESC;";
    
    ArrayList<Voto> lista = new ArrayList<>();
    try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
        this.resultado = ps.executeQuery();

        // Recorremos el resultado y creamos objetos Voto
        while (this.resultado.next()) {
            // ✅ La lectura de IDs es correcta.
            int id_voto = this.resultado.getInt("id_voto");
            int id_eleccion = this.resultado.getInt("id_eleccion");
            int id_partido = this.resultado.getInt("id_partido");
            Date fecha_voto = this.resultado.getDate("fecha_voto");
            int id_localidad = this.resultado.getInt("id_localidad");

            // public Voto(int id_voto, int id_localidad, int id_partido, int id_eleccion, Date fecha)
            Voto p = new Voto(id_voto, id_localidad, id_partido, id_eleccion, fecha_voto);
            lista.add(p);
        }
    }

    return lista;
}


//buscar por localidad
public ArrayList<Localidad> verTodoVotoLocalidad() throws SQLException {
    // SQL: Incluye id_localidad, id_comunidad y nombre_localidad
    String sql = "SELECT\n"
            + "    L.nombre AS nombre_localidad,\n"
            + "    L.id_localidad, \n" 
            + "    L.id_comunidad, \n" 
            + "    COUNT(V.id_voto) AS total_votos\n"
            + "FROM\n"
            + "    voto V\n"
            + "JOIN\n"
            + "    localidad L ON V.id_localidad = L.id_localidad\n"
            + "GROUP BY\n"
            + "    L.nombre, L.id_localidad, L.id_comunidad\n"
            + "ORDER BY\n"
            + "    total_votos DESC;";
    
    ArrayList<Localidad> lista = new ArrayList<>();
    try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
        this.resultado = ps.executeQuery();

        while (this.resultado.next()) {
            // ✅ Lectura corregida: Lee las IDs y el nombre con los alias correctos
            int id_localidad = this.resultado.getInt("id_localidad");
            int id_comunidad = this.resultado.getInt("id_comunidad");
            String nombre = this.resultado.getString("nombre_localidad"); // ¡Usar alias!
            // Nota: El total de votos está disponible en this.resultado.getLong("total_votos")

            // Asumiendo un constructor Localidad(int id_localidad, int id_comunidad, String nombre)
            Localidad p = new Localidad(id_localidad, id_comunidad, nombre); 
            lista.add(p);
        }
    }

    return lista;
}



 //buscar por comunidad autonomas
public ArrayList<Comunidad_autonoma> verTodoVotoComunidad() throws SQLException {
    // SQL: Incluye id_comunidad y nombre_comunidad
    String sql = "SELECT\n"
            + "    CA.nombre AS nombre_comunidad,\n"
            + "    CA.id_comunidad, \n" 
            + "    COUNT(V.id_voto) AS total_votos\n"
            + "FROM\n"
            + "    voto V\n"
            + "JOIN\n"
            + "    localidad L ON V.id_localidad = L.id_localidad\n"
            + "JOIN\n"
            + "    comunidad_autonoma CA ON L.id_comunidad = CA.id_comunidad\n"
            + "GROUP BY\n"
            + "    CA.nombre, CA.id_comunidad\n"
            + "ORDER BY\n"
            + "    total_votos DESC;";
    
    ArrayList<Comunidad_autonoma> lista = new ArrayList<>();
    try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
        this.resultado = ps.executeQuery();

        while (this.resultado.next()) {
            // ✅ Lectura corregida: Lee la ID y el nombre con el alias correcto
            int id_comunidad = this.resultado.getInt("id_comunidad");
            String nombre = this.resultado.getString("nombre_comunidad"); // ¡Usar alias!
            // Nota: El total de votos está disponible en this.resultado.getLong("total_votos")

            // Asumiendo un constructor Comunidad_autonoma(int id_comunidad, String nombre)
            Comunidad_autonoma p = new Comunidad_autonoma(id_comunidad, nombre);
            lista.add(p);
        }
    }

    return lista;
}
public void mostrarPresidente() throws SQLException {
    String sql = "SELECT\n" +
            "    C.nombre,\n" +
            "    C.apellidos,\n" +
            "    P.nombre AS nombre_partido,\n" +
            "    COUNT(V.id_voto) AS total_votos_partido\n" +
            "FROM\n" +
            "    voto V\n" +
            "JOIN\n" +
            "    partido_politico P ON V.id_partido = P.id_partido\n" +
            "JOIN\n" +
            "    candidato C ON P.id_partido = C.id_partido\n" +
            "WHERE\n" +
            "    C.numero_lista = 1\n" +
            "GROUP BY\n" +
            "    P.id_partido, C.nombre, C.apellidos, P.nombre\n" +
            "ORDER BY\n" +
            "    total_votos_partido DESC\n" +
            "LIMIT 1;";

    try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // 1. Extraer los datos de las columnas
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String nombrePartido = rs.getString("nombre_partido");
                int totalVotos = rs.getInt("total_votos_partido");

                // 2. Mostrar los datos
                System.out.println("--- 🏆 Presidente Electo ---");
                System.out.println("Candidato: " + nombre + " " + apellidos);
                System.out.println("Partido: " + nombrePartido);
                System.out.println("Votos del partido: " + totalVotos);
                System.out.println("--------------------------");

            } else {
                // Manejar el caso donde no hay resultados (si no hay votos o candidatos)
                System.out.println("No se encontró ningún presidente según el criterio de la consulta.");
            }
        }
    }
}
public String obtenerPresidente() throws SQLException {
    String sql = "SELECT\n" +
            "    C.nombre,\n" +
            "    C.apellidos,\n" +
            "    P.nombre AS nombre_partido,\n" +
            "    COUNT(V.id_voto) AS total_votos_partido\n" +
            "FROM\n" +
            "    voto V\n" +
            "JOIN\n" +
            "    partido_politico P ON V.id_partido = P.id_partido\n" +
            "JOIN\n" +
            "    candidato C ON P.id_partido = C.id_partido\n" +
            "WHERE\n" +
            "    C.numero_lista = 1\n" + // Criterio: el candidato "cabeza de lista"
            "GROUP BY\n" +
            "    P.id_partido, C.nombre, C.apellidos, P.nombre\n" +
            "ORDER BY\n" +
            "    total_votos_partido DESC\n" +
            "LIMIT 1;";
    
    // Usamos StringBuilder para construir el resultado HTML
    StringBuilder resultado = new StringBuilder();

    try (PreparedStatement ps = this.conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) { 
        
        if (rs.next()) {
            // Extraer los datos del candidato ganador
            String nombre = rs.getString("nombre");
            String apellidos = rs.getString("apellidos");
            String nombrePartido = rs.getString("nombre_partido");
            int totalVotos = rs.getInt("total_votos_partido");
            
            // Construir el HTML que se mostrará en el JSP
            resultado.append("<h2>El Presidente Electo es:</h2>");
            resultado.append("<ul>");
            resultado.append("<li><strong>Candidato:</strong> ").append(nombre).append(" ").append(apellidos).append("</li>");
            resultado.append("<li><strong>Partido:</strong> ").append(nombrePartido).append("</li>");
            resultado.append("<li><strong>Votos del partido:</strong> ").append(totalVotos).append("</li>");
            resultado.append("</ul>");

        } else {
            resultado.append("<p>⚠️ **No se encontró ningún presidente** o no hay suficientes datos de votos/candidatos registrados.</p>");
        }
    }
    return resultado.toString();
}



//buscar partidos
    public ArrayList<Partido_politico> partidosTotales() throws SQLException {
        String sql = "SELECT * FROM partido_politico ";
        ArrayList<Partido_politico> lista = new ArrayList<>();
        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            this.resultado = ps.executeQuery();

            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {
                int id_partido = this.resultado.getInt("id_partido");
                String nombre = this.resultado.getString("nombre");
                String sigas = this.resultado.getString("siglas");


                Partido_politico eleccion = new Partido_politico(id_partido, nombre, sigas);
                lista.add(eleccion);
            }
        }

        return lista;
    }
    
    
    //mostrar todas las localidades
        public ArrayList<Localidad> verTodasLocalidades() throws SQLException {
        String sql = "SELECT * FROM localidad";
     ArrayList<Localidad> lista = new ArrayList<>();
    try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
        this.resultado = ps.executeQuery();

        while (this.resultado.next()) {
            int id_localidad = this.resultado.getInt("id_localidad");
            int id_comunidad = this.resultado.getInt("id_comunidad");
            String nombre = this.resultado.getString("nombre");


//   public Localidad(int id_localidad, int id_comunidad, String nombre) {
            Localidad p = new Localidad(id_localidad, id_comunidad, nombre); 
            lista.add(p);
        }
    }

    return lista;
}
public String obtenerPresidentePartido() throws SQLException {
    String sql = "SELECT\n" +
            "    C.nombre,\n" +
            "    C.apellidos,\n" +
            "    P.nombre AS nombre_partido,\n" +
            "    COUNT(V.id_voto) AS total_votos_partido\n" +
            "FROM\n" +
            "    voto V\n" +
            "JOIN\n" +
            "    partido_politico P ON V.id_partido = P.id_partido\n" +
            "JOIN\n" +
            "    candidato C ON P.id_partido = C.id_partido\n" +
            "WHERE\n" +
            "    C.numero_lista = 1\n" + // Criterio: el candidato "cabeza de lista"
            "GROUP BY\n" +
            "    P.id_partido, C.nombre, C.apellidos, P.nombre\n" +
            "ORDER BY\n" +
            "    total_votos_partido DESC\n" +
            "LIMIT 1;";
    String nombrePartido=""; 
    
    // Usamos StringBuilder para construir el resultado HTML
    StringBuilder resultado = new StringBuilder();

    try (PreparedStatement ps = this.conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) { 
        
        if (rs.next()) {
            // Extraer los datos del candidato ganador
            String nombre = rs.getString("nombre");
            String apellidos = rs.getString("apellidos");
          nombrePartido  = rs.getString("nombre_partido");
            int totalVotos = rs.getInt("total_votos_partido");
            
            // Construir el HTML que se mostrará en el JSP
            resultado.append("<h2>El Presidente Electo es:</h2>");
            resultado.append("<ul>");
            resultado.append("<li><strong>Candidato:</strong> ").append(nombre).append(" ").append(apellidos).append("</li>");
            resultado.append("<li><strong>Partido:</strong> ").append(nombrePartido).append("</li>");
            resultado.append("<li><strong>Votos del partido:</strong> ").append(totalVotos).append("</li>");
            resultado.append("</ul>");

        } else {
            resultado.append("<p>⚠️ **No se encontró ningún presidente** o no hay suficientes datos de votos/candidatos registrados.</p>");
        }
    }
    return nombrePartido;
}

//mira el estado de la eleccion
//obtener idSuario
    public String devuelveEstado() throws SQLException {
        String sql = "SELECT estado FROM eleccion";
String estado="";

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {

            this.resultado = ps.executeQuery();
            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {
                estado = this.resultado.getString("estado");

            }
        }

        return estado;
    }
    
    
        //mostrar todos los del censo
        public int verTodosCenso() throws SQLException {
        String sql = "SELECT * FROM persona_censo";
  int conteo=0;
    try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
        this.resultado = ps.executeQuery();

        while (this.resultado.next()) {
  
conteo++;

        }
    }

    return conteo;
}
        
        //total votos:
      
        public int verTodosVotos(int idLocalidad) throws SQLException {
            String sql = "SELECT ha_votado FROM usuario where  ";
        int voto = 0;
int conteo=0;
        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            this.resultado = ps.executeQuery();
            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {
                voto = this.resultado.getInt("ha_votado");
if(voto!=0){
   conteo++;
}
            }
        }

        return conteo;
}
        /*
        //obtener idSuario
    public int devuelveVotoUsuario(String dni) throws SQLException {
        String sql = "SELECT ha_votado FROM usuario WHERE dni = ?";
        int voto = 0;

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {

            ps.setString(1, dni);
            this.resultado = ps.executeQuery();
            // Recorremos el resultado y creamos objetos Persona
            while (this.resultado.next()) {
                voto = this.resultado.getInt("ha_votado");

            }
        }

        return voto;
    }
        */
    public void cerrarConexion() throws SQLException {

        this.sentencia.close();
        this.conexion.close();
    }

}
