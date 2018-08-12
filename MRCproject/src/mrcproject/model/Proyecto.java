package mrcproject.model;

import java.util.ArrayList;
import java.util.HashMap;
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
        this.n_i = new Actividad("n_i", 0);
        this.n_f = new Actividad("n_f", 0);
    }
    // </editor-fold>

    // <editor-fold desc="Metodos" defaultstate="collapsed">
    public HashMap<String, Actividad> getActividades() {
        return actividades;
    }

    // <editor-fold desc="Ruta Critica" defaultstate="collapsed">
    public List<Actividad> rutaCritica() {
        if (!hay_ciclo()) {
            calcular_IC();
            calcular_IL();
        } else {
            System.out.println("Error: Hay un Ciclo en el grafo");
        }
        return null;
//        Actividad inicio = actividades.get("n_inicio");
//        Actividad a;
//
//        List<Actividad> ruta = new ArrayList<>();
//        add_cola(inicio, ruta);
//        while (!ruta.isEmpty() && inicio.getHolgura() == 0) {
//            a = ruta.remove(0);
//            add_cola(a, ruta);
//        }
//        return ruta.subList(0, ruta.size());
    }

    public void calcular_IC() {
        List<Actividad> cola = new ArrayList<>();
        ArrayList<Actividad> visitados = new ArrayList();
        Actividad temp;
        add_cola(n_i, cola);
        visitados.add(n_i);
        while (!cola.isEmpty()) {
            temp = cola.remove(0);
            if (!realizable1(temp, visitados)) {  //si sus antecesores no han sido visitados no se procesa 
                temp.setIC(buscamayor(temp));
                add_cola(temp, cola);
                visitados.add(temp);
            } else {                            //se reingresa en la cola al final
                cola.add(temp);
            }
        }
    }

    public void calcular_IL() {
        ArrayList<Actividad> visitados = new ArrayList();
        List<Actividad> cola = new ArrayList<>();
        Actividad temp;
        n_f.setIL(n_f.getIC());
        add_colaF(n_f, cola);
        visitados.add(n_f);
        while (!cola.isEmpty()) {
            temp = cola.remove(0);
            if (!realizable2(temp, visitados)) {  //si sus antecesores no han sido visitados no se procesa 
                visitados.add(temp);
                temp.setIL(buscamenor(temp) - temp.getDtime());
                add_colaF(temp, cola);
            } else {                            //se reingresa en la cola al final
                cola.add(temp);
            }
        }
    }
    // </editor-fold>

    // <editor-fold desc="Encoladores" defaultstate="collapsed">
    public void add_cola(Actividad a, List<Actividad> cola) {
        a.getSalidas().forEach((act) -> {
            cola.add(act);
        });
    }

    public void add_colaF(Actividad a, List<Actividad> cola) {
        a.getEntradas().forEach((act) -> {
            cola.add(act);
        });
    }
    // </editor-fold>

    // <editor-fold desc="Buscadores" defaultstate="collapsed">
    public int buscamayor(Actividad act) {
        int mayor = 0;
        for (Actividad a : act.getEntradas()) {
            if (mayor <= a.getTC()) {
                mayor = a.getTC();
            }
        }
        return mayor;
    }

    public int buscamenor(Actividad act) {
        int menor = Integer.MAX_VALUE;
        for (Actividad a : act.getSalidas()) {
            if (menor >= a.getIL()) {
                menor = a.getIL();
            }
        }
        return menor;
    }
    // </editor-fold>

    // <editor-fold desc="Agregadores" defaultstate="collapsed">
    public void add_inicio() {
        //this.n_i = new Actividad("n_inicio", 0);
        Set<String> keys = actividades.keySet();
        Actividad a;
        for (String key : keys) {
            a = actividades.get(key);
            if (a.getEntradas().isEmpty()) {
                this.n_i.getSalidas().add(a);
                a.getEntradas().add(this.n_i);
            }
        }
        //actividades.put("n_i", this.n_i); //agrega la actividad
    }

    public void add_final() {
        Set<String> keys = actividades.keySet();
        Actividad a;
        for (String key : keys) {
            a = actividades.get(key);
            if (a.getSalidas().isEmpty()) {
                this.n_f.getEntradas().add(a);
                a.getSalidas().add(this.n_f);
            }
        }
        //actividades.put("n_f", this.n_f);
    }
    // </editor-fold>

    // <editor-fold desc="Comprobadores" defaultstate="collapsed">
    public boolean hay_ciclo() {//busca en el grafo si hay un ciclo
        Set<String> keys = actividades.keySet();
        Actividad a;
        for (String key : keys) {
            a = actividades.get(key);
            for (Actividad act : a.getSalidas()) {
                if (act.getSalidas().contains(a)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean realizable1(Actividad a, ArrayList<Actividad> visit) {//busca en los visitados si sus predecesotres ya fueron visitados
        return a.getEntradas().stream().anyMatch((act) -> (!visit.contains(act)));
    }

    public boolean realizable2(Actividad a, ArrayList<Actividad> visit) {//busca en los visitados si sus sucesores ya fueron visitados
        return a.getSalidas().stream().anyMatch((act) -> (!visit.contains(act)));
    }
    // </editor-fold>

    // <editor-fold desc="ToStrings" defaultstate="collapsed">
    @Override
    public String toString() {
        StringBuilder str;
        str = new StringBuilder().append("Actividad     Duracion      Siguiente        Anterior \n");
        actividades.forEach((k, v)
                -> str.append(v.toString()));
        return str.toString();
    }

    public String Prueba_inicial() {
        StringBuilder str;
        str = new StringBuilder().append("id\ttiempo\tIC\tTC\tIL\tTL\tholgura\n");
        actividades.forEach((k, v)
                -> str.append(v.Prueba_inicial()).append("\n"));
        return str.toString();
    }
    // </editor-fold>

    // </editor-fold>
    
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private final HashMap<String, Actividad> actividades;//lista para las entradas
    Actividad n_i;
    Actividad n_f;
    // </editor-fold>
}