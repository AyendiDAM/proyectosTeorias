

<?php

//para reciger valores y formulario

//formulario
//deserializar
require_once '../modelos/Contacto.php';

//para modificar la bbdd

require_once '../dao/Connecion.php';

session_start();
//recuperar atributo de session

$contacto=unserialize($_SESSION['contacto']);//buscamos en nuestra session el id

//la base de datos introducir

try{
  $bbdd=new ConexionBBDD();
  $numero=$bbdd->modificarContacto($id_usuario,$nombre_nuevo);
  if($numero===1){
    $_SESSION['mensaje']="se ha modificado el contacto";
    header('Location:url');
  }else{
    $_SESSION['error']="no se ha modificado";
    header('Location:url');
  }

}catch(mysqli_sql_exception $ex){
$_SESSION['error']="sql error";
header('Location:../buscar vista');

}finally{

}

//preguntamos si nos han pulsado el boton

if(isset($_REQUEST['accion'])){
//recojemos los valores que nos han enviado

//nombre
$nombre_nuevo=$_REQUEST['nombre'];

//rediresionar

$_SESSION['error']="No se ha encontrado";
header('Location:../vistas/vistaError.php');

}

class ConexionBBDD{
    private $host;
     private $usuario;
      private $pass;
       private $baseDatos;
        private $conexion;

        
    public function __construct(){
        $this->host='localhost';
          $this->usuario='root';
            $this->pass='1234';
            $this->baseDatos='bbdd_arr_contactos';
            $this->conexion=new mysqli($this->host,$this->usuario,$this->pass,$this->baseDatos);
            //comprobamos la conexion

            $numero_error=$this->conexion->connect_errno;
                $mensaje_error=$this->conexion->connect_errno;

      if($numero_error!=0){
        $mensaje="codigo de error".$mensaje_error." ".$numero_error;
        throw new mysqli_sql_exception($mensaje);
      }


     
    }
     public function cerrar_conexion(){
        $this->conexion->close();
      }

      //crear la tabla contacto

      public function crear_tabla_contacto(){
        $order="CREATE TABLE IF NOT EXISTS contactos (id INT AUTO_INCREMENT,PRIMARY KEY(id),nombre VARCHAR(20),apellidos VARCHAR(20),telefono VARCHAR(20);";
        $this->conexion->query($orden);

        if($this->conexion->connect_errno!=0){
                    throw new mysqli_sql_exception("no se ha creado la tabla". $this->conexion->ftp_connect);
        }

      }
    }