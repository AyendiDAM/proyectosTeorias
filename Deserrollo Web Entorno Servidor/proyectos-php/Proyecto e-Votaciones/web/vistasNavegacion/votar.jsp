<%-- 
 Formulario para poder votar
2.   Votar. Apertura escrutinio. 
• Apertura de Votaciones. El administrador debe poner Escrutinio=”Abierto”. 
• Los votantes ya pueden ejercer el voto, se acreditan al entrar con DNI y password y eligen el 
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


    
    Usuario usuarioVotar=(Usuario) session.getAttribute("votar");
   


  ConexionBBDD bbdd =null;
ArrayList<Partido_politico> todosP=null;
    try {
           bbdd = new ConexionBBDD();
if(usuarioVotar.getVoto()==0){
         todosP=bbdd.verTodoPartido();
             //
            bbdd.cerrarConexion();

}
   
            
        
             
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
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejercicio del Voto</title>
</head>
<body bgcolor="cyan">
    <center>
        <form action="../GestionVotoServlet" method="post">
             <h2>Selección de Partido</h2>
            
            <p>Seleccione el partido político de su elección:</p>
            
    
 
            
                    <table>
             
                        
                        <% if(usuarioVotar.getVoto()==0){ %>
            
            <% for (Partido_politico partido : todosP) { %>
     
                <tr>
                     <td>
                         <input type="radio" name="partidoElegido" value="<%= partido.getSiglas()%>" required><%= partido.getSiglas()%></input>
                    
                        
                   </td>
                 
                </tr>
            <% }
          session.setAttribute("votoUsuario", usuarioVotar.getVoto());
               session.setAttribute("idUsuarioVotado", usuarioVotar.getDni());
            %>
            
        </table>
            
            <input type="submit" name="votar" value="Emitir Voto">
        </form>
            <% } else { %>

<p>Ya has votado</p>

<% } %>
    </center>
</body>
</html>