<%-- 
    Document   : votosResultadosLocalidades
    Created on : 29 nov 2025, 11:44:00
    Author     : ayend
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="modelos.Localidad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ConexionBBDD"%>
<%

    ConexionBBDD bbdd = null;
    ArrayList<Localidad> todosP = null;

    try {
        bbdd = new ConexionBBDD();

        todosP = bbdd.verTodoVotoLocalidad();
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
        <title>Resultados de Votación por Localidad</title>
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
        <h1>🏘️ Resultados por Localidad</h1>

        <% if (todosP != null && !todosP.isEmpty()) {%>
            <table>
                <thead>
                    <tr>
                        <th>ID Localidad</th>
                        <th>Nombre de la Localidad</th>
                        <th>ID Comunidad</th>
                        <th>Total de Votos (Count)</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Iteración sobre la lista de objetos Localidad
                        for (Localidad loc : todosP) {
                            // **NOTA IMPORTANTE:** Al igual que con Comunidad_autonoma, tu DAO 
                            // devuelve objetos Localidad, pero la consulta SQL incluye el 
                            // COUNT(V.id_voto). Sin una clase DTO o un campo 'totalVotos' 
                            // en el POJO Localidad, este valor no se puede mostrar.
                            
                            // Si tu clase Localidad ha sido modificada, usa el getter del total.
                            // Si no, solo se muestran los campos base (id, nombre, id_comunidad).
                    %>
                    <tr>
                        <td><%= loc.getId_localidad() %></td>
                        <td><%= loc.getNombre() %></td>
                        <td><%= loc.getId_comunidad() %></td>
                        <%-- Si pudieras modificar el POJO, aquí iría el conteo: <td><%= loc.getTotalVotos() %></td> --%>
                        <td>(Conteo no disponible en el POJO actual)</td> 
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p>No se encontraron resultados de votos por localidad.</p>
        <% } %>
    </body>
</html>