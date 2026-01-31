<?php

include_once 'conectar.php'; 

$dni = $_GET['dni']; 

$sql = "SELECT * FROM empleados WHERE dni = '$dni'"; 
$result = $conn->query($sql); 

if ($result->num_rows > 0) { 

$row = $result->fetch_assoc(); 

echo json_encode($row); //devolucion
} else { 

echo json_encode(['mensaje' => 'No se encontró ningún empleado con ese 
DNI']); } 

$conn->close(); ?> 