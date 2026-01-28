/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author ayend
 */
public class Comunidad_autonoma {
    private int id_comunidad;
    private String nombre;

    public Comunidad_autonoma(int id_comunidad, String nombre) {
        this.id_comunidad = id_comunidad;
        this.nombre = nombre;
    }

    public int getId_comunidad() {
        return id_comunidad;
    }

    public void setId_comunidad(int id_comunidad) {
        this.id_comunidad = id_comunidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
