
<?php

session_start();

//para reciger valores y formulario

//formulario
//deserializar
    require '../modelos/Contacto.php';

//para modificar la bbdd


    require '../dao/ConexionBBDD.php';

    $contacto=unserialize($_SESSION['contacto']);



session_start();
//recuperar atributo de session
if(isset($_REQUEST['accion'])){
     $contacto->setNombre(  $_REQUEST['nombre']);
       $contacto->setApellidos($_REQUEST['apellidos']);
        $contacto->setgetTelefono($_REQUEST['telefono']);

     

        try{
        $bbdd=new ConexionBBDD();
        $numero=$bbdd->insertar_nuevo_contacto($contacto);

        if($numero==1){
            $_SESSION['mensaje']="Contacto añadido";
            header('Location:../vistas/vistaMensajes.php');
        }else{
            $_SESSION['error']="no se ha modificado";
                 header('Location:../vistas/vistaError.php');

        }

        }catch(mysqli_sql_exception $ex){
   $_SESSION['error']="Error sql";
                 header('Location:../vistas/vistaError.php');
        }finally{
$bbdd->cerrar_conexion();
        }

}else{
       $_SESSION['error']="No se ha pulsado ningun boton";
                 header('Location:../vistas/vistaError.php');
}

?>