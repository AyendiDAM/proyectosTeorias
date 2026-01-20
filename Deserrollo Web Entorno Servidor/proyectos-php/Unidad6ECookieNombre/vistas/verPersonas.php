<?php
    require '../modelos/Persona.php';
    require 'estilos.php';
    session_start();
    
    $personas=$_SESSION['personas'];
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Unidad 6 Ejercicio cookie</title>
    
</head>
<body>

<div class="contenedor">
    <h1>Listado de Personas</h1>
    <?php
        // print_r($personas);
    ?>
    <table>
        <tr>
            <th>Nombre</th>
            <th>Edad</th>
        </tr>

        <?php
        foreach ($personas as $p){ ?>
        <tr>
            <td><?php echo $p->getNombre(); ?></td>
            <td><?php echo $p->getEdad(); ?></td>
        </tr>
        <?php 
        }
        ?>
        
    </table>
    
        <br>
        <br>
        <center><a href="../index.php">Ir a índice</a></center>
   
</div>

</body>
</html>
