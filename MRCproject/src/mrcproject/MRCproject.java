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
        Proyecto prueba = new Proyecto(new Archivos().carga("datos.xml"));//carga el archivo
        prueba.add_inicio();
        prueba.add_final();
        //prueba.calculaholgura();
        prueba.rutaCritica();
        System.out.print(prueba.Prueba_inicial());// se imprime lo cargado
        
    }
    
}
