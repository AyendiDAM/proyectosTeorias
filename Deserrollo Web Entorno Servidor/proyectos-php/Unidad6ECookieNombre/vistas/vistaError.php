<?php
    session_start();
 ?>
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

        <table>
            <tr class="fila-rojo">
                <td>Operación realizada con errores</td>
            </tr>
            <tr class="fila-dos">
                <?php
                    $error=$_SESSION['error'];
                    echo "<td>$error</td>";
                ?>
                
            </tr>
        </table>
        <br>
        <br>
        <center><a href="../index.php">Ir a índice</a></center>
    </div>

</body>
</html>

