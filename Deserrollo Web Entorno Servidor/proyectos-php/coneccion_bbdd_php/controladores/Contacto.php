<?php

class Contacto{
    private $id;
    private $nombre;
    private $apellidos;
    private $telefono;

    public function __construct($nombre,$telefono,$apellidos){
        $this->nombre=$nombre;
          $this->apellidos=$apellidos;
            $this->telefono=$telefono;
    }

    public function setId()  {

         $this->id;
    }
        public function getId()  {

        return $this->id;
    }

        public function getNombre()  {

        return $this->nombre;
    }
          public function getTelefono()  {

        return $this->telefono;
    }
          public function getApellidos()  {

        return $this->apellidos;
    }
    public function __toString(){
        $texto="<br>Nombre: ".$this->nombre."  ".$this->apellidos."  -telefono: ".$this->telefono;

        return $texto;
    }

}

?>