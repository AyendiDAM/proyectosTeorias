<%-- 
    Document   : habilitarEleccion
    Created on : 27 nov 2025, 9:43:23
    Author     : ayend
--%>

<%@page import="modelos.Localidad"%>
<%@page import="java.sql.SQLException"%>
<%@page import="dao.ConexionBBDD"%>
<%@page import="modelos.Eleccion"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%


    
  
   


  ConexionBBDD bbdd =null;
ArrayList<Localidad> todosElecciones=null;
    try {
           bbdd = new ConexionBBDD();

         todosElecciones=bbdd.verTodasLocalidades();
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
    <h2>
       Buscar los candidatos de las localidades
       </h2>

<div class="alumnos-container">
         <form method="post" action="../VerCandidatosLocalidadServlet">
    <%
        if (todosElecciones != null && !todosElecciones.isEmpty()) {
            
    %>

    
         <select id="nombre" name="nombre" required>
    <%for (Localidad e : todosElecciones) {%>

       
                <option value="<%=e.getId_localidad()%>"><%=e.getNombre()%></option>
            
       
     
         


    <%
            }
        } else {
    %>
        <p>No hay candidatos disponibles.</p>
    <%
        }
    %>
          <input type="submit" name="partidoAlta" value="Buscar candidatos">
    </form>

      

</div>

</body>
</html>