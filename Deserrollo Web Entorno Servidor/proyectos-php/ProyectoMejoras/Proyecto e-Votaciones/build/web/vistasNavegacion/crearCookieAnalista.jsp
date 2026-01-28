<%-- 
    Document   : crearCookieAnalista
    Created on : 2 dic 2025, 12:38:19
    Author     : ayend
--%><%@page import="java.sql.SQLException"%>
<%@page import="dao.ConexionBBDD"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

  String nombrePartidoGanador="";
    try {
      ConexionBBDD bbdd = new ConexionBBDD();

  nombrePartidoGanador =bbdd.obtenerPresidentePartido();
             //
            bbdd.cerrarConexion();


   
            
        
             
        } catch (ClassNotFoundException e1) {
        session.setAttribute("Mensaje error", e1.getMessage());
        response.sendRedirect("errorUsuario/notificaciones.jsp");
    } catch (SQLException e2) {
        session.setAttribute("Mensaje error", e2.getMessage());
        response.sendRedirect("errorUsuario/notificaciones.jsp");
    } 
    %>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body bgcolor="blue">
    <center>
        <form action="../ServletCrearCookie">
        <br>
        <br>
        <br>
        <h1>Partido ganador</h1> <br>
        <br>
        <br><!-- < -->
        <label>nombre</label>
        <input type="text" name="nombre" value="<%=nombrePartidoGanador%>">
       
        <input type="submit" name="boton" value="Crear cookie"/>
        </form>
        </center>
    </body>
</html>

