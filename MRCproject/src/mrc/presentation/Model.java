package mrc.presentation;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import mrc.data.Archivos;
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
    public void addObserver(Observer o) {
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

    public void abrirArchivo(String ruta) {
        Proyecto p = new Proyecto(new Archivos().carga(ruta));
        this.setP(p);
        System.out.print("\n" + p.rutaCritica() + "\n\n");// se imprime lo cargado
        System.out.print(p.toString());// se imprime lo cargado
    }
    
    public void guardarArchivo(String ruta){
        new Archivos().generar(ruta, proyecto.getActividades());
    }
    
    public void limpiarProyecto(){
        this.proyecto = new Proyecto();
        setChanged();
        notifyObservers(null);
    }
    
    public void relacionar(String a, String b) throws Exception{
        
        this.proyecto.relacionar(a, b);
        setChanged();
        notifyObservers(null);
    }
}
