<?php

/*
 * Clase SalaCine
 */

require_once "Persona.php";

class SalaCine {
    private $titulo;
    private $numeroButaca;
  private array $personasCina = [];

    
    
    public function __construct($nombre, $ocupada,Persona $person) {
        $this->titulo = $nombre;
        $this->numeroButaca = $ocupada;
                $this->personasCina[] = $persona;
                
    }
    
    


}
