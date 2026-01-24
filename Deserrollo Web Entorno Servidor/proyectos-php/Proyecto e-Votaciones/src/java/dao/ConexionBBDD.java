/*

Clase para realizar todas las operaciones con la bbdd
 */
package dao;

import com.sun.xml.registry.uddi.bindings_v2_2.Contact;
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
import java.util.List;
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
        this.usuario = "root";
        this.contraseña = "";
        this.url = "jdbc:mysql://localhost:3306/bbdd_votaciones_ayendi?useUnicode=true&characterEncoding=UTF-8";//no se copia la url como tal:
        //se pone el puerto del xammp y jdbc 

        //cargar el droier para que java y mysql se entienda
        Class.forName("com.mysql.cj.jdbc.Driver");

        //se obtiene una conexion con la base de datos
        this.conexion = DriverManager.getConnection(this.url, this.usuario, this.contraseña);

        //se cra un Statement,para realizar las acciones
        this.sentencia = this.conexion.createStatement();

    }

    //metodo para crear la tabla contactos
    public void creaTablaContactos() throws SQLException {
        //String orden = "DROP TABLE IF EXISTS contactos;";
        //this.sentencia.executeUpdate(orden);

        String orden = "CREATE TABLE IF NOT EXISTS contactos (id INT AUTO_INCREMENT, "
                + "PRIMARY KEY (id), nombre VARCHAR(40), telefono VARCHAR(20)); ";
        this.sentencia.executeUpdate(orden);
    }

    //metodo para insertar automáticamente dos contactos
    public void insertarContacto() throws SQLException {
        String orden = "INSERT INTO contactos values (NULL,'Inma','22 321 11');";
        this.sentencia.executeUpdate(orden);

    }

    public void eliminarContacto(String nombre) throws SQLException {
        String sql = "DELETE FROM contactos WHERE nombre = ?";

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, nombre); // Asigna el valor del parámetro
            ps.executeUpdate();      // Ejecuta la eliminación
        }
    }

    /* public int altaAlumno(Contacto nuevo) throws SQLException{
        String orden="INSERT INTO contactos values (NULL,'"+nuevo.getNombre()+"','"+nuevo.getTelefono()+"');";
        int filas=this.sentencia.executeUpdate(orden);
        return filas;
    }*/
    public ArrayList<String> getNombreCOntacto() throws SQLException {
        String orden = "SELECT nombre From contactos";
        ArrayList<String> nombre = new ArrayList<>();
        this.resultado = this.sentencia.executeQuery(orden);
        while (this.resultado.next()) {
            String guardaNombre = this.resultado.getString("nombre");
            nombre.add(guardaNombre);
        }

        return nombre;

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

    public ArrayList<String> todoContacto() throws SQLException, ClassNotFoundException {
        ArrayList<String> lista = new ArrayList<>();
        String sql = "SELECT nombre, telefono FROM contactos";
        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("telefono");
                lista.add(nombreCompleto);
            }
        }
        return lista;
    }

    public String buscarTelefono(String nombre) throws SQLException {
        String sql = "SELECT telefono FROM contactos WHERE nombre = ?";
        String telefono = "";

        try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
            ps.setString(1, nombre); // ← asignamos el parámetro
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    telefono = rs.getString("telefono");
                }
            }
        }

        return telefono;
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
