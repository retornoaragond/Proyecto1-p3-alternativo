package mrcproject;

import mrcproject.model.Archivos;
import mrcproject.model.Proyecto;

/**
 *
 * @author Esteban Espinoza Fallas
 * @author Carlos 
 */
public class MRCproject {

    public static void main(String[] args) throws Exception {
        Proyecto prueba = new Proyecto(new Archivos().carga("datos3.xml"));//carga el archivo
        System.out.print("\n"+prueba.rutaCritica()+"\n\n");// se imprime lo cargado
        System.out.print(prueba.Prueba_inicial());// se imprime lo cargado
        
    }
    
}
