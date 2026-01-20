<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Unidad 6 Ejercicio Cookie</title>
    <?php
        require 'vistas/estilos.php';
    ?>
    
</head>
<body>

    <div class="contenedor">
        <h1>Aplicación Cookie</h1>

        <form action="controladores/gestionPrincipal.php">
            
            <div class="botones">
                <button type="submit" name="accion" value="crear">Crear Persona</button>
            </div>
            
            
            <div class="botones">
                <button type="submit" name="accion" value="ver">Ver Personas</button>
            </div>
            
            <div class="botones">
                <button type="submit" name="accion" value="salir">Salir de la aplicación</button>
            </div>
        </form>
    </div>

</body>
</html>