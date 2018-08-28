package mrc.presentation;

import mrc.data.Archivos;
import mrc.logic.Actividad;

/**
 * @author Esteban Espinoza Fallas 402290345
 * @author Carlos Vargas Alfaro 402170927
 */
public class Controller {
    Model model;
    VentanaMRC view;

    public Controller(Model model, VentanaMRC view) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }
    
    public void agregarActividad(String id,int duracion,int x,int y) throws Exception{
        Actividad a = new Actividad(id, duracion, x, y);
        model.agregarActividad(a);
    }
    
    public void abrirarchivo(String ruta) throws Exception{
        model.abrirArchivo(ruta);
    }
    
    public void guardarArchivo(String ruta){
       model.guardarArchivo(ruta);
    }
    
    public void limpiarProyecto(){
        model.limpiarProyecto();
    }
    
    public void Relacionar(String a, String b) throws Exception{
        model.relacionar(a, b);
    }
}
