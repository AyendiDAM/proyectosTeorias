
<?php

//clase persona creada
class Persona{
//atributos 
 private $nombre;
 private $dni;

 //contructor
 public function __construct($nombre,$dni) {
    $this->nombre=$nombre;
    $this->dni=$dni;
 }

//metodo calcular dni

public function calculaLetra($dni){
$letras = [
            "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B",
            "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"
        ];

    //paso 1 dividir entre 23
    $divicionNumDni=$dni%23;

    return $letras[$divicionNumDni]

}

   // 6. Visualizar datos
    public function __toString() {
        return "Nombre: {$this->nombre} Dni: {$this->dni}";
    }
}

?>