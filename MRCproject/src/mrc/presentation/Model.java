/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrc.presentation;

import java.util.Observable;
import mrc.logic.Actividad;
import mrc.logic.Proyecto;

/**
 *
 * @author Estudiante
 */
public class Model extends Observable {

    Proyecto proyecto;

    public void setP(Proyecto p) {
        this.proyecto = p;
        this.setChanged();
        notifyObservers(null);
    }

    public Proyecto getP() {
        return proyecto;
    }

    public Model() {
        proyecto = new Proyecto();
    }

    public void agregarActividad(Actividad a) throws Exception {
        proyecto.agregarActividad(a);
        setChanged();
        notifyObservers(null);
    }
}
