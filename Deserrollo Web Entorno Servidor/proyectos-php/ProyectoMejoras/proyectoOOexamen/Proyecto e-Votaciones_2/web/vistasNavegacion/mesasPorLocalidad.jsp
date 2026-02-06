<%-- 
    Document   : mesasPorLocalidad
    Created on : 5 feb 2026, 12:27:21
    Author     : ayend
--%>

<%@page import="modelos.Eleccion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ConexionBBDD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

ArrayList<String> mesaPorLocalidad=null;
    try {
      ConexionBBDD bbdd = new ConexionBBDD();
            
             
           // bbdd.cerrarConexion();

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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultados Oficiales</title>
        <style>
            body {
    font-family: 'Segoe UI', sans-serif;
    background-color: #f4f4f4;
    text-align: center;
    }
            h1, h2 {
    color: #333;
    }

            /* Contenedores */
            .seccion {
    background: white;
    width: 80%;
    margin: 20px auto;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }

            /* Tablas */
            table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
    }
            th, td {
    border: 1px solid #ddd;
    padding: 12px;
    }
            th {
    background-color: #007bff;
    color: white;
    }
            tr:nth-child(even) {
    background-color: #f9f9f9;
    }

            /* Tarjeta Ganador */
            .ganador-box {
    border: 2px solid gold;
    background-color: #fffbe6;
    padding: 20px;
    margin-bottom: 20px;
    border-radius: 10px;
    }
            .ganador-titulo {
    color: goldenrod;
    font-weight: bold;
    font-size: 1.2em;
    }
        </style>
    </head>
    <body>
        <h1>📊 Escrutinio Final de Elecciones 📊</h1>
        <select name="localidadMesa">
            
            
            <input type="type" name="name"> </input>
            
            
        </select>
        
        <a href="../home.jsp" style="padding:10px; background:#333; color:white; text-decoration:none; border-radius:5px;">Volver al Panel</a>
        <br><br>
    </body>
</html>