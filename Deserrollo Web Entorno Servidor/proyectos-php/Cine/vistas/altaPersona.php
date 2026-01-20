<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Unidad 6 Ejercicio Cookie</title>
    <?php
        require 'estilos.php';
    ?>
    
</head>
<body>

    <div class="contenedor">
        <h1>Aplicación Cookie</h1>

        <form action="../controladores/crearPersona.php">
            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre" required>

            <label for="dni">Edad</label>
            <input type="number"  name="edad" required>

            <div class="botones">
                <button type="submit" name="accion" value="crear">Crear Persona</button>
            </div>
        </form>
    </div>

</body>
</html>

