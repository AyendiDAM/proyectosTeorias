<?php 
require_once 'Empleado.php';

// Validar que los datos vengan por POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nombre = trim($_POST['nombre'] ?? '');
    $dni = trim($_POST['dni'] ?? '');

    // Validar que DNI sea numérico y tenga hasta 8 cifras
    if ($nombre === '' || !ctype_digit($dni) || strlen($dni) > 8) {
        die("Datos inválidos. <a href='index.php'>Volver</a>");
    }

    // Crear objeto Persona
    $persona = new Persona($nombre, (int)$dni);

    // Mostrar datos
    echo "<h1>Registro completado</h1>";
    echo "<p>{$persona}</p>";
    echo "<a href='index.php'>Registrar otra persona</a>";
} else {
    echo "Acceso no permitido.";
}

?>