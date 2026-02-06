<%-- 
    Document : verPresidente
    Created on : 1 dic 2025
    Author : Gemini
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="dao.ConexionBBDD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Variable para almacenar el resultado HTML devuelto por el DAO
    String resultadoPresidente = "";
    ConexionBBDD bbdd = null;

    try {
        // 1. Inicializar la conexión
        bbdd = new ConexionBBDD();
        

        resultadoPresidente = bbdd.obtenerPresidente();

        // 3. Cerrar la conexión
        bbdd.cerrarConexion();
        
    } catch (ClassNotFoundException e1) {
        // Manejo de error si no se encuentra el driver JDBC
        resultadoPresidente = "<p style='color:red;'>ERROR: No se encontró el controlador de la base de datos. Mensaje: " + e1.getMessage() + "</p>";
        // Si usas el sistema de notificaciones, puedes descomentar estas líneas:
        // session.setAttribute("Mensaje error", e1.getMessage());
        // response.sendRedirect("errorUsuario/notificaciones.jsp");
    } catch (SQLException e2) {
        // Manejo de error de SQL
        resultadoPresidente = "<p style='color:red;'>ERROR de SQL al consultar los votos. Mensaje: " + e2.getMessage() + "</p>";
        // Si usas el sistema de notificaciones, puedes descomentar estas líneas:

    } finally {
       
        if (bbdd != null) {
            try {
                bbdd.cerrarConexion();
            } catch (SQLException e) {
       
            }
        }
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultados Electorales: Presidente</title>
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; }
            h1 { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
            .resultado { border: 1px solid #3498db; padding: 15px; border-radius: 5px; background-color: #ecf0f1; }
            ul { list-style: none; padding: 0; }
            li { margin-bottom: 8px; }
            strong { color: #e74c3c; }
        </style>
    </head>
    <body>
        <h1>🏆 Resultados de las Elecciones Presidenciales</h1>
        
        <div class="resultado">
            <%
                // Imprime el contenido HTML que se generó en el bloque Java.
                // Si la consulta fue exitosa, mostrará el nombre del presidente.
                // Si falló, mostrará el mensaje de error.
                out.print(resultadoPresidente);
            %>
        </div>
        
    </body>
</html>