<%-- 
    Document   : login
    Created on : 9 nov 2025, 7:36:36
    Author     : ayend
--%>

<%@page import="modelos.Partido_politico"%>
<%@page import="modelos.Eleccion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ConexionBBDD"%>
<%@page import="modelos.Usuario"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // 1. SEGURIDAD: VERIFICAR QUE SEA ADMIN
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

    // Si no existe o su rol NO es ADMIN, fuera
    if (usuario == null
            || (usuario.getRol() != null && !usuario.getRol().equalsIgnoreCase("ADMIN") && !usuario.getRol().equalsIgnoreCase("ADMINISTRADOR"))) {
        response.sendRedirect("../login.jsp");
        return;
    }

    // 2. DATOS PARA EL DASHBOARD
    ConexionBBDD eDao = new ConexionBBDD();


    // Obtenemos listas para contar
    ArrayList<Eleccion> listaElecciones = eDao.obtenerElecciones();
    ArrayList<Partido_politico> listaPartidos = eDao.obtenerPartidos();

    int numElecciones = listaElecciones.size();
    int numPartidos = listaPartidos.size();
    int activas = 0;

    for (Eleccion e : listaElecciones) {
        if (e.getEstado().equals("ABIERTA")) {
            activas++;
        }
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Administración | e‑Votaciones</title>
        <style>
            :root {
    --bg: #f8f9fa;
    --sidebar-bg: #1e293b;
    --sidebar-text: #e2e8f0;
    --primary: #0f172a;
    --accent: #3b82f6;
    --text-main: #334155;
    --card-bg: #ffffff;
    --success: #10b981;
    --danger: #ef4444;
    --shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
    }

            * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    }
            body {
    font-family: 'Segoe UI', system-ui, sans-serif;
    background: var(--bg);
    color: var(--text-main);
    height: 100vh;
    display: flex;
    overflow: hidden;
    }

            /* SIDEBAR OSCURO (Diferenciador del panel votante) */
            aside {
    width: 260px;
    background: var(--sidebar-bg);
    color: var(--sidebar-text);
    display: flex;
    flex-direction: column;
    padding: 1.5rem;
    flex-shrink: 0;
    }
            aside .brand {
    font-size: 1.4rem;
    font-weight: 800;
    color: white;
    margin-bottom: 2rem;
    display: flex;
    align-items: center;
    gap: 0.8rem;
    }
            aside nav {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    }
            aside nav a {
    text-decoration: none;
    color: #94a3b8;
    padding: 0.75rem 1rem;
    border-radius: 8px;
    font-weight: 500;
    transition: all 0.2s;
    display: flex;
    align-items: center;
    gap: 0.75rem;
    }
            aside nav a:hover, aside nav a.active {
    background: #334155;
    color: white;
    }
            aside nav a.active {
    border-left: 4px solid var(--accent);
    }

            aside .admin-info {
    margin-top: auto;
    padding-top: 1rem;
    border-top: 1px solid #334155;
    display: flex;
    align-items: center;
    gap: 0.8rem;
    }
            aside .avatar {
    width: 40px;
    height: 40px;
    background: var(--accent);
    color: white;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
    }

            /* MAIN CONTENT */
            main {
    flex: 1;
    overflow-y: auto;
    padding: 2rem;
    }
            header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 2rem;
    }
            h1 {
    font-size: 1.8rem;
    color: var(--primary);
    font-weight: 700;
    }
            .subtitle {
    color: #64748b;
    font-size: 0.95rem;
    }

            /* KPI CARDS */
            .stats-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 1.5rem;
    margin-bottom: 2.5rem;
    }
            .kpi-card {
    background: var(--card-bg);
    padding: 1.5rem;
    border-radius: 12px;
    box-shadow: var(--shadow);
    border-left: 5px solid var(--accent);
    }
            .kpi-card h3 {
    color: #64748b;
    font-size: 0.85rem;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    margin-bottom: 0.5rem;
    }
            .kpi-card .value {
    font-size: 2rem;
    font-weight: 700;
    color: var(--primary);
    }
            .kpi-card .subtext {
    font-size: 0.85rem;
    color: var(--success);
    margin-top: 5px;
    }

            /* MANAGEMENT GRID */
            .mgmt-title {
    font-size: 1.2rem;
    margin-bottom: 1rem;
    color: var(--primary);
    font-weight: 600;
    border-bottom: 2px solid #e2e8f0;
    padding-bottom: 10px;
    display: inline-block;
    }
            .mgmt-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 1.5rem;
    }

            .action-card {
                background: white;
    border-radius: 12px;
    padding: 1.5rem;
                box-shadow: var(--shadow);
    transition: transform 0.2s, box-shadow 0.2s;
                text-decoration: none;
    color: inherit;
    border: 1px solid #e2e8f0;
                display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 160px;
            }
            .action-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
    border-color: var(--accent);
    }

            .card-icon {
    width: 45px;
    height: 45px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 1rem;
    color: white;
    }
            .icon-blue {
    background: #3b82f6;
    }
            .icon-purple {
    background: #8b5cf6;
    }
            .icon-orange {
    background: #f97316;
    }
            .icon-green {
    background: #10b981;
    }

            .card-title {
    font-size: 1.1rem;
    font-weight: 700;
    color: var(--primary);
    }
            .card-desc {
    font-size: 0.9rem;
    color: #64748b;
    margin-top: 0.25rem;
    }
            .arrow-link {
    margin-top: auto;
    color: var(--accent);
    font-weight: 600;
    font-size: 0.9rem;
    display: flex;
    align-items: center;
    gap: 5px;
    }

        </style>
    </head>
    <body>

        <aside>
            <div class="brand">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5"/></svg>
                AdminPanel
            </div>
            <nav>
                <a href="#" class="active">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7"></rect><rect x="14" y="3" width="7" height="7"></rect><rect x="14" y="14" width="7" height="7"></rect><rect x="3" y="14" width="7" height="7"></rect></svg>
                    Dashboard
                </a>
                <a href="gestionElecciones.jsp">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
                    Elecciones
                </a>
                <a href="gestionPartido.jsp">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 15s1-1 4-1 5 2 8 2 4-1 4-1V3s-1 1-4 1-5-2-8-2-4 1-4 1z"></path><line x1="4" y1="22" x2="4" y2="15"></line></svg>
                    Partidos
                </a>
                <a href="gestionCandidatos.jsp">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
                    Candidatos
                </a>
            </nav>
            <div class="admin-info">
                <div class="avatar">A</div>
                <div style="flex: 1">
                    <div style="font-weight: 600; font-size: 0.9rem; color: white;">Administrador</div>
                    <div style="font-size: 0.8rem; color: #94a3b8;">Sistema Central</div>
                </div>
                <a href="../logout.jsp" title="Salir" style="color: #ef4444;">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path><polyline points="16 17 21 12 16 7"></polyline><line x1="21" y1="12" x2="9" y2="12"></line></svg>
                </a>
            </div>
        </aside>

        <main>
            <header>
                <div>
                    <h1>Torre de Control</h1>
                    <span class="subtitle">Bienvenido al panel de gestión del sistema e-Votaciones.</span>
                </div>
                <div style="text-align: right">
                    <span style="background: #dbeafe; color: #1e40af; padding: 5px 10px; border-radius: 20px; font-size: 0.8rem; font-weight: bold;">
                        Versión 1.0.0
                    </span>
                </div>
            </header>

            <section class="stats-grid">
                <div class="kpi-card" style="border-left-color: #3b82f6;">
                    <h3>Total Elecciones</h3>
                    <div class="value"><%= numElecciones%></div>
                    <div class="subtext"><%= activas%> Activas ahora mismo</div>
                </div>
                <div class="kpi-card" style="border-left-color: #8b5cf6;">
                    <h3>Partidos Políticos</h3>
                    <div class="value"><%= numPartidos%></div>
                    <div class="subtext">Registrados en BBDD</div>
                </div>
                <div class="kpi-card" style="border-left-color: #f97316;">
                    <h3>Sistema</h3>
                    <div class="value" style="font-size: 1.5rem; color: var(--success);">ONLINE</div>
                    <div class="subtext">Base de datos conectada</div>
                </div>
            </section>

            <h2 class="mgmt-title">Gestión Electoral</h2>
            <section class="mgmt-grid">

                <a href="gestionElecciones.jsp" class="action-card">
                    <div>
                        <div class="card-icon icon-blue">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="9 11 12 14 22 4"></polyline><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"></path></svg>
                        </div>
                        <div class="card-title">Elecciones</div>
                        <div class="card-desc">Crear nuevas convocatorias, abrir escrutinio o finalizar procesos.</div>
                    </div>
                    <div class="arrow-link">Gestionar &rarr;</div>
                </a>

                <a href="gestionPartido.jsp" class="action-card">
                    <div>
                        <div class="card-icon icon-purple">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 15s1-1 4-1 5 2 8 2 4-1 4-1V3s-1 1-4 1-5-2-8-2-4 1-4 1z"></path><line x1="4" y1="22" x2="4" y2="15"></line></svg>
                        </div>
                        <div class="card-title">Partidos Políticos</div>
                        <div class="card-desc">Dar de alta nuevas formaciones políticas y subir sus logotipos.</div>
                    </div>
                    <div class="arrow-link">Gestionar &rarr;</div>
                </a>

                <a href="gestionCandidatos.jsp" class="action-card">
                    <div>
                        <div class="card-icon icon-orange">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
                        </div>
                        <div class="card-title">Candidatos</div>
                        <div class="card-desc">Asignar personas del censo a las listas electorales.</div>
                    </div>
                    <div class="arrow-link">Gestionar &rarr;</div>
                </a>

                <a href="resultados.jsp" class="action-card">
                    <div>
                        <div class="card-icon icon-green">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="18" y1="20" x2="18" y2="10"></line><line x1="12" y1="20" x2="12" y2="4"></line><line x1="6" y1="20" x2="6" y2="14"></line></svg>
                        </div>
                        <div class="card-title">Ver Escrutinio</div>
                        <div class="card-desc">Consultar los resultados de las elecciones finalizadas.</div>
                    </div>
                    <div class="arrow-link">Ver Datos &rarr;</div>
                </a>

            </section>
        </main>

    </body>
</html>