<?php
    require 'estilos.php';
    session_start();
 ?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Unidad 6 Ejercicio Cookie</title>
    
</head>
<body>

    <div class="contenedor">
        <h1>Aplicación Cookie</h1>

        <center>
           <?php
               if (isset($_COOKIE['ultimo'])){
                   $ultimo=$_COOKIE['ultimo'];
                   echo "<h3>"."La última persona creada ha sido: ".$ultimo;
                   setcookie('ultimo', $ultimo, time() - 180, '/');
               }
               else {
                   echo "<h3>No se han creado personas</h3>";
               }
               
           
           ?>
        </center>
        <br>
        <br>
        <center><a href="../index.php">Ir a índice</a></center>
    </div>
    
</body>
</html>

