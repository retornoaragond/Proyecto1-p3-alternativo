package mrcproject.model;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author ExtremeTech
 */

@XmlRootElement(name = "datos")
public class Grafo {
    // <editor-fold desc="Constructores" defaultstate="collapsed">
    
    // </editor-fold>

    // <editor-fold desc="Metodos" defaultstate="collapsed">
    public int calculaholgura(){
        return 0;
    }
    
    public void calcularholgura1(){
        
    }
    
    public void calcularholgura2(){
        
    }
    // </editor-fold>
    
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private ArrayList<Actividad> Actividades;//lista para las entradas
    private ArrayList<Relacion> Relaciones;//lista de relaciones
    // </editor-fold>
}
