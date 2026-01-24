<%-- 
    Document   : cambiarRol
    Created on : 27 nov 2025, 0:41:59
    Author     : ayend
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cambiar Rol de Usuario</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: #f4f7fb;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .form-card {
        background: #fff;
        padding: 25px;
        border-radius: 12px;
        width: 380px;
        box-shadow: 0 6px 18px rgba(0,0,0,0.1);
    }
    h2 {
        margin-top: 0;
        color: #0A3D62;
        text-align: center;
    }
    label {
        font-weight: bold;
        margin-top: 10px;
        display: block;
    }
    input, select {
        width: 100%;
        padding: 10px;
        margin-top: 6px;
        border-radius: 6px;
        border: 1px solid #ccc;
    }
    button {
        margin-top: 15px;
        width: 100%;
        padding: 12px;
        background: #0A3D62;
        color: white;
        border: none;
        border-radius: 8px;
        font-size: 16px;
        cursor: pointer;
    }
    button:hover {
        background: #074568;
    }
</style>
</head>
<body>

<div class="form-card">
    <h2>Cambiar Rol de Usuario</h2>

    <form action="../GestionCambiaRolServlet" method="post">
        
        <label for="usuario">Usuario ID:</label>
        <input type="text" id="usuario" name="usuario" required>

        <label for="rol">Nuevo Rol:</label>
        <select id="rol" name="rol" required>
            <option value="VOTANTE">VOTANTE</option>
            <option value="ADMIN">ADMIN</option>
        </select>

        <button type="submit">Guardar Cambios</button>

    </form>
</div>

</body>
</html>
