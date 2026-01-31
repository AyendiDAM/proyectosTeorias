<?php

$servidor   = "localhost";
$usuario    = "admin";      
$password   = "1234";        
$base_datos = "prueba";    


$conn = new mysqli($servidor, $usuario, $password, $base_datos);


if ($conn->connect_error) {
    die("Fallo en la conexión: " . $conn->connect_error);//buscar que es
}

$conn->set_charset("utf8");
?>