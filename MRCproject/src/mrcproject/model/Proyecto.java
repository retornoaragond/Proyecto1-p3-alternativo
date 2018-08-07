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

       // calcular_IC_TC();
        calcular_IL_TL();

    }
    /*
    public void calculaICTc(){
    //Actividad n_inicio = new Actividad("n_inicio", 0);
       
    String ini="n_inicio";
         String salida;
        Set<String> keys = actividades.keySet();
        Actividad a;
        Relacion r;
        for (String key : keys) {
           // poner validacion 
            a = actividades.get(key);
             if (a.getEntradas().get(0).getSalida()=="n_inicio") 
              for(int j=0;j < a.getEntradas().size();j++){
             r= a.getEntradas().get(j);//atrapo a  salidas de a                 salida i dst a
             salida=r.getSalida();
             desti =r.getDestino();  //a
              n=actividades.get(salida);// busco destino de i
        }
        }
    }
    
    
    */
    
    
    public void calcular_IC_TC() {
       
        int aux=0;
        String desti,salida;
        
        
        ArrayList<Actividad> visitados = new ArrayList();
        List<Actividad> pila;
        List<Actividad> cola;
        
        Actividad n,nn ;
        Set<String> keys = actividades.keySet();
        Actividad a;
        Relacion r;
        for (String key : keys) {
           // poner validacion 
            a = actividades.get(key);
            
            if (a.getEntradas().isEmpty()) {
             //for(int j=0;j < a.getSalidas().size();j++){
              for(int j=0;j < a.getEntradas().size();j++){
              
              r= a.getEntradas().get(j);//atrapo a  salidas de a                 salida i dst a
             salida=r.getSalida();
             desti =r.getDestino();  //a
              n=actividades.get(salida);// busco destino de i
              
              nn=actividades.get(desti);// busco ic de i 
             
              nn.setIC(n.getTC());
              nn.setTC(aux=nn.getIC()+nn.getDtime());
              visitados.add(nn);
              }
              
              
        }
            System.out.println("se cayo");  
        }
        
    }
             
    
    
        
   
        
        
        
        
        
        
        /*
        Set<String> keys = actividades.keySet();
        Actividad a;
        Relacion r;
        //for (int i=0;i<actividades.size();i++) {
            for (String key : keys) {
            a = actividades.get(key);
            a.getEntradas();
             
            
            // destino=a.getNombre();
                
           
            
            a = actividades.get(keys);
           a.setTC(aux=a.getIC()+a.getDtime());
           visitados.add(a);
        
        }
         
    }
   
    */
    

   
        
      public void calcular_IL_TL() {

      }

      public Actividad getActividad(String name){
       //List<Actividad> actividades;
       Actividad aa= new Actividad("",0);
       Set<String> keys = actividades.keySet();   
      
     
    return null;
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

    // </editor-fold>
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private final HashMap<String, Actividad> actividades;//lista para las entradas
    // </editor-fold>
}
