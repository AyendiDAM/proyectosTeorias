<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Prueba PHP</title>
    <style>
        body { font-family: sans-serif; text-align: center; padding: 50px; }
        .resultado { color: green; font-weight: bold; font-size: 1.5em; }
    </style>
</head>
<body>
    <h1>Mi Primer Entorno PHP Profesional</h1>

    <?php
        // Definimos variables
        $radio = 5;
        $pi = 3.1416;
        
        // Hacemos un cálculo
        $area = $pi * ($radio * $radio);
        
        // Usamos una función de PHP para obtener la fecha
        $fecha = date("d-m-Y H:i:s");
    ?>

    <p>Calculando el área de un círculo con radio: <?php echo $radio; ?> cm</p>
    
    <div class="resultado">
        El Área es: <?php echo $area; ?> cm²
    </div>

    <hr>
    <small>Ejecutado desde Ubuntu el: <?php echo $fecha; ?></small>

</body>
</html>