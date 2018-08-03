package mrcproject.model;

/**
 *
 * @author ExtremeTech
 */
public class Relacion {

    // <editor-fold desc="Constructores" defaultstate="collapsed">
    
    public Relacion(char destino, char salida) {
        this.destino = destino;
        this.salida = salida;
    }

    public Relacion() {
    }
    // </editor-fold>

    // <editor-fold desc="Metodos" defaultstate="collapsed">
    public char getDestino() {
        return destino;
    }

    public void setDestino(char destino) {
        this.destino = destino;
    }

    public char getSalida() {
        return salida;
    }
    
    public void setSalida(char salida) {
        this.salida = salida;
    }
    
    // </editor-fold>

    // <editor-fold desc="Atributos" defaultstate="collapsed">
    private char destino;
    private char salida;
    // </editor-fold>
}
