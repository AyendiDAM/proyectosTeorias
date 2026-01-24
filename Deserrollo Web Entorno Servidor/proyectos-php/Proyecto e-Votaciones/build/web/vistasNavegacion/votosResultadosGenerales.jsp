<%-- 
    Document   : votosResultadosGenerales
    Created on : 29 nov 2025, 11:43:34
    Author     : ayend
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="modelos.Voto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ConexionBBDD"%>
<%
    // Inicialización a null
    ConexionBBDD bbdd = null;
    ArrayList<Voto> todosP = null;

    try {
        bbdd = new ConexionBBDD();
        // Llama al método que devuelve la lista de Votos (según tu código DAO modificado)
        todosP = bbdd.verTodoVotos();
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
        <title>Resultados Generales (Votos)</title>
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
        <h1>📊 Resultados Generales de Votación</h1>

        <% if (todosP != null && !todosP.isEmpty()) {%>
            <table>
                <thead>
                    <tr>
                        <th>ID Voto</th>
                        <th>ID Localidad</th>
                        <th>ID Partido</th>
                        <th>ID Elección</th>
                        <th>Fecha del Voto</th>
                        <%-- NOTA: Los nombres de Comunidad/Localidad y el conteo (total_votos) 
                             NO están disponibles si solo se usa el POJO 'Voto'. --%>
                        <th>ID Voto (de la Fila Agrupada)</th> 
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Iteración sobre la lista de objetos Voto
                        for (Voto voto : todosP) {
                    %>
                    <tr>
                        <td><%= voto.getId_voto() %></td>
                        <td><%= voto.getId_localidad() %></td>
                        <td><%= voto.getId_partido() %></td>
                        <td><%= voto.getId_eleccion() %></td>
                        <td><%= voto.getFecha() %></td>
                        <%-- Aquí debería ir el conteo. Si el GROUP BY en el DAO es por fila de Voto, 
                             esta columna simplemente repetirá el ID de Voto y no será útil para el conteo.
                             Si hubieras usado un DTO, aquí iría el total de votos. --%>
                        <td><%= voto.getId_voto() %></td> 
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p>No se encontraron resultados de votos.</p>
        <% } %>
    </body>
</html>
