<?php
    session_start();
?>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Vista acción incorrecta</title>
    </head>
    <body>
    <center>
        <br>
        <br>
        <h1>Operación realizada con errores</h1>
        <br>
        <br>
        <?php
          $error=$_SESSION['error'];
          echo $error;
        ?>
        <br>
        <br>
        <a href="../index.php">Volver a index</a>
    </center>
    </body>
</html>
