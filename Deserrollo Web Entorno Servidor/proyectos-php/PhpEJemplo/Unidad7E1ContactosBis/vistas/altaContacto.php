<?php
    require 'estilos.php';
    session_start();
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario de Contacto</title>
    
</head>
<body>

    <div class="form-container">
        <h1>Formulario de Contacto</h1>
        <form method="post" action="../controladores/procesarNuevoContacto.php">
            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre" id="nombre"  required>
            
            <label for="apellidos">Apellidos:</label>
            <input type="text" name="apellidos" id="apellidos"  required>
            
            <label for="telefono">Teléfono:</label>
            <input type="text" name="telefono" id="telefono" required>
            
            <button type="submit" name="accion">Guardar Contacto</button>
            
            <br>
            <br>
            <br>
            <center>
            <a href="../index.php">Volver a inicio</a>
            </center>
        </form>
    </div>
   
</body>
</html>

