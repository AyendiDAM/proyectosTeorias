<?php
    
    require '../dao/ConexionBBDD.php';
    session_start();
    
    //gestionar las opciones principales
    if (isset($_REQUEST['accion'])){
        $boton=$_REQUEST['accion'];
        try{
            $bbdd=new ConexionBBDD();
            
            switch ($boton){
                case 'crear_tabla':
                    $bbdd->crear_tabla_contactos();
                    $bbdd->cerrar_conexion();
                    $_SESSION['mensaje']="Tabla contactos creada";
                    header("Location:../vistas/vistaMensajes.php");
                    break;
                case 'anadir_contacto':
                    header("Location:../vistas/altaContacto.php");
                    break;
                case 'listar_contactos':
                    header("Location:../vistas/verContactos.php");
                    break;
                case 'modificar_contacto':
                    header("Location:../vistas/seleccionarContacto.php");
                    break;
                
            }
    
        } catch (mysqli_sql_exception $ex) {
            $_SESSION['error']=$ex->getMessage();
            header("Location:../vistas/vistaError.php");
        }
    }// del if
    else {
        $_SESSION['error']="No se ha pulsado ningún botón";
        header("Location:../vistas/vistaError.php");
    }



?>

