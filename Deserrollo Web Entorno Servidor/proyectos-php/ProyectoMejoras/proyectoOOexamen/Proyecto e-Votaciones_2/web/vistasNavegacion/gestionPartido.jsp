<%-- 
 Formulario para dar de alta partidos políticos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestión de Partidos</title>
    </head>
    <body>
        <h2>Alta de Partido Político</h2>

        <% String mensaje = (String) request.getAttribute("mensaje"); %>
        <% if (mensaje != null) {%> <h3><%= mensaje%></h3> <% }%>

        <form action="../GestionPartidoAnadirServlet" method="POST" enctype="multipart/form-data">

            <label>Nombre del Partido:</label>
            <input type="text" name="nombre" required> <br><br>

            <label>Siglas:</label>
            <input type="text" name="siglas" required> <br><br>

            <label>Logo del Partido:</label>
            <input type="file" name="logo" required> <br><br>

            <button type="submit">Guardar Partido</button>
        </form>

        <br>
        <a href="tablaAdmin.jsp">Volver al Panel de Administración</a>
    </body>
</html>