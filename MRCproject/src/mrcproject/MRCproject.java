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
        //System.out.print(prueba.toString());// se imprime lo cargado
        prueba.add_inicio();
        prueba.add_final();
        prueba.calcular_IC_TC();
        System.out.print(prueba.toString());// se imprime lo cargado
    }
    
}
