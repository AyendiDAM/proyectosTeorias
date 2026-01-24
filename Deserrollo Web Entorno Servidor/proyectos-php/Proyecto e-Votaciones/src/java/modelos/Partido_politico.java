/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author ayend
 */
public class Partido_politico {
    private int id;
    private String nombre;
    private String siglas;
    private String logo;

    
        public Partido_politico(int id,String nombre,String siglas) {
        this.nombre = nombre;
        this.siglas = siglas;
        this.id = id;
    }
    public Partido_politico(String nombre, String siglas, String logo) {
        this.nombre = nombre;
        this.siglas = siglas;
        this.logo = logo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
