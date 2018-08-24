package mrc;

import mrc.data.Archivos;
import mrc.logic.Proyecto;
import mrc.presentation.Controller;
import mrc.presentation.Model;
import mrc.presentation.VentanaMRC;

/*
  * @author Esteban Espinoza Fallas   402290345
  * @author Carlos Vargas Alfaro      402170927   
 */
public class MRCproject {

    public static void main(String[] args) throws Exception {
        try {
            VentanaMRC view = new VentanaMRC();
            Model model = new Model();
            view.setModel(model);
            Controller controller = new Controller(model, view);
            view.setVisible(true);
        } catch (Exception e) {
            System.err.println("El programa no puede Ejecutarse!!");
        }
    }
}
