package mrc.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Esteban Espinoza Fallas 402290345
 * @author Carlos Vargas Alfaro 402170927
 */
public class Proyecto {

    // <editor-fold desc="Constructores" defaultstate="collapsed">
    public Proyecto(HashMap<String, Actividad> actividades) {
        this.actividades = actividades;
        this.n_i = new Actividad("n_i", 0, 0, 0);
        this.n_f = new Actividad("n_f", 0, 0, 0);
    }

    public Proyecto() {
        this.actividades = new HashMap<>();
        this.n_i = new Actividad("n_i", 0, 0, 0);
        this.n_f = new Actividad("n_f", 0, 0, 0);
    }
    // </editor-fold>

    // <editor-fold desc="Metodos" defaultstate="collapsed">
    
    public HashMap<String, Actividad> getActividades() {
        return actividades;
    }

    // <editor-fold desc="Ruta Critica" defaultstate="collapsed">
    public String rutaCritica() { //método que retorna la o las rutas críticas del proyecto
        add_inicio();
        add_final();
        if (!hay_ciclo()) {
            calcular_IC();
            calcular_IL();
            ArrayList<ArrayList<Actividad>> rutas = new ArrayList<>();//lista de listas para las rutas
            CPM(rutas, n_f, 0);//método para N rutas criticas
            reverse(rutas);   //método que invierte las rutas ya que estan al revés
            return ruta(rutas);
        } else {
            System.out.println("Error: Hay un Ciclo en el grafo");
        }
        return null;
    }

    public void CPM(ArrayList<ArrayList<Actividad>> rutas, Actividad a, int ruta) {//método que calcula N rutas críticas empezando desde el final
        int part = 0;//bandera para saber si hay más caminos que llega a un nodo
        for (Actividad temp : a.getEntradas()) {
            if (!("n_i".equals(temp.getName()))) {//si es el nodo inicial no lo toma encuenta 
                if (temp.getHolgura() == 0) {
                    if ("n_f".equals(a.getName())) {//si son predecesores del final crea una ruta por cada uno
                        ArrayList<Actividad> tempL = new ArrayList<>();
                        tempL.add(temp);
                        rutas.add(tempL);
                        CPM(rutas, temp, rutas.size() - 1);//llamado recursivo
                    } else {
                        if (cant_cami(a) < 2) {//si sus antecesorees con hogura 0 es menor a 1
                            rutas.get(ruta).add(temp);//lo ingresa en esa lista
                            CPM(rutas, temp, ruta);//llamad recursivo
                        } else {
                            if (part == 0) {//si es el primer de los nodos antecesores  
                                ArrayList<Actividad> tempL2 = (ArrayList) rutas.get(ruta).clone();//crea una copia de la ruta actual
                                rutas.add(tempL2);//la copia la ingresa en un nuevo lugar
                                rutas.get(ruta).add(temp);//ingresa el primer antecesor
                                CPM(rutas, temp, ruta);//llamado recursivo
                                part++;//cambia la bandera para ingresar cambiar de ruta
                            } else {
                                rutas.get(++ruta).add(temp);//ingresa en la ruta copiada anteriormente
                                CPM(rutas, temp, rutas.size() - 1);//llamado recursivo
                            }
                        }
                    }
                }
            }
        }
    }

    public int cant_cami(Actividad a) {// método que cuenta la cantidad de nodos antecesores que tienen holgura 0
        int n = 0;
        for (Actividad temp : a.getEntradas()) {
            if (temp.getHolgura() == 0) {
                n++;
            }
        }
        return n;
    }

    public void reverse(ArrayList<ArrayList<Actividad>> rutas) {//da vuelta a las rutas ya que estan invertidas
        rutas.forEach((list) -> {
            Collections.reverse(list);
        });
    }

    public void calcular_IC() {    // método que calcula el inicio más cercano de cada actividad
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

    public void calcular_IL() { // método que calcula el inicio más lejano de cada actividad
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
    public void add_cola(Actividad a, List<Actividad> cola) { // método que agrega en una cola los sucesores de de cada actividad que ingresa
        a.getSalidas().forEach((act) -> {
            cola.add(act);
        });
    }

    public void add_colaF(Actividad a, List<Actividad> cola) { // método que agrega en una cola los predecesores de cada actividad que ingresa
        a.getEntradas().forEach((act) -> {
            cola.add(act);
        });
    }
    // </editor-fold>

    // <editor-fold desc="Buscadores" defaultstate="collapsed">
    public int buscamayor(Actividad act) { // método que asigna el valor a tiempo lejano
        int mayor = 0;
        for (Actividad a : act.getEntradas()) {
            if (mayor <= a.getTC()) {
                mayor = a.getTC();
            }
        }
        return mayor;
    }

    public int buscamenor(Actividad act) { // método que asigna el valor a tiempo cercano
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
    public void add_inicio() {// método que agrega un nodo inicial al grafo 
        Set<String> keys = actividades.keySet();
        Actividad a;
        for (String key : keys) {
            a = actividades.get(key);
            if (a.getEntradas().isEmpty()) {
                this.n_i.getSalidas().add(a);
                a.getEntradas().add(this.n_i);
            }
        }
    }

    public void add_final() {// método que agrega un nodo final al grafo 
        Set<String> keys = actividades.keySet();
        Actividad a;
        for (String key : keys) {
            a = actividades.get(key);
            if (a.getSalidas().isEmpty()) {
                this.n_f.getEntradas().add(a);
                a.getSalidas().add(this.n_f);
            }
        }
    }

    public void agregarActividad(Actividad a) {
        actividades.put(a.getName(), a);
    }
    // </editor-fold>

    // <editor-fold desc="Comprobadores" defaultstate="collapsed">
    public boolean hay_ciclo() {// método que busca en el grafo si hay un ciclo
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
        str = new StringBuilder().append("id\ttiempo\tIC\tTC\tIL\tTL\tholgura\tX\tY\n");
        actividades.forEach((k, v)
                -> str.append(v.toString()).append("\n"));
        return str.toString();
    }

    public String ruta(ArrayList<ArrayList<Actividad>> rutas) {
        StringBuilder str;
        str = new StringBuilder().append("Ruta Critica: \n");
        for (ArrayList<Actividad> list : rutas) {
            for (Actividad a : list) {
                str.append("->").append(a.getName());
            }
            str.append("\n");
        }
        return str.toString();
    }
    // </editor-fold>

    // </editor-fold>
    
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private final HashMap<String, Actividad> actividades;//lista para las entradas
    Actividad n_i;// nodo inicial
    Actividad n_f;// nodo final
    // </editor-fold>
}
