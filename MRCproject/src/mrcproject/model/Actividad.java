package mrcproject.model;

import java.util.ArrayList;

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

    public ArrayList<Actividad> getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayList<Actividad> entradas) {
        this.entradas = entradas;
    }

    public ArrayList<Actividad> getSalidas() {
        return salidas;
    }

    public void setSalidas(ArrayList<Actividad> salidas) {
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
        StringBuilder str = new StringBuilder();
        //hacer el toString
        return str.toString();
    }
    
    public String Prueba_inicial(){
        StringBuilder str = new StringBuilder();
        str.append(this.name).append("\t").append(this.dtime).append("\t").append(this.IC)
                .append("\t").append(this.TC).append("\t").append(this.TL).append("\t")
                .append(this.IL).append("\t").append(this.holgura);
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
    private ArrayList<Actividad> entradas;//predecesor          lista para las entradas
    private ArrayList<Actividad> salidas;// sucesor             lista para las salidas
    private int holgura;//tiempo de retraso para no atrasar el proyecto

    // </editor-fold>
}
