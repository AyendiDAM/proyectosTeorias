<%-- 
 Formulario para dar de alta candidato
--%>

<%@page import="modelos.Partido_politico"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ConexionBBDD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestión de Candidatos</title>
    </head>
    <body>
        <h2>Alta de Candidatos</h2>
        
        <% 
            String mensaje = (String) request.getAttribute("mensaje");
            String error = (String) request.getAttribute("error");
            if(mensaje != null) { %><h3 style="color:green"><%= mensaje %></h3><% } 
            if(error != null) { %><h3 style="color:red"><%= error %></h3><% } 
        %>

        <form action="../GestionCandidatoServlet" method="POST">
            
            <label>DNI del Candidato (Debe estar en el Censo):</label>
            <input type="text" name="dni" required placeholder="Ej: 11111111A"> 
            <br><br>
            
            <label>Partido Político:</label>
            <select name="id_partido" required>
                <% 
                    // Cargar partidos dinámicamente para el desplegable
                    ConexionBBDD con = new ConexionBBDD();
                
                    ArrayList<Partido_politico> listaPartidos = con.obtenerPartidos();
                    
                    for(Partido_politico p : listaPartidos) {
                %>
                    <option value="<%= p.getId()%>">
                        <%= p.getNombre() %> (<%= p.getSiglas() %>)
                    </option>
                <% } %>
            </select>
            <br><br>
            
            <label>Orden en la lista:</label>
            <input type="number" name="orden" min="1" required placeholder="1 = Presidente">
            <br><small>Nota: El orden 1 será el candidato a Presidente.</small>
            <br><br>
            
            <button type="submit">Guardar Candidato</button>
        </form>
        
        <br>
        <a href="tablaAdmin.jsp">Volver al Panel</a>
    </body>
</html>