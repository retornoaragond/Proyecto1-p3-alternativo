package mrc.presentation;

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
}
