package mrc;

import mrc.data.Archivos;
import mrc.logic.Actividad;
import mrc.logic.Proyecto;
import mrc.presentation.Model;
import mrc.presentation.VentanaMRC;

/*
  * @author Esteban Espinoza Fallas   402290345
  * @author Carlos Vargas Alfaro      402170927   
 */
public class MRCproject {

    public static void main(String[] args) throws Exception {
        try {
            Proyecto prueba = new Proyecto(new Archivos().carga("datos3.xml"));//carga el archivo
            System.out.print("\n" + prueba.rutaCritica() + "\n\n");// se imprime lo cargado
            System.out.print(prueba.Prueba_inicial());// se imprime lo cargado
            new Archivos().generar("datosG3.xml", prueba.getActividades());
            VentanaMRC view = new VentanaMRC();
            Model m = new Model();
            m.setP(prueba);
            view.setModel(m);
            view.setVisible(true);
        } catch (Exception e) {
            System.err.println("El programa no puede Ejecutarse!!");
        }
    }
}
