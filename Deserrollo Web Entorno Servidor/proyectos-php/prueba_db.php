<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Prueba de Conexión</title>
    <style>
        body { font-family: sans-serif; text-align: center; margin-top: 50px; }
        .exito { color: green; font-size: 1.5em; border: 2px solid green; padding: 20px; display: inline-block; }
        .error { color: red; font-size: 1.2em; border: 2px solid red; padding: 20px; display: inline-block; }
    </style>
</head>
<body>
    <h1>Probando conexión Windows <-> Linux</h1>

    <?php
        // Datos de conexión (Los que configuramos en Linux)
        $servidor = "localhost";
        $usuario = "admin";
        $password = "admin";

        try {
            // Intentamos conectar
            $conn = new mysqli($servidor, $usuario, $password);

            if ($conn->connect_error) {
                die("<div class='error'>❌ Fallo la conexión: " . $conn->connect_error . "</div>");
            }

            echo "<div class='exito'>✅ ¡ÉXITO! <br> Tu código en Windows está conectado a MariaDB en Linux.</div>";
            echo "<p>Información del servidor: " . $conn->server_info . "</p>";

            $conn->close();
        } catch (Exception $e) {
            echo "<div class='error'>❌ Error: " . $e->getMessage() . "</div>";
        }
    ?>
</body>
</html>