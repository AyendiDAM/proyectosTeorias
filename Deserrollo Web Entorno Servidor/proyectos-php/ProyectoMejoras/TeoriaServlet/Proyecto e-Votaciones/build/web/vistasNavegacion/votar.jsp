<%-- 
 Formulario para poder votar
2.   Votar. Apertura escrutinio. 
? Apertura de Votaciones. El administrador debe poner Escrutinio=?Abierto?. 
? Los votantes ya pueden ejercer el voto, se acreditan al entrar con DNI y password y eligen el 
partido. Debemos controlar que no ha votado. Si el proceso es correcto, se informa al votante 
de tal eventualidad  y se registra que ya ha votado. 
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="dao.ConexionBBDD"%>
<%@page import="modelos.Partido_politico"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelos.Usuario"%>
<%
    // VALIDACIÓN DE SEGURIDAD
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Votación Electrónica</title>
        <style>
            .tarjeta-partido {
                border: 1px solid #ccc;
padding: 10px;
margin: 10px;
                display: inline-block;
width: 200px;
text-align: center;
                border-radius: 8px;
box-shadow: 2px 2px 5px #aaa;
            }
            .tarjeta-partido img {
max-width: 100px;
height: auto;
}
            .btn-votar {
                background-color: #007bff;
color: white;
padding: 10px 20px;
                text-decoration: none;
border-radius: 5px;
display: block;
margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <h1>Bienvenido, <%= usuario.getNombre()%></h1>
        <h2>Elija su papeleta</h2>

        <% String msg = (String) request.getAttribute("mensaje");
            String err = (String) request.getAttribute("error"); %>
        <% if (msg != null) {%><h2 style="color:green"><%= msg%></h2><% } %>
        <% if (err != null) {%><h2 style="color:red"><%= err%></h2><% } %>

        <% if (msg == null) { // Si ya votó (msg éxito), ocultamos las papeletas %>
        <div id="contenedor-papeletas">
            <% 
                ConexionBBDD pDao = new ConexionBBDD();
              
                ArrayList<Partido_politico> partidos = pDao.obtenerPartidos();

                for (Partido_politico p : partidos) {
            %>
            <div class="tarjeta-partido">
                <img src="../imagenes/<%= p.getLogo()%>" alt="Logo <%= p.getSiglas()%>">
                <h3><%= p.getNombre()%></h3>

                <form action="../GestionVotoServlet" method="POST">
                    <input type="hidden" name="id_partido" value="<%= p.getId()%>">
                    <button type="submit" class="btn-votar">Votar a <%= p.getSiglas()%></button>
                </form>
            </div>
            <% } %>
        </div>
        <% }%>

        <br><br>
        <a href="logout.jsp">Cerrar Sesión</a>
    </body>
</html>