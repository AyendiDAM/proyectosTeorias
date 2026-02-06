   
   
   
   
<?php

   require 'estilos.php';

    require '../modelos/Contacto.php';
        require '../dao/ConexionBBDD.php';


session_start();
//recuperar atributo de session


if(isset($_REQUEST['buscar'])){
      $id=$_REQUEST['seleccionado'];
       
  

        try{
        $bbdd=new ConexionBBDD();
        $contacto=$bbdd->buscar_contacto_id($id);
        $_SESSION['contacto']=serialize($contacto);

        if($numero==1){
            $_SESSION['mensaje']="se ha modificado el contacto";
      
        }else{
            $_SESSION['error']="no se ha modificado";
              

        }

        }catch(mysqli_sql_exception $ex){
   $_SESSION['error']="Error sql";
       
        }finally{
$bbdd->cerrar_conexion();
        }

}if(isset($_REQUEST['eliminar'])){
  
        $id_contacto=$contacto->getId();

        try{
        $bbdd=new ConexionBBDD();
        $numero=$bbdd->eliminar_tabla($id_contacto);

        if($numero==1){
            $_SESSION['mensaje']="se ha modificado el contacto";
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

}

else{
       $_SESSION['error']="No se ha pulsado ningun boton";
       
}

?>


   
 <?php  if(isset($_REQUEST['buscar'])){ ?>
   
   <div class="form-container">
        <h1>Formulario de Contacto</h1>
        <form method="post" action="../controladores/procesarNuevoContacto.php">
            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre" id="nombre" value="<?php echo $contacto->getNombre() ?>" required>
            
            <label for="apellidos">Apellidos:</label>
            <input type="text" name="apellidos" id="apellidos"  required>
            
            <label for="telefono">Teléfono:</label>
            <input type="text" name="telefono" id="telefono" required>
            
            <button type="submit" name="accion">Guardar Contacto</button>
            
            <br>
            <br>
            <br>
            <center>
            <a href="../index.php">Volver a inicio</a>
            </center>
        </form>
    </div>
     <?php
 }
     ?>

      <?php  if(isset($_REQUEST['eleiminar'])){ ?>
   
  <h1>Hola mundo</h1>
     <?php
 }
     ?>