package mrcproject.model;
import java.util.ArrayList;

/**
 *
 * @author ExtremeTech
 */
public class Actividad {
    
    // <editor-fold desc="Constructores" defaultstate="collapsed">
    public Actividad(int name, int dtime) {
        this.name = name;
        this.dtime = dtime;
        this.IC = -1;
        this.TC = -1;
        this.IL = -1;
        this.TL = -1;
        this.entradas = null;
        this.salidas = null;
    }
    
    public Actividad(int name, int dtime, int IC, int TC, int IL, int TL, ArrayList<Relacion> entradas, ArrayList<Relacion> salidas) {
        this.name = name;
        this.dtime = dtime;
        this.IC = IC;
        this.TC = TC;
        this.IL = IL;
        this.TL = TL;
        this.entradas = entradas;
        this.salidas = salidas;
    }
    // </editor-fold>
    
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    
    private final int name;// nombre o sigla 
    private final int dtime;//tiempo de duracion de la actividad
    private int IC;//Inicio más cercano
    private int TC;//Término más cercano
    private int IL;//Inicio más lejano
    private int TL;//Término más lejano
    private ArrayList<Relacion> entradas;//lista para las entradas
    private ArrayList<Relacion> salidas;//lista para las salidas
    private int holgura;//tiempo de retraso para no atrasar el proyecto
    
    // </editor-fold>
}
