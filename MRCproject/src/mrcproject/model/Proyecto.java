package mrcproject.model;

import java.util.HashMap;

/**
 *
 * @author ExtremeTech
 */
public class Proyecto {

    // <editor-fold desc="Constructores" defaultstate="collapsed">
    public Proyecto(HashMap<String, Actividad> actividades) {
        this.actividades = actividades;
    }
    // </editor-fold>
    // <editor-fold desc="Metodos" defaultstate="collapsed">

    public HashMap<String, Actividad> getActividades() {
        return actividades;
    }

    public int calculaholgura() {
        return 0;
    }

    public void calcularholgura1() {

    }

    public void calcularholgura2() {

    }

    @Override
    public String toString() {
        StringBuilder str;
        str = new StringBuilder().append("Actividad     Duracion      Siguiente        Anterior \n");
        actividades.forEach((k, v) -> 
                str.append(v.toString()));
        return str.toString();
    }

    // </editor-fold>
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private final HashMap<String, Actividad> actividades;//lista para las entradas
    // </editor-fold>
}
