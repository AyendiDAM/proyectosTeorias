<%-- 
 Formulario para finalizar escrutinio
--%>

<%@page import="modelos.Eleccion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ConexionBBDD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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

        <%
            try {
                ConexionBBDD dao = new ConexionBBDD();
  

                // 1. Buscar la última elección FINALIZADA
                ArrayList<Eleccion> todas = dao.obtenerElecciones();
                int idEleccion = -1;
                String nombreEleccion = "";

                for (Eleccion e : todas) {
                    if ("FINALIZADA".equalsIgnoreCase(e.getEstado())) {
                        idEleccion = e.getId();
                        nombreEleccion = e.getNombre();
                        break;
                    }
                }

                if (idEleccion != -1) {
                    // Obtenemos los 3 tipos de datos
                    ArrayList<String[]> totales = dao.obtenerResultados(idEleccion);
                    ArrayList<String[]> porComunidad = dao.obtenerResultadosPorComunidad(idEleccion);
                    ArrayList<String[]> porLocalidad = dao.obtenerResultadosPorLocalidad(idEleccion);
        %>

        <div class="seccion">
            <h2>🏆 Resultados Generales: <%= nombreEleccion%></h2>

            <% if (!totales.isEmpty()) {
                    String[] ganador = totales.get(0);
            %>
            <div class="ganador-box">
                <div class="ganador-titulo">PRESIDENTE DEL GOBIERNO</div>
                <h1><%= ganador[2]%></h1>
                <h3><%= ganador[0]%> (<%= ganador[1]%> votos)</h3>
                <img src="../imagenes/<%= ganador[3]%>" style="max-height: 80px;">
            </div>
            <% } %>

            <table>
                <tr><th>Logo</th><th>Partido</th><th>Candidato</th><th>Votos Totales</th></tr>
                        <% for (String[] f : totales) {%>
                <tr>
                    <td><img src="../imagenes/<%= f[3]%>" width="40"></td>
                    <td><%= f[0]%></td>
                    <td><%= f[2]%></td>
                    <td><strong><%= f[1]%></strong></td>
                </tr>
                <% } %>
            </table>
        </div>

        <div class="seccion">
            <h2>🌍 Votos por Comunidad Autónoma</h2>
            <table>
                <tr><th>Comunidad</th><th>Partido</th><th>Siglas</th><th>Votos</th></tr>
                        <% for (String[] f : porComunidad) {%>
                <tr>
                    <td style="font-weight: bold; color: #555;"><%= f[0]%></td>
                    <td><%= f[1]%></td>
                    <td><%= f[2]%></td>
                    <td><%= f[3]%></td>
                </tr>
                <% } %>
            </table>
        </div>

        <div class="seccion">
            <h2>🏘️ Votos por Localidad</h2>
            <table>
                <tr><th>Localidad</th><th>Partido</th><th>Siglas</th><th>Votos</th></tr>
                        <% for (String[] f : porLocalidad) {%>
                <tr>
                    <td style="font-weight: bold; color: #555;"><%= f[0]%></td>
                    <td><%= f[1]%></td>
                    <td><%= f[2]%></td>
                    <td><%= f[3]%></td>
                </tr>
                <% } %>
            </table>
        </div>

        <%
        } else {
        %>
        <h3 style="color:red; margin-top:50px;">No hay resultados disponibles. El administrador debe finalizar la elección primero.</h3>
        <%
                }
            } catch (Exception e) {
                out.print("<h3>Error técnico: " + e.getMessage() + "</h3>");
                e.printStackTrace();
            }
        %>

        <br>
        <a href="tablaAdmin.jsp" style="padding:10px; background:#333; color:white; text-decoration:none; border-radius:5px;">Volver al Panel</a>
        <br><br>
    </body>
</html>