<?php
    require '../dao/ConexionBBDD.php';
?>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Listado de contactos</title>
    </head>
    <body>
    <center>
        <br>
        <br>
        <h1>Listado de contactos</h1>
        <?php
           try {
               $bbdd=new ConexionBBDD();
               $todos_contactos=$bbdd->get_todos_contactos();
               $bbdd->cerrar_conexion();
               foreach ($todos_contactos as $contacto) {
                   echo "<br>". $contacto ['nombre']."  ".$contacto['apellidos']." - ".$contacto['telefono'];
                   
               }
               echo "<br><br>";
               echo "<a href='../index.php'>Ir a índice</a>";
           } catch (mysqli_sql_exception $ex) {
               $_SESSION['error']=$ex->getMessage();
               header('Location:vistaError.php');
           }
            
        ?>
    </center>
    </body>
</html>
