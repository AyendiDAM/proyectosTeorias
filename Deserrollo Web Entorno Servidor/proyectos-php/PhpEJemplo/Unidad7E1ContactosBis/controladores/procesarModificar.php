<?php
    require '../modelos/Contacto.php';
    require '../dao/ConexionBBDD.php';
    
    session_start();

    if (isset($_REQUEST['accion'])){
        $nombre=$_REQUEST['nombre'];
        $apellidos=$_REQUEST['apellidos'];
        $telefono=$_REQUEST['telefono'];
        $nuevo_contacto=new Contacto($nombre,$apellidos,$telefono);
        try{
            $bbdd=new ConexionBBDD();
            $bbdd->insertar_nuevo_contacto($nuevo_contacto);
            $bbdd->cerrar_conexion();
            $_SESSION['mensaje']="Contacto añadido a la tabla";
            header("Location:../vistas/vistaMensajes.php");
            
        } catch (mysqli_sql_exception $ex) {
            $_SESSION['error']=$ex->getMessage();
            header("Location:../vistas/vistaError.php");
        }
    }
    else {
        $_SESSION['error']="No se ha pulsado ningún botón";
        header("Location:../vistas/vistaError.php");
    }

?>