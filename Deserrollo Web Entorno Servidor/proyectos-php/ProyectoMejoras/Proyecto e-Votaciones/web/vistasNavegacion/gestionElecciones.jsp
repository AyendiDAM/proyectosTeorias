<%-- 
 Formulario para dar de alta elecciones
--%>

<%@page import="modelos.Eleccion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ConexionBBDD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestión de Elecciones</title>
        <style>
            /* Estilos básicos para que se vea ordenado */
            table {
width: 100%;
border-collapse: collapse;
margin-top: 20px;
}
            th, td {
border: 1px solid #ddd;
padding: 8px;
text-align: left;
}
            th {
background-color: #f2f2f2;
}
            .btn {
padding: 5px 10px;
color: white;
border: none;
cursor: pointer;
border-radius: 4px;
}
            .btn-abrir {
background-color: #28a745;
}
            .btn-fin {
background-color: #dc3545;
}
            .estado-abierta {
color: green;
font-weight: bold;
}
            .estado-cerrada {
color: orange;
font-weight: bold;
}
            .estado-finalizada {
color: grey;
font-weight: bold;
}
        </style>
    </head>
    <body>
        <h2>Panel de Control de Elecciones</h2>

        <% String msg = (String) request.getAttribute("mensaje");
            String err = (String) request.getAttribute("error"); %>

        <% if (msg != null) {%> <div style="color:green; border:1px solid green; padding:10px;"><%= msg%></div> <% } %>
        <% if (err != null) {%> <div style="color:red; border:1px solid red; padding:10px;"><%= err%></div> <% } %>

        <fieldset>
            <legend>Crear Nueva Elección</legend>
            <form action="../GestionEleccionServlet" method="POST">
                <label>Nombre Elección:</label>
                <input type="text" name="nombre" required placeholder="Ej: Generales 2026">

                <label>Fecha Inicio:</label>
                <input type="date" name="fecha_inicio" required>

                <label>Fecha Fin:</label>
                <input type="date" name="fecha_fin" required>

                <button type="submit">Crear Elección</button>
            </form>
        </fieldset>

        <h3>Historial</h3>
        <table>
            <tr>
                <th>ID</th> <th>Nombre</th> <th>Inicio</th> <th>Fin</th> <th>Estado</th> <th>Acciones</th>
            </tr>
            <%
                ConexionBBDD dao = new ConexionBBDD();
         
                ArrayList<Eleccion> lista = dao.obtenerElecciones();

                for (Eleccion e : lista) {
            %>
            <tr>
                <td><%= e.getId()%></td>
                <td><%= e.getNombre()%></td>
                <td><%= e.getFechaInicio()%></td>
                <td><%= e.getFechaFinal()%></td>

                <td>
                    <% if (e.getEstado().equals("ABIERTA")) { %> <span class="estado-abierta">ABIERTA</span>
                    <% } else if (e.getEstado().equals("CERRADA")) { %> <span class="estado-cerrada">CERRADA</span>
                    <% } else { %> <span class="estado-finalizada">FINALIZADA</span> <% } %>
                </td>

                <td>
                    <% if (e.getEstado().equals("CERRADA")) {%>
                    <form action="../GestionModificarEleccionServlet" method="POST">
                        <input type="hidden" name="id" value="<%= e.getId()%>">
                        <input type="hidden" name="accion" value="ABIERTA">
                        <button type="submit" class="btn btn-abrir">Abrir Escrutinio</button>
                    </form>
                    <% } else if (e.getEstado().equals("ABIERTA")) {%>
                    <form action="../ModificarEstadoEleccionServlet" method="POST">
                        <input type="hidden" name="id" value="<%= e.getId()%>">
                        <input type="hidden" name="accion" value="FINALIZADA">
                        <button type="submit" class="btn btn-fin">Finalizar Elección</button>
                    </form>
                    <% } else { %>
                    <a href="resultados.jsp">Ver Resultados</a>
                    <% } %>
                </td>
            </tr>
            <% }%>
        </table>

        <br>
        <a href="tablaAdmin.jsp">Volver al Menú Principal</a>
    </body>

</html>