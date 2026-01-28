<%-- 
para mostrar el mensaje de error, que se produce cuando se crea la bbdd.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String mensajeError=(String)session.getAttribute("Mensaje error");
    %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Ha ocurrido el siguiente error</h1>
        <br><!-- salto de linea -->
        <h2><%=mensajeError%></h2>
    </body>
</html>
