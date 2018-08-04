package mrcproject.model;

/**
 *
 * @author ExtremeTech
 */
public class Relacion {

    // <editor-fold desc="Constructores" defaultstate="collapsed">
    
    public Relacion(String salida, String destino) {
        this.destino = destino;
        this.salida = salida;
    }

    public Relacion() {
    }
    // </editor-fold>

    // <editor-fold desc="Metodos" defaultstate="collapsed">
    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getSalida() {
        return salida;
    }
    
    public void setSalida(String salida) {
        this.salida = salida;
    }
    
    // </editor-fold>

    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private String destino;
    private String salida;
    // </editor-fold>
}
