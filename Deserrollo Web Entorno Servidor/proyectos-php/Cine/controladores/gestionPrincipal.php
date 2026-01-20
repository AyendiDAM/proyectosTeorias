<?php
    /* 
     * Gestión de las opciones principales a realizar
     */

    //trabajamos con sesiones 
    session_start();
    
    if (!isset($_SESSION['personas'])) {
        $personas=array();
        $_SESSION['personas'] = $personas;
    }
    if (isset($_REQUEST['accion'])){
        $boton=$_REQUEST['accion'];
        switch ($boton) {
                case "crear":
                    header("Location:../vistas/altaPersona.php");
                    break;
                case "ver":
                    header("Location:../vistas/verPersonas.php");
                    break;
                case "salir":
                    header("Location:../vistas/salir.php");
                    break;
                default:
                    break;
        }
    }
    else {
        $_SESSION['error']="No se ha pulsado ningún botón";
        header("Location:../vistas/vistaError.php");
    }

?>