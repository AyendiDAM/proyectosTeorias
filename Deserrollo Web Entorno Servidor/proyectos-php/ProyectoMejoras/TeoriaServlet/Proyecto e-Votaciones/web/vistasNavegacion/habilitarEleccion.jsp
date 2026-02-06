<%-- 
    Document   : habilitarEleccion
    Created on : 27 nov 2025, 9:43:23
    Author     : ayend
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="dao.ConexionBBDD"%>
<%@page import="modelos.Eleccion"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%


    
  
   


  ConexionBBDD bbdd =null;
ArrayList<Eleccion> todosElecciones=null;
    try {
           bbdd = new ConexionBBDD();

         todosElecciones=bbdd.eleccionesTodas();
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
    <meta charset="UTF-8">
    <title>Habilitar / Deshabilitar Elecciones</title>

    <style>
        .card {
            border: 1px solid #ddd;
            padding: 12px;
            margin: 8px;
            border-radius: 6px;
            cursor: pointer;
            width: 300px;
        }
        .estado {
            font-size: 14px;
            color: gray;
        }
        .btnCambiar {
            margin-top: 6px;
            padding: 6px 10px;
            background: royalblue;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
    </style>

</head>
<body>

<h2>Habilitar / Deshabilitar Elecciones</h2>

<div class="alumnos-container">
    <%
        if (todosElecciones != null && !todosElecciones.isEmpty()) {
            for (Eleccion e : todosElecciones) {
    %>

        <div class="card">
            <strong><%= e.getNombre() %></strong><br>
            <span class="estado">
                Estado actual: 
                <%= e.getEstado()%>
            </span>

       <form method="post" action="../AbrirEleccionServlet">
    <input type="hidden" name="nombreEleccion" value="<%= e.getNombre()%>">

       <select id="eleccion" name="eleccion" required>
                <option value="CERRADA">CERRAR</option>
              <option value="ABIERTA">ABIERTA</option>

            </select><br><br>
         
      <input type="submit" name="partidoAlta" value="CAMBIAR ESTADO">
</form>

        </div>

    <%
            }
        } else {
    %>
        <p>No hay elecciones disponibles.</p>
    <%
        }
    %>
</div>

</body>
</html>