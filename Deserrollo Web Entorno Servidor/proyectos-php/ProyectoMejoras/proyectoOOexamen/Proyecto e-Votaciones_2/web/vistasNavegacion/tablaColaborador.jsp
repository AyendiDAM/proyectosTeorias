<%-- 
    Document   : tablaColaborador
    Created on : 5 feb 2026, 11:06:39
    Author     : ayendi
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
            || (usuario.getRol() != null && !usuario.getRol().equalsIgnoreCase("colaborador"))) {
        response.sendRedirect("../home.jsp");
        return;
    }

    // 2. DATOS PARA EL DASHBOARD
    ConexionBBDD eDao = new ConexionBBDD();
    int datos=eDao.obtenerLocalidaColaborador();


   
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

     

    </body>
</html>