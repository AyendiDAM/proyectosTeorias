<!DOCTYPE html>
<?php
require_once '../dao/ConexionBBDD.php';
session_start();

try {
    $bbdd = new ConexionBBDD();
    $todos_contactos = $bbdd->get_todos_contactos();
} catch (mysqli_sql_exception $ex) {
    $error = $ex->getMessage();
    $_SESSION['error'] = $error;
    header('Location:vistaError.php');
} finally {
    $bbdd->cerrar_conexion();
}
?>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Aplicación contactos</title>
    </head>
    <body>
    <center>
        <br>
        <br>
        <br>
        <h1>Elige un contacto</h1>
        <br>
        <form method="post" action="../controladores/buscarContacto.php">
        
             <select name='seleccionado'>
                <?php
                foreach ($todos_contactos as $contacto){ 
                ?>   
                
                 <option value='<?php echo $contacto['id']; ?>'><?php echo $contacto['nombre']." ". $contacto['apellidos']; ?> </option>
                
                <?php
                }
                ?> 
                
                </select>  
              
              
        <br>
        <br>
        <br>
        <button type="submit" name="buscar">Modificar Contacto</button>
    </form>    
    </center>
    </body>
</html>
