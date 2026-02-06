<%-- 
    Document   : votosResultadosComunidades
    Created on : 29 nov 2025, 11:43:48
    Author     : ayend
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelos.Comunidad_autonoma"%>
<%@page import="dao.ConexionBBDD"%>
<%
    // Inicialización a null
    ConexionBBDD bbdd = null;
    ArrayList<Comunidad_autonoma> todosP = null;

    try {
        bbdd = new ConexionBBDD();
        // Llama al método que devuelve la lista de Comunidades_autonomas con el conteo de votos
        todosP = bbdd.verTodoVotoComunidad();
        bbdd.cerrarConexion();
    } catch (ClassNotFoundException e1) {
        session.setAttribute("Mensaje error", e1.getMessage());
        response.sendRedirect("errorUsuario/notificaciones.jsp");
    } catch (SQLException e2) {
        session.setAttribute("Mensaje error", e2.getMessage());
        response.sendRedirect("errorUsuario/notificaciones.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultados de Votación por Comunidad Autónoma</title>
        <style>
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <h1>🗳️ Resultados por Comunidad Autónoma</h1>

        <% if (todosP != null && !todosP.isEmpty()) {%>
            <table>
                <thead>
                    <tr>
                        <th>ID Comunidad</th>
                        <th>Nombre de la Comunidad</th>
                        <th>Total de Votos (Count)</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Iteración sobre la lista de Comunidades_autonomas
                        for (Comunidad_autonoma ca : todosP) {
                            // **NOTA IMPORTANTE:** Como tu DAO devuelve objetos Comunidad_autonoma 
                            // pero la consulta SQL devuelve el COUNT(V.id_voto), estás perdiendo 
                            // el valor del conteo. Se asume que has modificado la clase 
                            // Comunidad_autonoma para almacenar temporalmente el conteo de votos.
                            // Si no puedes modificar la clase, solo podrás mostrar el ID y el Nombre.
                            
                            // Si la clase Comunidad_autonoma solo tiene id y nombre:
                            // long totalVotos = ca.getTotalVotos(); // Esto es necesario pero no existe en el POJO estándar.
                    %>
                    <tr>
                        <td><%= ca.getId_comunidad() %></td>
                        <td><%= ca.getNombre() %></td>
                        <%-- Si pudieras modificar el POJO, aquí iría el conteo: <td><%= ca.getTotalVotos() %></td> --%>
                        <td>(Conteo no disponible en el POJO actual)</td> 
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p>No se encontraron resultados de votos por comunidad.</p>
        <% } %>
    </body>
</html>