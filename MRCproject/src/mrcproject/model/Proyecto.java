package mrcproject.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ExtremeTech
 */
public class Proyecto {

    // <editor-fold desc="Constructores" defaultstate="collapsed">
    public Proyecto(HashMap<String, Actividad> actividades) {
        this.actividades = actividades;
    }
    // </editor-fold>
    // <editor-fold desc="Metodos" defaultstate="collapsed">

    public HashMap<String, Actividad> getActividades() {
        return actividades;
    }

    public void calculaholgura() {
        calcular_IC_TC();
        calcular_IL_TL();

    }

    public void calcular_IC_TC() {
        ArrayList<Actividad> visitados = new ArrayList();
        List<Actividad> cola = new ArrayList<>();
        Actividad inicio = actividades.get("n_inicio");
        Actividad temp;
        add_cola(inicio, cola);
        visitados.add(inicio);
        while (!cola.isEmpty()) {
            temp = cola.remove(0);
            visitados.add(temp);
            temp.setIC(buscamayor(temp));
            temp.setTC(temp.getIC() + temp.getDtime());
            add_cola(temp, cola);
        }
    }

    public void calcular_IL_TL() {
        
    }
    
    public void add_cola(Actividad a,List<Actividad> cola){
        Actividad temp;
        for (Relacion relini : a.getSalidas()) {
            temp = actividades.get(relini.getDestino());
            cola.add(temp);
        }
    }

    public int buscamayor(Actividad act) {
        int mayor = 0;
        Actividad temp;
        for (Relacion r : act.getEntradas()) {
            temp = actividades.get(r.getSalida());
            if (mayor <= temp.getTC()) {
                mayor = temp.getTC();
            }
        }
        return mayor;
    }

    public void add_inicio() {
        Actividad n_inicio = new Actividad("n_inicio", 0);
        Set<String> keys = actividades.keySet();
        Actividad a;
        Relacion r;
        for (String key : keys) {
            a = actividades.get(key);
            if (a.getEntradas().isEmpty()) {
                r = new Relacion("n_inicio", a.getName());
                n_inicio.getSalidas().add(r);
                a.getEntradas().add(r);
            }
        }
        actividades.put("n_inicio", n_inicio); //agrega la actividad
    }

    public void add_final() {
        Actividad n_final = new Actividad("n_final", 0);
        Set<String> keys = actividades.keySet();
        Actividad a;
        Relacion r;
        for (String key : keys) {
            a = actividades.get(key);
            if (a.getSalidas().isEmpty()) {
                r = new Relacion(a.getName(), "n_final");
                n_final.getEntradas().add(r);
                a.getSalidas().add(r);
            }
        }
        actividades.put("n_final", n_final);
    }

    @Override
    public String toString() {
        StringBuilder str;
        str = new StringBuilder().append("Actividad     Duracion      Siguiente        Anterior \n");
        actividades.forEach((k, v)
                -> str.append(v.toString()));
        return str.toString();
    }
    
    public String Prueba_inicial(){
        StringBuilder str;
        str = new StringBuilder().append("id\ttiempo\tIC\tTC\n");
        actividades.forEach((k, v)
                -> str.append(v.Prueba_inicial()).append("\n"));
        return str.toString();
    }

    // </editor-fold>
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private final HashMap<String, Actividad> actividades;//lista para las entradas
    // </editor-fold>
}
