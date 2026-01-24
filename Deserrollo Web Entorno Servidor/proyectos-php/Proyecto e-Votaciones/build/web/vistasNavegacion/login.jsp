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
  <title>Iniciar Sesión | e-Votaciones</title>
  <style>
    body {
      font-family: 'Inter', sans-serif;
      background-color: #f7f9fc;
      display: flex;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
      margin: 0;
    }
    .login-container {
      background: white;
      padding: 2.5rem;
      border-radius: 16px;
      box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
      width: 100%;
      max-width: 400px;
    }
    h1 {
      text-align: center;
      color: #0A3D62;
      margin-bottom: 2rem;
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
    input {
      padding: 0.75rem;
      border: 1px solid #ccc;
      border-radius: 8px;
      font-size: 1rem;
    }
    input:focus {
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

  <div class="login-container">
    <h1>Iniciar Sesión</h1>
      <form action="../Login_Controlador" method="POST">
      <div>
        <label for="dni">DNI</label>
        <input type="text" id="dni" name="dni" placeholder="12345678X" maxlength="9" required>
      </div>
      <div>
        <label for="password">Contraseña</label>
        <input type="password" id="password" name="password" placeholder="••••••••" required>
      </div>
      <button type="submit" class="btn">Entrar</button>
      <p class="error" id="mensaje-error" style="display:none;">DNI o contraseña incorrectos.</p>
    </form>
    <div class="footer-link">
      <p>¿No tienes cuenta? <a href="registroVotante.jsp">Regístrate aquí</a></p>
    </div>
  </div>
</body>
</html>