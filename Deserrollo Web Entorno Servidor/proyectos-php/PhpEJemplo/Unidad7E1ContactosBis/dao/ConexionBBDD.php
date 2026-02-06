<?php


class ConexionBBDD {
    private $host;
    private $usuario;
    private $password;
    private $baseDatos;
    private $conexion;
    
    public function __construct(){
        $this->host='localhost';
        $this->usuario='admin';
        $this->password='1234';
        $this->baseDatos='bbdd_ayendi_contactos';
        
        $this->conexion=new mysqli($this->host,$this->usuario,$this->password,$this->baseDatos);
        //Comprobamos la conexion
        $numero_error=$this->conexion->connect_errno;
        $mensaje_error=$this->conexion->connect_error;
        
        if ($numero_error!=0){
            $mensaje="Código de error $numero_error  $mensaje_error";
            throw new mysqli_sql_exception($mensaje);
        }
    }
    
    public function cerrar_conexion(){
        $this->conexion->close();
    }
    
    //crear la tabla contactos
    public function crear_tabla_contactos(){
        $orden = "CREATE TABLE IF NOT EXISTS contactos (id INT(11) AUTO_INCREMENT,"
                . "PRIMARY KEY(id), "
                . "nombre VARCHAR(20),"
                . "apellidos VARCHAR(20), "
                . "telefono VARCHAR(20));";
        $this->conexion->query($orden);
        if ($this->conexion->connect_errno!=0){
            throw new mysqli_sql_exception("No se ha creado la tabla. ".$this->conexion->connect_error);
        }
    }
    
    //metodo para añadir un contacto en la tabla
    public function insertar_nuevo_contacto($nuevo){
        $orden="INSERT INTO contactos (nombre, apellidos, telefono) VALUES (?,?,?);";
        $sentencia=$this->conexion->prepare($orden);
        $sentencia->bind_param("sss", $nuevo->getNombre(),$nuevo->getApellidos(),$nuevo->getTelefono());
        $sentencia->execute();
        $numero=$sentencia->affected_rows;
        if ($sentencia->affected_rows==0){
            throw new mysqli_sql_exception("Llego aquí");
        }
return $numero;
    }   
    

    //metodo que devuelve un array con todos los contactos
    public function get_todos_contactos(){
        $orden="SELECT * FROM contactos";
        $resultado=$this->conexion->query($orden);
        $todos=[]; //$todos=array();
        if ($resultado->num_rows>=1){
            $fila=$resultado->fetch_assoc();
            while ($fila){
                $todos[]=$fila; //array_push($todos,$fila);
                $fila=$resultado->fetch_assoc();
            }
            return $todos;    
        }
        else {
            throw new mysqli_sql_exception("No se han recuperado datos");
        }
    }
    

//modificar contacto
public function modificar_contacto($id_contacto,$nombre_nuevo,$apellido,$telefono){

$order="UPDATE contactos SET nombre=?, apellidos=?,telefono=? WHERE id=?;";

$sentencia=$this->conexion->prepare($order);
$sentencia->bind_param("sssi",$nombre_nuevo,$apellido,$telefono,$id_contacto);
$sentencia->execute();
$numero=$sentencia->affected_rows;
if($numero==0){
    throw new mysqli_sql_exception();
}else {
    return $numero;
}

}


    
    //metodo para buscar un contacto en la tabla por id

    public function buscar_contacto_id($id){
$orden="select * from contactos where id=?;";
        $sentencia=$this->conexion->prepare($orden);//esto indica que es una sentencia preparada
        $sentencia->bind_param("i", $id);//la i indica que es intero que se le pasa

        //ejecutar la orden
    $sentencia->execute();
     $resultado= $sentencia->get_result();
if($sentencia->num_rows!=0){
    throw new mysqli_sql_exception("No se ha encontrado el resultado");

}else{
$fila=$resultado->fetch_assoc();

require_once '../modelos/Contacto.php';

$contacto =new Contacto($fila['nombre'],$fila['apellidos'],$fila['telefono']);
$contacto->setId($fila['id']);
return $contacto;



}

    }
        public function eliminar_tabla($id){
$order="DELETE FROM contactos where id=?;";

$sentencia=$this->conexion->prepare($order);
$sentencia->bind_param("i",$id);
$sentencia->execute();
$numero=$sentencia->affected_rows;
if($numero==0){
    throw new mysqli_sql_exception();
}else {
    return $numero;
}

    }

    
    
}//clase ConexionBBDD
    
    
    
    
    
    
    

