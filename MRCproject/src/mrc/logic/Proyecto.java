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
        if (!hasCycle()) {
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
            if (!consultaEntradas(temp, visitados)) {  //si sus antecesores no han sido visitados no se procesa 
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
            if (!consultaSalidas(temp, visitados)) {  //si sus antecesores no han sido visitados no se procesa 
                visitados.add(temp);
                temp.setIL(buscamenor(temp) - temp.getDtime());
                add_colaF(temp, cola);
            } else {                            //se reingresa en la cola al final
                cola.add(temp);
            }
        }
    }

    public void limpia_ini_fin() {
        ArrayList<Actividad> inicios = n_i.getSalidas();
        ArrayList<Actividad> finales = n_f.getEntradas();
        n_i.setSalidas(new ArrayList<>());
        n_f.setEntradas(new ArrayList<>());
        inicios.forEach((a) -> {
            a.getEntradas().remove(n_i);
        });
        finales.forEach((a) -> {
            a.getSalidas().remove(n_f);
        });
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

    public void recalcula() {
        limpia_ini_fin();
        add_inicio();
        add_final();
        rutaCritica();
    }

    public void agregarActividad(Actividad a) throws Exception {
        if (a.getDtime() > 0) {
            if (!actividades.isEmpty()) {
                if (!existe(a.getName())) {
                    actividades.put(a.getName(), a);
                    this.recalcula();

                } else {
                    throw new Exception("1");
                }
            } else {
                actividades.put(a.getName(), a);
                this.recalcula();
            }
        } else {
            throw new Exception("2");
        }
    }

    public void relacionar(String a, String b) throws Exception {
        if (!b.equals(a)) {
            if (!actividades.get(a).getSalidas().contains(actividades.get(b))) {
                Actividad ac = this.getActividades().get(a);
                Actividad bc = this.getActividades().get(b);
                ac.getSalidas().add(bc);
                bc.getEntradas().add(ac);
                if (!hasCycle()) {
                    this.recalcula();
                } else {
                    //eliminar la relacion
                    ac.getSalidas().remove(bc);
                    bc.getEntradas().remove(ac);
                    throw new Exception("3");
                }
            } else {
                throw new Exception("2");
            }
        } else {
            throw new Exception("1");
        }
    }

    // </editor-fold>
    // <editor-fold desc="Comprobadores" defaultstate="collapsed">
    public boolean hasCycle() {
        ArrayList<Actividad> visitados = new ArrayList<>();
        limpia_ini_fin();
        for (Actividad a : actividades.values()) {
            if (hasCycle(a, visitados)) {
                add_inicio();
                add_final();
                return true;
            }
        }
        add_inicio();
        add_final();
        return false;
    }

    public boolean hasCycle(Actividad a, ArrayList<Actividad> visitados) {
        if (visitados.contains(a)) {
            return true;
        }
        visitados.add(a);
        if (actividades.get(a.getName()).getSalidas().stream().anyMatch((nextNode)
                -> (hasCycle(nextNode, visitados)))) {
            return true;
        }
        visitados.remove(visitados.size() - 1);
        return false;
    }

//    public boolean generaciclo(Actividad temp, Actividad temp2) {
//        return temp.getEntradas().contains(temp2);
//    }

    public boolean consultaEntradas(Actividad a, ArrayList<Actividad> visit) {//busca en los visitados si sus predecesotres ya fueron visitados
        return a.getEntradas().stream().anyMatch((act) -> (!visit.contains(act)));
    }

    public boolean consultaSalidas(Actividad a, ArrayList<Actividad> visit) {//busca en los visitados si sus sucesores ya fueron visitados
        return a.getSalidas().stream().anyMatch((act) -> (!visit.contains(act)));
    }

    public boolean existe(String a) {
        Set<String> keys = actividades.keySet();
        Actividad temp;
        for (String key : keys) {
            temp = actividades.get(key);
            if (a.equals(temp.getName())) {
                return true;
            }
        }
        return false;
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
