
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista Telefónica</title>
    <?php
    require 'vistas/estilos.php';
    ?>
</head>
<body>
<center>
    <br>
    <br>
    <h1>Mis contactos telefónicos</h1>
    <div class="form-container">
        <form method="post" action="controladores/gestionarAcciones.php">
            <button type="submit" name="accion" value="crear_tabla">Crear Tabla Contactos</button>
            <button type="submit" name="accion" value="anadir_contacto">Añadir un Contacto</button>
            <button type="submit" name="accion" value="modificar_contacto">Modificar un Contacto</button>
            <button type="submit" name="accion" value="borrar_contacto">Borrar un Contacto</button>
            <button type="submit" name="accion" value="listar_contactos">Listar Todos los Contactos</button>
            <button type="submit" name="accion" value="buscar_contacto">Buscar un Contacto</button>
            
            <button type="submit" name="accion" value="salir_aplicacion">Salir de la aplicación</button>
        </form>
    </div>
</center>
</body>
</html>

