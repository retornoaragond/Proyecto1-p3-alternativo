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
    }
    // </editor-fold>

    // <editor-fold desc="Metodos" defaultstate="collapsed">
    public HashMap<String, Actividad> getActividades() {
        return actividades;
    }
    
    // <editor-fold desc="Ruta Critica" defaultstate="collapsed">
    public List<Actividad> rutaCritica() {
        Actividad inicio = actividades.get("n_inicio");
        Actividad a;

        List<Actividad> ruta = new ArrayList<>();
        add_cola(inicio, ruta);
        while (!ruta.isEmpty() && inicio.getHolgura() == 0) {
            a = ruta.remove(0);
            add_cola(a, ruta);
        }
        return ruta.subList(0, ruta.size());
    }

    public void calculaholgura() {
        if (!hay_ciclo()) {
            calcular_IC_TC();
            calcular_IL_TL();
            Actividad inicio = actividades.get("n_inicio");
            List<Actividad> cola = new ArrayList<>();
            Actividad temp;
            add_cola(inicio, cola);
            while (!cola.isEmpty()) {
                temp = cola.remove(0);
                temp.setHolgura(temp.getTL() - temp.getTC());
                add_cola(temp, cola);
            }
        } else {
            System.out.println("Error: Hay un Ciclo en el grafo");
        }
    }

    public void calcular_IC_TC() {
        List<Actividad> cola = new ArrayList<>();
        ArrayList<Actividad> visitados = new ArrayList();
        Actividad inicio = actividades.get("n_inicio");
        Actividad temp;
        add_cola(inicio, cola);
        visitados.add(inicio);
        while (!cola.isEmpty()) {
            temp = cola.remove(0);
            if (!realizable1(temp, visitados)) {  //si sus antecesores no han sido visitados no se procesa 
                temp.setIC(buscamayor(temp));
                temp.setTC(temp.getIC() + temp.getDtime());
                add_cola(temp, cola);
                visitados.add(temp);
            } else {                            //se reingresa en la cola al final
                cola.add(temp);
            }
        }
    }

    public void calcular_IL_TL() {
        ArrayList<Actividad> visitados = new ArrayList();
        List<Actividad> cola = new ArrayList<>();
        Actividad n_final = actividades.get("n_final");
        Actividad temp;
        add_colaF(n_final, cola);
        visitados.add(n_final);
        while (!cola.isEmpty()) {
            temp = cola.remove(0);
            if (!realizable2(temp, visitados)) {  //si sus antecesores no han sido visitados no se procesa 
                visitados.add(temp);
                temp.setTL(buscamenor(temp));
                temp.setIL(temp.getTL() - temp.getDtime());
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
        int menor = 0;
        for (Actividad a : act.getSalidas()) {
            if (menor <= a.getIC()) {
                menor = a.getIC();
            }
        }
        return menor;
    }
    // </editor-fold>

    // <editor-fold desc="Agregadores" defaultstate="collapsed">
    public void add_inicio() {
        Actividad n_inicio = new Actividad("n_inicio", 0);
        Set<String> keys = actividades.keySet();
        Actividad a;
        for (String key : keys) {
            a = actividades.get(key);
            if (a.getEntradas().isEmpty()) {
                n_inicio.getSalidas().add(a);
                a.getEntradas().add(n_inicio);
            }
        }
        actividades.put("n_inicio", n_inicio); //agrega la actividad
    }

    public void add_final() {
        Actividad n_final = new Actividad("n_final", 0);
        Set<String> keys = actividades.keySet();
        Actividad a;
        for (String key : keys) {
            a = actividades.get(key);
            if (a.getSalidas().isEmpty()) {
                n_final.getEntradas().add(a);
                a.getSalidas().add(n_final);
            }
        }
        actividades.put("n_final", n_final);
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
        str = new StringBuilder().append("id\ttiempo\tIC\tTC\tTL\tIL\tholgura\n");
        actividades.forEach((k, v)
                -> str.append(v.Prueba_inicial()).append("\n"));
        return str.toString();
    }
    // </editor-fold>

    // </editor-fold>
    
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private final HashMap<String, Actividad> actividades;//lista para las entradas
    // </editor-fold>
}
