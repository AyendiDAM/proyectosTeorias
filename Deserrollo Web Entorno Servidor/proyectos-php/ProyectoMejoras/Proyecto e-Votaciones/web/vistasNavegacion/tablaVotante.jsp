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
<%!Cookie[] cookies;
%>



<% cookies=request.getCookies();
for(int i=0;i<cookies.length;i++){
    Cookie una=cookies[i];
    if(una.getName().equalsIgnoreCase("nombre")){
        nombre=una.getValue();
      nombCodificado=URLDecoder.decode(nombre, "UTF-8");
    }

}


%>
<!doctype html>
<html lang="es">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1" />
<title>Dashboard Votante | e‑Votaciones</title>
<style>
:root{--bg:#f4f7fb;--card:#fff;--primary:#0A3D62;--accent:#2E86DE;--muted:#6b7280;--success:#27ae60}
*{box-sizing:border-box}
body{margin:0;font-family:Inter, system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial;background:var(--bg);color:#111}
header{background:var(--primary);color:#fff;padding:0.9rem 1.25rem;display:flex;justify-content:space-between;align-items:center}
header .brand{font-weight:700}
header .right{font-size:0.95rem}
.wrap{max-width:1100px;margin:1.5rem auto;padding:0 1rem}
.intro{display:flex;justify-content:space-between;align-items:center;gap:1rem}
.profile{background:var(--card);padding:0.8rem;border-radius:10px;box-shadow:0 6px 18px rgba(10,61,98,0.06)}
.profile small{display:block;color:var(--muted)}
.search{flex:1;margin-left:1rem}
.search input{width:100%;padding:0.6rem;border-radius:8px;border:1px solid #ddd}


.sections{display:grid;grid-template-columns:1fr 360px;gap:1rem;margin-top:1rem}
@media (max-width:940px){.sections{grid-template-columns:1fr}}


.elections{display:flex;flex-direction:column;gap:1rem}
.election-card{background:var(--card);padding:1rem;border-radius:12px;box-shadow:0 6px 18px rgba(0,0,0,0.06);display:flex;justify-content:space-between;align-items:center}
.election-info h3{margin:0;color:var(--primary)}
.meta{color:var(--muted);font-size:0.92rem}
.badge{padding:6px 10px;border-radius:999px;font-weight:700;font-size:0.85rem}
.badge.abierta{background:var(--success);color:#fff}
.badge.cerrada{background:#e67e22;color:#fff}
.badge.finalizada{background:#7f8c8d;color:#fff}
.actions{display:flex;gap:0.5rem}
.btn{padding:0.6rem 0.9rem;border-radius:8px;border:none;cursor:pointer;font-weight:700}
.btn.primary{background:var(--primary);color:#fff}
.btn.ghost{background:transparent;border:1px solid #ddd}


.side-panel{background:var(--card);padding:1rem;border-radius:12px;box-shadow:0 6px 18px rgba(0,0,0,0.06)}
.side-panel h4{margin:0 0 0.5rem 0;color:var(--primary)}
.side-item{display:flex;justify-content:space-between;align-items:center;padding:0.5rem 0;border-bottom:1px solid #f1f3f5}
.side-item:last-child{border-bottom:none}
.help{font-size:0.9rem;color:var(--muted);margin-top:0.75rem}


footer{max-width:1100px;margin:1.5rem auto;padding:0 1rem 2rem 1rem;color:var(--muted)}
</style>
</head>
<body>
<header>
<div class="brand">e‑Votaciones</div>
<div class="right">Bienvenido, <strong><%=nombCodificado%></strong> — <a href="login.jsp" style="color:#fff;text-decoration:underline">Salir</a></div>
</header>


<main class="wrap">
    
    <aside>
<h2>e‑Votaciones</h2>
<nav>
    <a href="votar.jsp">Ir a votar</a><br>
        <a href="verPresidente.jsp">Ver presidente</a>

  
</nav>
</aside>
<div class="intro">
<div class="profile">
    <strong>Usuario conectado: <%=nombCodificado%></strong>
</div>
<div class="search">
<input type="search" placeholder="Buscar elecciones por nombre...">
</div>
</div>


<div class="sections">
<section>
<h2 style="color:var(--primary);margin-top:0.6rem">Mis elecciones</h2>
<div class="elections">


<article class="election-card">
        <form action="../EleccionVotarControlador" method="POST">
<div class="election-info">
<h3>Elecciones Generales 2025</h3>
<p class="meta">Periodo: 10 Nov 2025 — 17 Nov 2025 · Ámbito: Nacional</p>
<p class="meta">Descripción: Elección nacional de carácter simulado.</p>
</div>
<div style="text-align:right">
<div class="badge abierta">Abierta</div>
<div class="actions" style="margin-top:0.6rem">
    
<button type="submit" class="btn primary">Votar ahora</button>
<button  class="btn ghost">Info</button>
</div>
</div>
        </form>

</article>

<article class="election-card">
<div class="election-info">
<h3>Elecciones Regionales 2024</h3>
</html>