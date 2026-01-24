/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author ayend
 */
public class Voto {
    private int id_voto;
   private int id_localidad;
   private int id_partido;
   private int id_eleccion;
   private Date fecha;

    public Voto(int id_voto, int id_localidad, int id_partido, int id_eleccion, Date fecha) {
        this.id_voto = id_voto;
        this.id_localidad = id_localidad;
        this.id_partido = id_partido;
        this.id_eleccion = id_eleccion;
        this.fecha = fecha;
    }

   
   
    public int getId_voto() {
        return id_voto;
    }

    public void setId_voto(int id_voto) {
        this.id_voto = id_voto;
    }

    public int getId_localidad() {
        return id_localidad;
    }

    public void setId_localidad(int id_localidad) {
        this.id_localidad = id_localidad;
    }

    public int getId_partido() {
        return id_partido;
    }

    public void setId_partido(int id_partido) {
        this.id_partido = id_partido;
    }

    public int getId_eleccion() {
        return id_eleccion;
    }

    public void setId_eleccion(int id_eleccion) {
        this.id_eleccion = id_eleccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
   
    
}
