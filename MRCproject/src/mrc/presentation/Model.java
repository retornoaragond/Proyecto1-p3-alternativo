package mrc.presentation;

import java.util.Observable;
import java.util.Observer;
import mrc.logic.Actividad;
import mrc.logic.Proyecto;

/**
 * @author Esteban Espinoza Fallas 402290345
 * @author Carlos Vargas Alfaro 402170927
 */

public class Model extends Observable {

    Proyecto proyecto;
public Model() {
        proyecto = new Proyecto();
    }


    public void setP(Proyecto p) {
        this.proyecto = p;
        this.setChanged();
        notifyObservers(null);
    }

    public Proyecto getPoryect() {
        return proyecto;
    }

    @Override
    public void addObserver(Observer o){
        super.addObserver(o);
        setChanged();
        notifyObservers(null);
    }

    public void agregarActividad(Actividad a) throws Exception {
        proyecto.agregarActividad(a);
        //actualizar las rutas
        setChanged();
        notifyObservers(null);
    }
}
