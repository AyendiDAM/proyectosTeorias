<%-- 
    Document   : MinimoPartidosPoliticosExcepcion
    Created on : 2 dic 2025, 10:48:09
    Author     : ayend
--%>
<%@page import="modelos.ExcepcionMinimoPartidos"%>
<%
    //
    // ArrayList<Partido_politico>  partidosTotales=(ArrayList<Partido_politico> ) session.getAttribute("Todo correcto paritodos");
    ExcepcionMinimoPartidos pm=(ExcepcionMinimoPartidos)session.getAttribute("Insuficiente partidos");
    %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>hola<%pm.getMessage();%></h1>
        <nav>
    <a href="../vistasNavegacion/gestionPartido.jsp">Crear mas partidos</a>

</nav>

    </body>
</html>
