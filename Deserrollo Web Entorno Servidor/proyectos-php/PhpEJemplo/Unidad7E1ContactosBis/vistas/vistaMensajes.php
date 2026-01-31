<?php
    session_start();
?>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Vista acción correcta</title>
    </head>
    <body>
    <center>
        <br>
        <br>
        <h1>Operación realizada con éxito</h1>
        <br>
        <br>
        <?php
          $mensaje=$_SESSION['mensaje'];
          echo $mensaje;
        ?>
        <br>
        <br>
        <a href="../index.php">Volver a index</a>
    </center>
    </body>
</html>
