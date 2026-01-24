<%-- 
    Document   : index
    Created on : 4 nov 2025, 13:21:47
    Author     : ayend
--%>


<%@page import="java.net.URLDecoder"%>
<%!String nombre,nombCodificado;%>
<%!Cookie[] cookies;
%>


<% cookies=request.getCookies();
for(int i=0;i<cookies.length;i++){
    Cookie una=cookies[i];
    if(una.getName().equalsIgnoreCase("nombre_despedida")){
        nombre=una.getValue();
      nombCodificado=URLDecoder.decode(nombre, "UTF-8");
    }

}


%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>e‑Votaciones | Inicio</title>
  <link rel="stylesheet" href="styles.css">
  <style>
    body {
      font-family: 'Inter', sans-serif;
      margin: 0;
      background-color: #f7f9fc;
      color: #222;
    }
    header {
      background-color: #0A3D62;
      color: white;
      padding: 1rem 2rem;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    header h1 {
      margin: 0;
      font-size: 1.5rem;
    }
    nav a {
      color: white;
      text-decoration: none;
      margin-left: 1.5rem;
      font-weight: 500;
    }
    .hero {
      text-align: center;
      padding: 5rem 2rem;
      background: linear-gradient(to bottom right, #0A3D62, #2E86DE);
      color: white;
    }
    .hero h2 {
      font-size: 2.5rem;
      margin-bottom: 1rem;
    }
    .hero p {
      font-size: 1.2rem;
      margin-bottom: 2rem;
    }
    .btn-group a {
      background-color: #fff;
      color: #0A3D62;
      padding: 0.8rem 1.5rem;
      margin: 0 0.5rem;
      border-radius: 8px;
      text-decoration: none;
      font-weight: bold;
      transition: background 0.3s;
    }
    .btn-group a:hover {
      background-color: #e8f0fe;
    }
    section.features {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      padding: 3rem 2rem;
      gap: 2rem;
    }
    .card {
      background: white;
      border-radius: 12px;
      box-shadow: 0 4px 10px rgba(0,0,0,0.1);
      padding: 2rem;
      width: 300px;
      text-align: center;
    }
    .card h3 {
      color: #0A3D62;
    }
    footer {
      background-color: #0A3D62;
      color: white;
      text-align: center;
      padding: 1rem;
      font-size: 0.9rem;
      margin-top: 3rem;
    }
  </style>
</head>
<body>
  <header>
    <h1>e‑Votaciones</h1>
    <div class="right">Bienvenido, <strong><%=nombCodificado%></strong> — <a href="login.jsp" style="color:#fff;text-decoration:underline">Salir</a></div>
    <nav>
      <a href="#">Inicio</a>
      <a href="vistasNavegacion/login.jsp">Iniciar sesión</a>
      <a href="vistasNavegacion/registroVotante.jsp">Registrarse</a>
    </nav>
  </header>

  <section class="hero">
    <h2>Bienvenido a e‑Votaciones</h2>
    <p>Tu plataforma segura para participar en las elecciones simuladas 2025.</p>
    <div class="btn-group">
      <a href="vistasNavegacion/login.jsp">Iniciar sesión</a>
      <a href="vistasNavegacion/registroVotante.jsp">Registrarse</a>
    </div>
  </section>

  <section class="features">
    <div class="card">
      <h3>Participa</h3>
      <p>Regístrate, verifica tu censo y ejerce tu voto de forma segura y transparente.</p>
    </div>
    <div class="card">
      <h3>Administra</h3>
      <p>Los administradores pueden crear elecciones, abrir y cerrar escrutinios fácilmente.</p>
    </div>
    <div class="card">
      <h3>Consulta Resultados</h3>
      <p>Visualiza los resultados por comunidad, localidad o totales de forma clara y rápida.</p>
    </div>
  </section>

  <footer>
    <p>© 2025 Proyecto e‑Votaciones — Desarrollo Web Entorno Servidor</p>
  </footer>
</body>
</html>

