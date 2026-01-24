<%-- 
    Document   : eleccionFinalizada
    Created on : 29 nov 2025, 9:31:39
    Author     : ayend
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="dao.ConexionBBDD"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelos.Eleccion"%>
<%

boolean estado=false;
    

   


ArrayList<Eleccion> todasElecciones=null;
    try {
      ConexionBBDD bbdd = new ConexionBBDD();

         todasElecciones=bbdd.verTodoEleccionFinalizada();
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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
  <body>
    <center>
  
      <form  action="../GestionEleccionFinalizadaServlet">
            <div class="elecciones">
               <%
    if (todasElecciones != null && !todasElecciones.isEmpty()) {
        estado=true;
session.setAttribute("estado eleccion cookie", estado);
%>
        <table>
            <thead>
                <tr>
                    <th>Seleccionar</th>
                    <th>ID</th>
                    <th>Nombre de la Elección</th>
                    <th>Fecha Inicio</th>
                    <th>Fecha Fin</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
<%
                        for (Eleccion a : todasElecciones) {
%>
                <tr>
                    <td>
                        <input type="radio" name="idEleccionSeleccionada" value="<%= a.getId() %>" required>
                    </td>
                    <td><%= a.getId() %></td>
                    <td><%= a.getNombre() %></td>
                    <td><%= a.getFechaInicio() %></td>
                    <td><%= a.getFechaFinal()%></td>
                    <td><%= a.getEstado() %></td>
                </tr>
<%
                        }
%>
            </tbody>
        </table>
<%
    } else {
%>
        <p>⚠️ **No hay Elecciones Finalizadas** para mostrar resultados.</p>
<%
    }
%>
            </div>

 <input type="submit" name="elecciones" value="MostrarResultado">
        </form>

        
    </center>
</body>
</html>
