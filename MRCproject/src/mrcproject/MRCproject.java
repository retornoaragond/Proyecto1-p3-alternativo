package mrcproject;

import mrcproject.model.Archivos;
import mrcproject.model.Proyecto;

/*
  * @author Esteban Espinoza Fallas   402290345
  * @author Carlos Vargas Alfaro      402170927   
*/
public class MRCproject {

    public static void main(String[] args) throws Exception {
        try {
            Proyecto prueba = new Proyecto(new Archivos().carga("datos.xml"));//carga el archivo
            System.out.print("\n" + prueba.rutaCritica() + "\n\n");// se imprime lo cargado
            System.out.print(prueba.Prueba_inicial());// se imprime lo cargado
        } catch (Exception e) {
            System.err.println("El programa no puede Ejecutarse!!");
        }
    }
}
