package mrcproject.model;

import java.util.ArrayList;

/**
 * @author Esteban Espinoza Fallas 402290345
 * @author Carlos Vargas Alfaro 402170927
 */
public class Actividad {

    // <editor-fold desc="Constructores" defaultstate="collapsed">
    public Actividad(String name, int dtime) {
        this.name = name;
        this.dtime = dtime;
        this.IC = 0;
        this.IL = 0;
        this.entradas = new ArrayList<>();
        this.salidas = new ArrayList<>();
    }
    // </editor-fold>

    // <editor-fold desc="Metodos" defaultstate="collapsed">
    // métodos sets 
    public void setName(String name) {
        this.name = name;
    }

    public void setDtime(int dtime) {
        this.dtime = dtime;
    }

    public void setIC(int IC) {
        this.IC = IC;
    }

    public void setIL(int IL) {
        this.IL = IL;
    }

    public void setEntradas(ArrayList<Actividad> entradas) {
        this.entradas = entradas;
    }

    public void setSalidas(ArrayList<Actividad> salidas) {
        this.salidas = salidas;
    }

    //métodos gets 
    public int getDtime() {
        return dtime;
    }

    public String getName() {
        return name;
    }

    public int getIC() {
        return IC;
    }

    public int getTC() {
        return this.IC + this.dtime;
    }

    public int getIL() {
        return IL;
    }

    public int getTL() {
        return this.IL + this.dtime;
    }

    public ArrayList<Actividad> getEntradas() {
        return entradas;
    }

    public ArrayList<Actividad> getSalidas() {
        return salidas;
    }

    public int getHolgura() {    // método que cálcula la holgura de cada actividad 
        return this.IL - this.IC;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.name).append("\t").append(this.dtime).append("\t").append(this.IC)
                .append("\t").append(this.getTC()).append("\t").append(this.IL).append("\t")
                .append(this.getTL()).append("\t").append(this.getHolgura());
        return str.toString();
    }

    // </editor-fold>
    
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private String name;// nombre o sigla 
    private int dtime;//tiempo de duración de la actividad
    private int IC;//Inicio más cercano
    private int IL;//Inicio más lejano
    private ArrayList<Actividad> entradas;//predecesor          lista para los Antecesores
    private ArrayList<Actividad> salidas;// sucesor             lista para los Sucesores
    // </editor-fold>
}
