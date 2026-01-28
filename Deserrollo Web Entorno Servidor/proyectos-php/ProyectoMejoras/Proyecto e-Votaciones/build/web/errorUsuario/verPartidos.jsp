<%-- 
    Document   : verPartidos
    Created on : 2 dic 2025, 10:25:44
    Author     : ayend
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="modelos.Partido_politico"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
        
  ArrayList<Partido_politico>  partidosTotales=(ArrayList<Partido_politico> ) session.getAttribute("Todo correcto paritodos");
   

    %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Se puede votar a los siguientes partidos</h1>

        <% if (partidosTotales != null && !partidosTotales.isEmpty()) {%>
            <table>
                <thead>
                    <tr>
                        <th>ID </th>
                        <th>Nombre partido</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Iteración sobre la lista de Comunidades_autonomas
                        for (Partido_politico ca : partidosTotales) {
                    %>
                    <tr>
                        <td><%= ca.getSiglas()%></td>
                        <td><%= ca.getNombre() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p>No se encontraron resultados de votos por comunidad.</p>
        <% } %>
    </body>
</html>
