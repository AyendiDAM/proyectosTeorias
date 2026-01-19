<?php

require_once 'Empleado.php';


if ($_SERVER["REQUEST_METHOD"] == "POST") {

    // Recogemos los datos del formulario
    $nombre = $_POST['nombre'];
    $dni = $_POST['dni'];

    // 3. Creamos el objeto usando la clase importada
    $persona = new Persona($nombre, $dni);

    // 4. Mostramos el resultado
    echo "<h1>Persona Creada con Éxito</h1>";
    echo $persona; // Llama al __toString()

    // Botón para volver atrás
    echo "<br><br><a href='index.php'>Volver al formulario</a>";

} else {
    echo "Acceso no permitido.";
}
?>