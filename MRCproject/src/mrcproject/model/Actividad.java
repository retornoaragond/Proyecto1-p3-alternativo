package mrcproject.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ExtremeTech
 */
public class Actividad {

    // <editor-fold desc="Constructores" defaultstate="collapsed">
    public Actividad(String name, int dtime) {
        this.name = name;
        this.dtime = dtime;
        this.IC = 0;
        this.TC = 0;
        this.IL = 0;
        this.TL = 0;
        this.entradas = new ArrayList<>();
        this.salidas = new ArrayList<>();
        //this.predecesor= new ArrayList();
    }
    // </editor-fold>
    // <editor-fold desc="Metodos" defaultstate="collapsed">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDtime() {
        return dtime;
    }

    public void setDtime(int dtime) {
        this.dtime = dtime;
    }

    public int getIC() {
        return IC;
    }

    

    public int getTC() {
        return TC;
    }

    public void setIC(int IC) {
        this.IC = IC;
    }
    
   
    public void setTC(int TC) {
        this.TC = TC;
    }

    public void setIL(int IL) {
        this.IL = IL;
    }
    
    public void setTL(int TL) {
        this.TL = TL;
    }
    
    public int getIL() {
        return IL;
    }

    

    public int getTL() {
        return TL;
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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder().append("   ").append(getName()).append("\t\t(").append(getDtime()).append(")\t\t");
        if (!salidas.isEmpty()) {
            salidas.forEach((Relacion r) -> {
                str.append(r.getDestino()).append(". ");
            });
        }else{
            str.append("-");
        }
        str.append("\t\t");
        if (!entradas.isEmpty()) {
            entradas.forEach((Relacion r) -> {
                str.append(r.getSalida()).append(". ");
            });
        }else{
            str.append("-");
        }
        str.append("\n");
        return str.toString();
    }
    
    
    
    // </editor-fold>
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private String name;// nombre o sigla 
    private int dtime;//tiempo de duracion de la actividad
    private int IC;//Inicio más cercano
    private int TC;//Término más cercano
    private int IL;//Inicio más lejano
    private int TL;//Término más lejano
    private ArrayList<Relacion> entradas;//predecesor          lista para las entradas
    private ArrayList<Relacion> salidas;// sucesor            //lista para las salidas
    //private HashMap<String,Actividad> predecesor;
    private int holgura;//tiempo de retraso para no atrasar el proyecto

    // </editor-fold>
}
