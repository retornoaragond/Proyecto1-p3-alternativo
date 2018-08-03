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

    public Actividad() {   
    }
    
    // </editor-fold>
    
    // <editor-fold desc="Metodos" defaultstate="collapsed">
    public int getIC() {
        return IC;
    }

    public void setIC(int IC) {
        this.IC = IC;
    }

    public int getTC() {
        return TC;
    }

    public void setTC(int TC) {
        this.TC = TC;
    }

    public int getIL() {
        return IL;
    }

    public void setIL(int IL) {
        this.IL = IL;
    }

    public int getTL() {
        return TL;
    }

    public void setTL(int TL) {
        this.TL = TL;
    }

    public ArrayList<Relacion> getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayList<Relacion> entradas) {
        this.entradas = entradas;
    }

    public ArrayList<Relacion> getSalidas() {
        return salidas;
    }

    public void setSalidas(ArrayList<Relacion> salidas) {
        this.salidas = salidas;
    }

    public int getHolgura() {
        return holgura;
    }

    public void setHolgura(int holgura) {
        this.holgura = holgura;
    }
    // </editor-fold>
    
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    
    private int name;// nombre o sigla 
    private int dtime;//tiempo de duracion de la actividad
    private int IC;//Inicio más cercano
    private int TC;//Término más cercano
    private int IL;//Inicio más lejano
    private int TL;//Término más lejano
    private ArrayList<Relacion> entradas;//lista para las entradas
    private ArrayList<Relacion> salidas;//lista para las salidas
    private int holgura;//tiempo de retraso para no atrasar el proyecto
    
    // </editor-fold>
}
