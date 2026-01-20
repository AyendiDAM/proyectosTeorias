<?php

    require '../modelos/Persona.php';
    session_start();
    
    $personas=$_SESSION['personas'];
    
    if (isset($_REQUEST['accion'])){
        $boton=$_REQUEST['accion'];
        if ($boton=="crear") {
            $nombre=$_REQUEST['nombre'];
            $edad=$_REQUEST['edad'];
            $persona=new Persona($nombre,$edad);
            
            array_push($personas, $persona);
            
            $_SESSION['personas']=$personas;
            
            //creo la cookie con el nombre de la ultima persona introducida
            //tiempo de vida de 3 minutos (60 * 3)
            //en el path pongo desde la raiz del proyecto
            setcookie('ultimo', $persona->getNombre(), time() + 180 , '/');
            
            $_SESSION['mensaje']="Alta de persona efectuada";
            header("Location:../vistas/vistaMensaje.php");
        }
    }
    else {
        $_SESSION['error']="No se ha pulsado ningún botón";
        header("Location:../vistas/vistaError.php");
    }

?>