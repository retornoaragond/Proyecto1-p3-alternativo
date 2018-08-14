package mrcproject;

import mrcproject.model.Archivos;
import mrcproject.model.Proyecto;

/**
 * @author Esteban Espinoza Fallas
 * @author Carlos Vargas Alfaro
 */
public class MRCproject {

    public static void main(String[] args) throws Exception {
        try {
            Proyecto prueba = new Proyecto(new Archivos().carga("datos3s.xml"));//carga el archivo
            System.out.print("\n" + prueba.rutaCritica() + "\n\n");// se imprime lo cargado
            System.out.print(prueba.Prueba_inicial());// se imprime lo cargado
        } catch (Exception e) {
            System.err.println("El programa no puede ejecutarse no se ha encontrado el archivo");
        }
    }
}
