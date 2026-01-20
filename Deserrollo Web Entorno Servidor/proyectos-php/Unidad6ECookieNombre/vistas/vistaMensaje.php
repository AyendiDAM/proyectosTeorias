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

        <table>
            <tr class="fila-verde">
                <td>Operación realizada con éxito</td>
            </tr>
            <tr class="fila-dos">
                <?php
                    $mensaje=$_SESSION['mensaje'];
                    echo "<td>$mensaje</td>";
                ?>
                
            </tr>
        </table>
        <br>
        <br>
        <center><a href="../index.php">Ir a índice</a></center>
    </div>
    
</body>
</html>

