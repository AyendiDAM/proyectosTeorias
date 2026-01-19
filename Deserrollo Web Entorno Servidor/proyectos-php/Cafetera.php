<?php

class Cafetera {
    // Atributos privados (Encapsulamiento)
    private $capacidadMaxima;
    private $cantidadActual;

    // 1. Constructor: Inicia vacía y con 1000 de máximo
    public function __construct() {
        $this->capacidadMaxima = 1000;
        $this->cantidadActual = 0;
    }

    // 2. Llenar cafetera al máximo
    public function llenarCafetera() {
        $this->cantidadActual = $this->capacidadMaxima;
    }

    // 3. Servir taza (Lógica inteligente)
    public function servirTaza($cantidad) {
        if ($this->cantidadActual >= $cantidad) {
            // Hay suficiente café
            $this->cantidadActual -= $cantidad;
            return "Se ha servido una taza de $cantidad c.c.";
        } else {
            // No alcanza, servimos lo que queda
            $servido = $this->cantidadActual;
            $this->cantidadActual = 0; // Se queda vacía
            return "No alcanzaba para llenar la taza. Se sirvió el remanente de $servido c.c.";
        }
    }

    // 4. Vaciar cafetera
    public function vaciarCafetera() {
        $this->cantidadActual = 0;
    }

    // 5. Agregar café (Validando el tope)
    public function agregarCafe($cantidad) {
        $espacioLibre = $this->capacidadMaxima - $this->cantidadActual;
        
        if ($cantidad <= $espacioLibre) {
            $this->cantidadActual += $cantidad;
            return "Se agregaron $cantidad c.c. de café.";
        } else {
            // Si intenta echar más del máximo, solo llenamos hasta el borde
            $this->cantidadActual = $this->capacidadMaxima;
            return "Se desbordaría. Se llenó la cafetera hasta su capacidad máxima (1000 c.c).";
        }
    }

    // 6. Visualizar datos
    public function __toString() {
        return "☕ Capacidad Máxima: {$this->capacidadMaxima} c.c. | ✅ Cantidad Actual: {$this->cantidadActual} c.c.";
    }

    // Getters (para poder leer los valores desde fuera si hace falta)
    public function getCantidadActual() {
        return $this->cantidadActual;
    }
    
    public function getCapacidadMaxima() {
        return $this->capacidadMaxima;
    }
}
?>