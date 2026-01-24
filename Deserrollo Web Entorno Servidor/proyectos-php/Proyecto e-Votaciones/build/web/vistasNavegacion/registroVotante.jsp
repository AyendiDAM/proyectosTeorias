<%-- 
    Document   : login
    Created on : 9 nov 2025, 7:36:36
    Author     : ayend
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Registro | e-Votaciones</title>
  <style>
    body {
      font-family: 'Inter', sans-serif;
      background-color: #f7f9fc;
      margin: 0;
      padding: 0;
      display: flex;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
    }
    .register-container {
      background: white;
      padding: 2.5rem;
      border-radius: 16px;
      box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
      width: 100%;
      max-width: 450px;
    }
    h1 {
      text-align: center;
      color: #0A3D62;
      margin-bottom: 1.5rem;
    }
    form {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }
    label {
      font-weight: 600;
      color: #333;
    }
    input, select {
      padding: 0.75rem;
      border: 1px solid #ccc;
      border-radius: 8px;
      font-size: 1rem;
    }
    input:focus, select:focus {
      outline: none;
      border-color: #2E86DE;
      box-shadow: 0 0 4px rgba(46, 134, 222, 0.4);
    }
    .btn {
      background-color: #0A3D62;
      color: white;
      padding: 0.9rem;
      border: none;
      border-radius: 10px;
      font-size: 1rem;
      font-weight: bold;
      cursor: pointer;
      transition: background 0.3s;
    }
    .btn:hover {
      background-color: #2E86DE;
    }
    .error {
      color: #c0392b;
      font-size: 0.9rem;
    }
    .success {
      color: #27ae60;
      font-size: 0.9rem;
      text-align: center;
    }
    .footer-link {
      text-align: center;
      margin-top: 1rem;
      font-size: 0.9rem;
    }
    .footer-link a {
      color: #0A3D62;
      text-decoration: none;
      font-weight: 600;
    }
    .footer-link a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
    <form action="../RegistroControlador" method="POST">
  <div class="register-container">
    <h1>Registro de Votante</h1>
    <form>
      <div>
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" placeholder="Tu nombre" required>
      </div>
      <div>
        <label for="apellidos">Apellidos</label>
        <input type="text" id="apellidos" name="apellidos" placeholder="Tus apellidos" required>
      </div>
      <div>
        <label for="dni">DNI</label>
        <input type="text" id="dni" name="dni" placeholder="12345678X" maxlength="9" required>
      </div>
      <div>
        <label for="fecha_nacimiento">Fecha de nacimiento</label>
        <input type="date" id="fecha_nacimiento" name="fecha_nacimiento" required>
      </div>

      <div>
        <label for="localidad">Localidad</label>
        <select id="localidad" name="localidad" >

       
                <option value="1">Albacete</option>
              <option value="2">Madrid</option>
                <option value="3">Barcelona</option>
                  <option value="4">Galicia</option>

        </select>
      </div>
      <div>
        <label for="password">Contraseña</label>
        <input type="password" id="password" name="password" placeholder="••••••••" required>
      </div>
      <div>
        <label for="confirm_password">Repetir contraseña</label>
        <input type="password" id="confirm_password" name="confirm_password" placeholder="••••••••" required>
      </div>
      <button type="submit" class="btn">Registrarse</button>
      <p class="success" id="mensaje-exito" style="display:none;">Registro completado correctamente.</p>
      <p class="error" id="mensaje-error" style="display:none;">Ha ocurrido un error. Verifique los datos.</p>
    </form>
    <div class="footer-link">
      <p>¿Ya tienes cuenta? <a href="login.jsp">Inicia sesión aquí</a></p>
    </div>
  </div>
        </form>
</body>
</html>

