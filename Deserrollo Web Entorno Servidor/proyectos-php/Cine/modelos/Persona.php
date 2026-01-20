<?php

/*
 * Clase Persona
 */


class Persona {
    private $nombre;
    private $edad;
    
    
    public function __construct($nombre, $edad) {
        $this->nombre = $nombre;
        $this->edad = $edad;
    }
    
       
    
    public function __toString(){
        return "<br>DATOS DE LA PERSONA".
               "<br>NOMBRE: " . $this->nombre . 
               "<br>EDAD: ". $this->edad;
    }


    public function getNombre() {
        return $this->nombre;
    }

    public function getEdad() {
        return $this->edad;
    }


}
