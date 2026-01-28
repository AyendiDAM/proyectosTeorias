<%-- 
    Document   : login
    Created on : 9 nov 2025, 7:36:36
    Author     : ayend
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Control Escrutinio | e-Votaciones</title>
  <style>
    :root{
      --bg:#f4f7fb;
      --card:#ffffff;
      --primary:#0A3D62;
      --accent:#2E86DE;
      --success:#27ae60;
      --warn:#e67e22;
      --muted:#6b7280;
      --danger:#c0392b;
    }
    *{box-sizing:border-box}
    body{margin:0;font-family:Inter,system-ui,Arial;background:var(--bg);color:#111}
    .wrap{max-width:1100px;margin:1.5rem auto;padding:1rem}
    header{display:flex;justify-content:space-between;align-items:center;margin-bottom:1rem}
    header h1{margin:0;color:var(--primary)}
    .grid{display:grid;grid-template-columns:1fr 360px;gap:1rem}
    @media (max-width:960px){ .grid{grid-template-columns:1fr} }

    /* Control panel */
    .panel{background:var(--card);padding:1rem;border-radius:12px;box-shadow:0 8px 26px rgba(10,61,98,0.06)}
    .panel h2{margin-top:0;color:var(--primary)}
    .form-row{display:flex;gap:0.5rem;align-items:center;margin:0.75rem 0}
    select,input[type="date"]{padding:0.6rem;border-radius:8px;border:1px solid #ddd;flex:1}
    .big-actions{display:flex;flex-direction:column;gap:0.6rem;margin-top:1rem}
    .bigbtn{display:block;padding:0.95rem;border-radius:10px;border:none;font-weight:800;cursor:pointer;font-size:1rem}
    .open{background:var(--success);color:#fff}
    .close{background:var(--warn);color:#fff}
    .final{background:var(--primary);color:#fff}

    /* State box */
    .state{background:#fbfcfd;border:1px solid #eef3f7;padding:1rem;border-radius:10px}
    .state .row{display:flex;justify-content:space-between;padding:0.35rem 0}
    .state .label{color:var(--muted)}
    .state .value{font-weight:700}

    /* Logs */
    .logs{margin-top:1rem}
    table{width:100%;border-collapse:collapse;background:var(--card);border-radius:8px;overflow:hidden}
    th,td{padding:0.65rem;border-bottom:1px solid #eee;text-align:left;font-size:0.95rem}
    thead th{background:#f7fafc;color:var(--muted);font-weight:700}

    /* Small helpers */
    .note{font-size:0.92rem;color:var(--muted);margin-top:0.6rem}
    .muted{color:var(--muted);font-size:0.92rem}

    /* Modal CSS (checkbox hack) - purely visual for confirmation) */
    .modal-toggle{display:none}
    .modal{
      position:fixed;inset:0;display:flex;align-items:center;justify-content:center;
      background:rgba(0,0,0,0.45);visibility:hidden;opacity:0;transition:opacity .2s ease;
    }
    .modal-box{background:var(--card);padding:1.25rem;border-radius:10px;max-width:520px;width:92%;box-shadow:0 16px 40px rgba(0,0,0,0.25)}
    .modal h3{margin:0 0 0.5rem 0}
    .modal p{margin:0 0 1rem 0;color:var(--muted)}
    .modal .actions{display:flex;justify-content:flex-end;gap:0.5rem}
    .btn-light{background:transparent;border:1px solid #ddd;padding:0.6rem 0.9rem;border-radius:8px}
    .btn-danger{background:var(--danger);color:#fff;border:none;padding:0.6rem 0.9rem;border-radius:8px}

    /* Show modal when checkbox checked */
    .modal-toggle:checked + .modal{visibility:visible;opacity:1}

    /* Footer */
    footer{margin-top:1rem;color:var(--muted);font-size:0.9rem}
  </style>
</head>
<body>
  <div class="wrap">
    <header>
      <h1>Control de Escrutinio</h1>
      <div class="muted">Administrador: <strong>admin</strong></div>
    </header>

    <div class="grid">
      <!-- LEFT: Controls + Logs -->
      <div>
        <section class="panel" aria-labelledby="panel-title">
          <h2 id="panel-title">Acciones de escrutinio</h2>

          <!-- Selección de elección (formulario sin JS) -->
          <form action="/admin/escrutinio" method="post">
            <div class="form-row">
              <label for="eleccion">Elección:</label>
              <select id="eleccion" name="eleccion" required>
                <option value="eg2025">Elecciones Generales 2025</option>
                <option value="reg2024">Elecciones Regionales 2024</option>
                <option value="loc2023">Elecciones Locales 2023</option>
              </select>
            </div>

            <div class="form-row">
              <label for="fecha_inicio">Fecha inicio (opcional):</label>
              <input type="date" id="fecha_inicio" name="fecha_inicio">
            </div>

            <div class="form-row">
              <label for="fecha_fin">Fecha fin (opcional):</label>
              <input type="date" id="fecha_fin" name="fecha_fin">
            </div>

            <p class="note">Use los botones para cambiar el estado del escrutinio. Todas las acciones deben ser confirmadas por el administrador.</p>

            <!-- Botones grandes: cada uno envía una acción distinta -->
            <div class="big-actions">
              <!-- Abrir escrutinio -->
              <button type="submit" name="accion" value="abrir" class="bigbtn open">Abrir Escrutinio</button>

              <!-- Cerrar escrutinio -->
              <button type="submit" name="accion" value="cerrar" class="bigbtn close">Cerrar Escrutinio</button>

              <!-- Finalizar y calcular resultados -->
              <button type="submit" name="accion" value="finalizar" class="bigbtn final">Finalizar y Calcular Resultados</button>
            </div>
          </form>

          <p class="muted">Al pulsar una acción, el servidor debe validar: estado actual de la elección, permisos del usuario y registrar la acción en el log.</p>

          <!-- Logs -->
          <div class="logs" aria-live="polite">
            <h3 style="margin-top:1rem;margin-bottom:0.5rem;color:var(--primary)">Logs de acciones</h3>
            <table>
              <thead>
                <tr><th>Fecha</th><th>Usuario</th><th>Acción</th><th>Detalle</th></tr>
              </thead>
              <tbody>
                <tr><td>2025-11-10 09:12</td><td>admin</td><td>Abrir escrutinio</td><td>Elecciones Generales 2025</td></tr>
                <tr><td>2025-11-09 18:03</td><td>admin</td><td>Crear partido</td><td>Partido Verde (PV)</td></tr>
                <tr><td>2025-11-08 12:20</td><td>admin</td><td>Editar elección</td><td>Elecciones Locales 2023 (fechas)</td></tr>
              </tbody>
            </table>
          </div>
        </section>
      </div>

      <!-- RIGHT: Estado rápido -->
      <aside class="panel state" aria-labelledby="estado-title">
        <h2 id="estado-title" style="margin-top:0;color:var(--primary)">Estado actual</h2>
        <div class="row"><div class="label">Elección</div><div class="value">Elecciones Generales 2025</div></div>
        <div class="row"><div class="label">Estado</div><div class="value" style="color:var(--success)">Abierta</div></div>
        <div class="row"><div class="label">Fecha inicio</div><div class="value">2025-11-10</div></div>
        <div class="row"><div class="label">Fecha fin</div><div class="value">2025-11-17</div></div>
        <div style="height:12px"></div>
        <h3 style="margin:0 0 0.5rem 0;color:var(--primary)">Acciones recientes</h3>
        <div class="row"><div class="label">Última acción</div><div class="value">Abrir escrutinio — 2025-11-10 09:12</div></div>
        <div class="row"><div class="label">Usuario</div><div class="value">admin</div></div>

        <p class="note">Las acciones realizadas se registran en la base de datos para auditoría. Use la tabla de logs para más detalle.</p>

        <!-- Visual modal trigger (puro visual): muestra confirmación estática -->
        <div style="margin-top:0.75rem">
          <label for="modal-confirm" style="display:inline-block;padding:0.6rem 0.9rem;border-radius:8px;background:#f1f5f9;border:1px solid #ddd;cursor:pointer">Ver ejemplo de confirmación</label>
          <input id="modal-confirm" class="modal-toggle" type="checkbox">
          <div class="modal" aria-hidden="true">
            <div class="modal-box" role="dialog" aria-modal="true" aria-labelledby="mc-title">
              <h3 id="mc-title">Confirmar acción</h3>
              <p>¿Confirma que desea finalizar la elección <strong>Elecciones Generales 2025</strong>? Esta operación generará el recuento final y no podrá revertirse.</p>
              <div class="actions">
                <label for="modal-confirm" class="btn-light" style="cursor:pointer">Cancelar</label>
                <button class="btn-danger" style="margin-left:0.5rem">Confirmar (simulado)</button>
              </div>
            </div>
          </div>
        </div>

      </aside>
    </div>

    <footer>
      <p class="muted">Nota: esta es una maqueta de diseño. Los botones envían formularios al servidor; la lógica y la seguridad (validación de estado, permisos y logging) deben implementarse en el backend.</p>
    </footer>
  </div>
</body>
</html>

