/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author ayend
 */
public class ExcepcionMinimoPartidos extends Exception{
    private int insuficiente;

    public ExcepcionMinimoPartidos(int insuficiente) {
        this.insuficiente = insuficiente;
    }
    @Override
    public String getMessage(){
        
        return "Partidos insuficiente "
                + this.insuficiente;
    }
}
