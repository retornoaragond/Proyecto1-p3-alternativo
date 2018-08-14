package mrcproject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Esteban Espinoza Fallas
 * @author Carlos Vargas Alfaro
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
    public String rutaCritica() {
        add_inicio();
        add_final();
        if (!hay_ciclo()) {
            calcular_IC();
            calcular_IL();
            ArrayList<ArrayList<Actividad>> rutas = new ArrayList<>();//lista de listas para las rutas
            CPM2(rutas, n_f, 0);//metodo para N rutas criticas
            reverse(rutas);//metodo da vuelta a las rutas ya que estan al reves
            return ruta(rutas);
        } else {
            System.out.println("Error: Hay un Ciclo en el grafo");
        }
        return null;
    }

    public void CPM2(ArrayList<ArrayList<Actividad>> rutas, Actividad a, int ruta) {//metodo para N rutas criticas empezando desde el final
        int part = 0;//bandera para saber si hay mas caminos que llega a un nodo
        for (Actividad temp : a.getEntradas()) {
            if (!("n_i".equals(temp.getName()))) {//si es el nodo inicial no lo toma encuenta 
                if (temp.getHolgura() == 0) {
                    if ("n_f".equals(a.getName())) {//si son predecesores de el final crea una ruta por cada uno
                        ArrayList<Actividad> tempL = new ArrayList<>();
                        tempL.add(temp);
                        rutas.add(tempL);
                        CPM2(rutas, temp, rutas.size() - 1);//llamado recursivo
                    } else {
                        if (cant_cami(a) < 2) {//si sus antecesorees con hogura 0 es menor a 1
                            rutas.get(ruta).add(temp);//lo ingresa en esa lista
                            CPM2(rutas, temp, ruta);//llamad recursivo
                        } else {
                            if (part == 0) {//si es el primer de los nodos antecesores  
                                ArrayList<Actividad> tempL2 = (ArrayList) rutas.get(ruta).clone();//crea una copia de la ruta actual
                                rutas.add(tempL2);//la copia la ingresa en un nuevo lugar
                                rutas.get(ruta).add(temp);//ingresa el primero antecesor
                                CPM2(rutas, temp, ruta);//llamado recursivo
                                part++;//cambia la bandera para ingresar cambiar de ruta
                            } else {
                                rutas.get(++ruta).add(temp);//ingresa en la ruta copiada anteriormente
                                CPM2(rutas, temp, rutas.size() - 1);//llamado recursivo
                            }
                        }
                    }
                }
            }
        }
    }

    public int cant_cami(Actividad a) {//cuenta la cantidad de nodos antecesores que tiene holgura 0
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
    Actividad n_i;
    Actividad n_f;
    // </editor-fold>
}
