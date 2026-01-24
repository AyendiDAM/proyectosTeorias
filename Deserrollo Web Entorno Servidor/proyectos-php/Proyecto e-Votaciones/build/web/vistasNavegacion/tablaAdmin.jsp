<%-- 
    Document   : login
    Created on : 9 nov 2025, 7:36:36
    Author     : ayend
--%>

<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--recogemos las cookies--%>
<%!String nombre,nombCodificado;%>
<%!Cookie[] cookies;%>


<% cookies=request.getCookies();
for(int i=0;i<cookies.length;i++){
    Cookie una=cookies[i];
    if(una.getName().equalsIgnoreCase("nombre")){
        nombre=una.getValue();
      nombCodificado=URLDecoder.decode(nombre, "UTF-8");
    }

}

    String accioneAdmin = (String) session.getAttribute("PartidoCreado");
%>
<%
//cookies de despedidas

         String valorCodificado=URLEncoder.encode(nombre,"UTF-8");
        
          //creo la cookie para el nombre
             Cookie cNombre=new Cookie("nombre_despedida", valorCodificado);
             //tiempo de vida
             cNombre.setMaxAge(60*6);
             //guarda cookie para el cliente y dar repuesta
             response.addCookie(cNombre);



%>
<!doctype html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Admin — Dashboard | e-Votaciones</title>
<style>
:root{--bg:#f4f7fb;--card:#fff;--primary:#0A3D62;--muted:#6b7280}
*{box-sizing:border-box}
body{margin:0;font-family:Inter,system-ui,Arial;background:var(--bg);color:#111}
.app{display:flex;min-height:100vh}
aside{width:260px;background:#07243a;color:#fff;padding:1.25rem}
aside h2{margin:0 0 1rem 0}
nav a{display:block;color:#cfe8ff;text-decoration:none;padding:0.45rem 0;border-radius:6px}
nav a:hover{background:rgba(255,255,255,0.04)}
main{flex:1;padding:1.5rem}
header{display:flex;justify-content:space-between;align-items:center;margin-bottom:1rem}
.metrics{display:grid;grid-template-columns:repeat(auto-fit,minmax(200px,1fr));gap:1rem}
.metric{background:var(--card);padding:1rem;border-radius:10px;box-shadow:0 6px 18px rgba(10,61,98,0.06)}
table{width:100%;border-collapse:collapse;background:var(--card);border-radius:10px;overflow:hidden}
th,td{padding:0.75rem;text-align:left;border-bottom:1px solid #eee}
</style>
</head>
<body>
<div class="app">
<aside>
<h2>e‑Votaciones</h2>
<nav>
    <a href="gestionPartido.jsp">Partidos</a>
    <a href="gestionCandidatos.jsp">Candidatos</a>
    <a href="gestionElecciones.jsp">Elecciones</a>
    <a href="modificarEstadoEleccion.jsp">modificar Elecciones</a>
      <a href="cambiarRol.jsp">Cambiar rol a votante</a>
    <a href="controlEscrutinio.jsp">Control Escrutinio</a>
       <a href="habilitarEleccion.jsp">habilitar eleccion</a>
       <a href="eleccionFinalizada.jsp">ResultadoELecciones</a>
     <a href="salirAplicacion.jsp">Salir de la aplicacion</a>
  
<a href="">Censo</a>
<a href="#">Configuración</a>
</nav>
</aside>


<main>
<header>
<h1>Dashboard Administrador</h1>
<div>Admin: <strong><%=nombCodificado%></strong></div>
</header>


<section class="metrics">
<div class="metric">
<h3>Votantes registrados</h3>
<p style="font-size:1.5rem;font-weight:700;">1.254</p>
</div>
<div class="metric">
<h3>Elecciones activas</h3>
<p style="font-size:1.5rem;font-weight:700;">1</p>
</div>
<div class="metric">
<h3>Votos totales</h3>
<p style="font-size:1.5rem;font-weight:700;">3.482</p>
</div>
<div class="metric">
<h3>Estado escrutinio</h3>
<p style="font-size:1.2rem;font-weight:700;color:#27ae60;">Abierto</p>
</div>
</section>


<section style="margin-top:1.5rem">
<h2>Últimas acciones</h2>

<table>
    <% if (accioneAdmin != null) { %>
        <tr>
            <td><%= accioneAdmin.toString() %></td>
        </tr>
    <% } else { %>
        <tr>
            <td>No hay acciones registradas.</td>
        </tr>
    <% } %>
</table>

</section>

</main>
</div>
</body>
</html>