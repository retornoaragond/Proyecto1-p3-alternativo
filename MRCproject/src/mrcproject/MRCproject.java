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
        //System.out.print(prueba.toString());// se imprime lo cargado
        prueba.calcular_IC_TC();
        prueba.calcular_IL_TL();
        prueba.calculaholgura();
        prueba.rutaCritica();
        System.out.print(prueba.Prueba_inicial());// se imprime lo cargado
        
    }
    
}
