<?php
require_once 'Cafetera.php';
session_start(); // IMPORTANTE: Para recordar el estado de las cafeteras entre clics

// Inicializamos las cafeteras en la sesión si no existen
if (!isset($_SESSION['cafeteraNormal'])) {
    $_SESSION['cafeteraNormal'] = new Cafetera();
}
if (!isset($_SESSION['cafeteraDescafeinado'])) {
    $_SESSION['cafeteraDescafeinado'] = new Cafetera();
}

$mensaje = "";

// Lógica para procesar los botones
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $tipo = $_POST['tipo']; // 'normal' o 'descafeinado'
    $accion = $_POST['accion'];
    $cantidad = isset($_POST['cantidad']) ? (int)$_POST['cantidad'] : 0;

    // Seleccionamos con qué cafetera trabajar
    if ($tipo === 'normal') {
        $cafetera = $_SESSION['cafeteraNormal'];
    } else {
        $cafetera = $_SESSION['cafeteraDescafeinado'];
    }

    // Ejecutamos la acción
    switch ($accion) {
        case 'llenar':
            $cafetera->llenarCafetera();
            $mensaje = "Cafetera $tipo llenada.";
            break;
        case 'vaciar':
            $cafetera->vaciarCafetera();
            $mensaje = "Cafetera $tipo vaciada.";
            break;
        case 'servir':
            $mensaje = $cafetera->servirTaza($cantidad);
            break;
        case 'agregar':
            $mensaje = $cafetera->agregarCafe($cantidad);
            break;
    }
}
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Control de Cafeteras POO</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; background-color: #f4f4f4; }
        .container { display: flex; justify-content: center; gap: 20px; margin-top: 20px; }
        .cafetera { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); width: 300px; }
        .normal { border-top: 5px solid #6f4e37; } /* Marrón café */
        .descafeinado { border-top: 5px solid #orange; } /* Naranja */
        h2 { margin-top: 0; }
        .estado { font-weight: bold; color: #333; background: #eee; padding: 10px; border-radius: 5px; margin-bottom: 15px;}
        button { cursor: pointer; padding: 5px 10px; margin: 5px; }
        input[type="number"] { width: 60px; padding: 5px; }
        .mensaje { background-color: #dff0d8; color: #3c763d; padding: 10px; margin: 20px auto; width: 60%; border-radius: 5px; border: 1px solid #d6e9c6; }
    </style>
</head>
<body>

    <h1>☕ Gestión de Cafeteras PHP</h1>

    <?php if ($mensaje): ?>
        <div class="mensaje"><?php echo $mensaje; ?></div>
    <?php endif; ?>

    <div class="container">
        <div class="cafetera normal">
            <h2>Normal</h2>
            <div class="estado">
                <?php echo $_SESSION['cafeteraNormal']->__toString(); ?>
            </div>
            
            <form method="POST">
                <input type="hidden" name="tipo" value="normal">
                <button type="submit" name="accion" value="llenar">Llenar Todo</button>
                <button type="submit" name="accion" value="vaciar">Vaciar</button>
                <hr>
                <input type="number" name="cantidad" placeholder="c.c.">
                <br>
                <button type="submit" name="accion" value="servir">Servir Taza</button>
                <button type="submit" name="accion" value="agregar">Agregar Café</button>
            </form>
        </div>

        <div class="cafetera descafeinado">
            <h2>Descafeinado</h2>
            <div class="estado">
                <?php echo $_SESSION['cafeteraDescafeinado']->__toString(); ?>
            </div>

            <form method="POST">
                <input type="hidden" name="tipo" value="descafeinado">
                <button type="submit" name="accion" value="llenar">Llenar Todo</button>
                <button type="submit" name="accion" value="vaciar">Vaciar</button>
                <hr>
                <input type="number" name="cantidad" placeholder="c.c.">
                <br>
                <button type="submit" name="accion" value="servir">Servir Taza</button>
                <button type="submit" name="accion" value="agregar">Agregar Café</button>
            </form>
        </div>
    </div>
    
    <p><small><a href="index.php?reset=1" onclick="<?php session_destroy(); ?>">Reiniciar simulación</a></small></p>

</body>
</html>