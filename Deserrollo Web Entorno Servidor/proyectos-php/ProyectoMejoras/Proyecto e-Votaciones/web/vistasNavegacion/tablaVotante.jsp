<%-- 
    Document   : login
    Created on : 9 nov 2025, 7:36:36
    Author     : ayend
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="modelos.Usuario"%>
<%@page import="dao.ConexionBBDD"%>
<%@page import="modelos.Eleccion"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--recogemos las cookies--%>
<%
    // 1. SEGURIDAD DE SESIÓN
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("../login.jsp");
        return;
    }

    // 2. CONEXIÓN Y CARGA DE DATOS
    ConexionBBDD eDao = new ConexionBBDD();

    // Obtenemos todas las elecciones
    ArrayList<Eleccion> misElecciones = eDao.obtenerElecciones();

    // Contadores para las tarjetas de arriba
    int activas = 0;
    int votosRealizados = 0; // Podríamos calcularlo contando en cuántas ha votado
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Dashboard Votante | e‑Votaciones</title>
        <style>
            :root {
                --bg: #f3f4f6;
                --sidebar-bg: #ffffff;
                --primary: #0A3D62;
                --primary-hover: #06263f;
                --accent: #2E86DE;
                --text-main: #1f2937;
                --text-muted: #6b7280;
                --success: #10b981;
                --warning: #f59e0b;
                --danger: #ef4444;
                --border: #e5e7eb;
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

            /* SIDEBAR */
            aside {
                width: 260px;
                background: var(--sidebar-bg);
                border-right: 1px solid var(--border);
                display: flex;
                flex-direction: column;
                padding: 1.5rem;
                flex-shrink: 0;
            }
            aside .brand {
                font-size: 1.5rem;
                font-weight: 800;
                color: var(--primary);
                margin-bottom: 2rem;
                display: flex;
                align-items: center;
                gap: 0.5rem;
            }
            aside nav {
                display: flex;
                flex-direction: column;
                gap: 0.5rem;
            }
            aside nav a {
                text-decoration: none;
                color: var(--text-muted);
                padding: 0.75rem 1rem;
                border-radius: 8px;
                font-weight: 500;
                transition: all 0.2s;
                display: flex;
                align-items: center;
                gap: 0.75rem;
            }
            aside nav a:hover, aside nav a.active {
                background: #eef2ff;
                color: var(--primary);
            }
            aside .user-info {
                margin-top: auto;
                padding-top: 1rem;
                border-top: 1px solid var(--border);
                display: flex;
                align-items: center;
                gap: 0.8rem;
            }
            aside .avatar {
                width: 40px;
                height: 40px;
                background: var(--primary);
                color: white;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-weight: bold;
                font-size: 1.2rem;
            }

            /* MAIN */
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
            }
            .date {
                color: var(--text-muted);
                font-size: 0.9rem;
            }

            /* CARDS */
            .stats-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
                gap: 1.5rem;
                margin-bottom: 2rem;
            }
            .stat-card {
                background: white;
                padding: 1.5rem;
                border-radius: 12px;
                box-shadow: var(--shadow);
                border: 1px solid var(--border);
            }
            .stat-card h3 {
                color: var(--text-muted);
                font-size: 0.9rem;
                font-weight: 500;
                margin-bottom: 0.5rem;
            }
            .stat-card .value {
                font-size: 2rem;
                font-weight: 700;
                color: var(--primary);
            }

            /* ELECTION LIST */
            .elections-grid {
                display: flex;
                flex-direction: column;
                gap: 1rem;
            }
            .election-card {
                background: white;
                border-radius: 12px;
                padding: 1.5rem;
                box-shadow: var(--shadow);
                border: 1px solid var(--border);
                display: flex;
                justify-content: space-between;
                align-items: center;
                transition: transform 0.2s;
            }
            .election-card:hover {
                transform: translateY(-2px);
                border-color: var(--accent);
            }
            .election-content h3 {
                margin-bottom: 0.4rem;
                color: var(--primary);
            }
            .meta-info {
                display: flex;
                gap: 1.5rem;
                color: var(--text-muted);
                font-size: 0.9rem;
                margin-top: 0.5rem;
            }

            /* BADGES & BUTTONS */
            .status-badge {
                padding: 0.35rem 0.75rem;
                border-radius: 999px;
                font-size: 0.8rem;
                font-weight: 600;
                text-transform: uppercase;
            }
            .status-badge.ABIERTA {
                background: #d1fae5;
                color: var(--success);
            }
            .status-badge.CERRADA {
                background: #fee2e2;
                color: var(--warning);
            }
            .status-badge.FINALIZADA {
                background: #f3f4f6;
                color: var(--text-muted);
            }

            .btn {
                padding: 0.6rem 1.2rem;
                border-radius: 8px;
                border: none;
                font-weight: 600;
                cursor: pointer;
                text-decoration: none;
                display: inline-block;
                font-size: 0.9rem;
            }
            .btn-primary {
                background: var(--primary);
                color: white;
            }
            .btn-primary:hover {
                background: var(--primary-hover);
            }
            .btn-secondary {
                background: white;
                border: 1px solid var(--border);
                color: var(--text-main);
            }
            .btn-disabled {
                background: #e5e7eb;
                color: #9ca3af;
                cursor: not-allowed;
            }
        </style>
    </head>
    <body>

        <aside>
            <div class="brand">🗳️ e‑Votaciones</div>
            <nav>
                <a href="#" class="active">Mis Elecciones</a>
                <a href="vistasNavegacion/resultados.jsp">Resultados Globales</a>
            </nav>
            <div class="user-info">
                <div class="avatar"><%= usuario.getNombre().substring(0, 1).toUpperCase()%></div>
                <div style="flex: 1">
                    <div style="font-weight: 600; font-size: 0.9rem;"><%= usuario.getNombre()%></div>
                    <div style="font-size: 0.8rem; color: var(--text-muted);">DNI: <%= usuario.getDni()%></div>
                </div>
                <a href="home.jsp" style="color: var(--danger); font-weight:bold; text-decoration:none;">Salir</a>
            </div>
        </aside>

        <main>
            <header>
                <div>
                    <h1>Panel de Votación</h1>
                    <span class="date">Bienvenido al sistema electoral seguro.</span>
                </div>
            </header>

            <%
                // Calculamos estadísticas rápidas para las tarjetas
                int abiertas = 0;
                int finalizadas = 0;
                for (Eleccion e : misElecciones) {
                    if ("ABIERTA".equals(e.getEstado())) {
                        abiertas++;
                    }
                    if ("FINALIZADA".equals(e.getEstado())) {
                        finalizadas++;
                    }
                }

                // Ver si el usuario ya votó (usamos la variable de sesión o BBDD)
                String estadoVotoUsuario = (usuario.getVoto() == 1) ? "Ya ha votado" : "Pendiente de voto";
            %>
            <section class="stats-grid">
                <div class="stat-card">
                    <h3>Elecciones Activas</h3>
                    <div class="value"><%= abiertas%></div>
                </div>
                <div class="stat-card">
                    <h3>Histórico Finalizadas</h3>
                    <div class="value"><%= finalizadas%></div>
                </div>
                <div class="stat-card">
                    <h3>Tu Estado</h3>
                    <div class="value" style="font-size: 1.2rem; color: var(--accent); margin-top:10px;">
                        <%= estadoVotoUsuario%>
                    </div>
                </div>
            </section>

            <section>
                <h2 style="margin-bottom: 1rem; color: var(--text-main);">Convocatorias Electorales</h2>
                <div class="elections-grid">

                    <%
                        if (misElecciones.isEmpty()) {
                    %>
                    <div class="election-card"><h3>No hay elecciones registradas en el sistema.</h3></div>
                    <%
                    } else {
                        for (Eleccion e : misElecciones) {
                            // Verificamos si YA VOTÓ en esta elección específica
                            boolean yaVoto = eDao.yaHaVotado(usuario.getId_usuario(), e.getId());
                    %>

                    <article class="election-card" style="<%= e.getEstado().equals("FINALIZADA") ? "opacity:0.7;" : ""%>">
                        <div class="election-content">
                            <div style="margin-bottom:0.5rem">
                                <span class="status-badge <%= e.getEstado()%>"><%= e.getEstado()%></span>
                                <% if (yaVoto) { %> 
                                <span style="font-size:0.8rem; color:var(--success); font-weight:bold; margin-left:10px;">✓ Voto Registrado</span> 
                                <% }%>
                            </div>
                            <h3><%= e.getNombre()%></h3>
                            <div class="meta-info">
                                <span>📅 Inicio: <%= e.getFechaInicio()%></span>
                                <span>🏁 Fin: <%= e.getFechaFinal()%></span>
                            </div>
                        </div>

                        <div class="election-actions">
                            <% if (e.getEstado().equals("ABIERTA")) { %>

                            <% if (!yaVoto) { %>
                            <a href="votar.jsp" class="btn btn-primary">Ir a Votar</a>
                            <% } else { %>
                            <button class="btn btn-disabled" disabled>Ya has votado</button>
                            <% } %>

                            <% } else if (e.getEstado().equals("FINALIZADA")) { %>

                            <a href="resultados.jsp" class="btn btn-secondary">Ver Escrutinio</a>

                            <% } else { %>

                            <button class="btn btn-disabled" disabled>Próximamente</button>

                            <% } %>
                        </div>
                    </article>

                    <%
                            } // Fin del for
                        } // Fin del else
                    %>

                </div>
            </section>
        </main>

    </body>
</html>