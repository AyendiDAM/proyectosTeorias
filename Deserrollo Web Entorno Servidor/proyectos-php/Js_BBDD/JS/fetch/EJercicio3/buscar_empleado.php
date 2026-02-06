<?php

include_once 'conectar.php'; 

    session_start();

      if (isset($_REQUEST['accion'])){

$data=json_decode(file_get_contents('php://input'),true);
$dni = $data['dni'];


$sql = "SELECT * FROM empleados WHERE dni = '$dni'"; 
$result = $conn->query($sql); 

if ($result->num_rows > 0) { 

$row = $result->fetch_assoc(); 

echo json_encode($row); //devolucion
} else { 

echo json_encode(['mensaje' => 'No se encontró ningún empleado con ese 
DNI']); } 
 }
$conn->close(); ?> 