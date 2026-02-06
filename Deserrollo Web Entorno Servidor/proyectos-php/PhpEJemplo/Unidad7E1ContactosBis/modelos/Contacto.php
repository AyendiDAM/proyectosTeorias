<?php


class Contacto {
   private $id;
   private $nombre;
   private $apellidos;
   private $telefono;
   
   public function __construct($nombre, $apellidos,$telefono){
       $this->nombre=$nombre;
       $this->apellidos=$apellidos;
       $this->telefono=$telefono;
   }
   
   public function setId($id){
       $this->id=$id;
   }
   
   public function getId() {
       return $this->id;
   }

   public function getNombre() {
       return $this->nombre;
   }
   public function setNombre($id){
       $this->nombre=$id;
   }

   public function getApellidos() {
       return $this->apellidos;
   }
      public function setApellidos($id) {
      $this->apellidos=$id;
   }

   public function getTelefono() {
       return $this->telefono;
   }
      public function setgetTelefono($id) {
   $this->telefono=$id;
   }

   public function __toString() {
       $texto= "<br>NOMBRE: ".  $this->nombre. " ".$this->apellidos
               . " - TELÉFONO: " . $this->telefono;
       return $texto;      
   }
   
}
