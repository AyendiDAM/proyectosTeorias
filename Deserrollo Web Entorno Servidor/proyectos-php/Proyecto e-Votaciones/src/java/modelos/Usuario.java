/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author ayend
 */
public class Usuario {
    private String dni;
    private String password;
    private String rol;
    private int voto;
    private int localidad;

    public Usuario(String dni, String password, String rol,int localidad,int voto) {
        this.dni = dni;
        this.password = password;
        this.rol = rol;
        this.voto = voto;
        this.localidad=localidad;
    }

    public int getLocalidad() {
        return localidad;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    @Override
    public String toString() {
    String text="Usuario: ";
    text+="\nDNI: "+this.dni+" Tipo de usuario "+this.rol+" ha votado: "+this.voto;
    return text;
    }
    
    
}
