package mrcproject.model;

/**
 *
 * @author ExtremeTech
 */
public class Relacion {
    
    // <editor-fold desc="Atributos" defaultstate="collapsed">
    public Relacion(Actividad destino, Actividad salida) {
        this.destino = destino;
        this.salida = salida;
    }

    public Actividad getDestino() {
        return destino;
    }

    public Actividad getSalida() {
        return salida;
    }
    private final Actividad destino;
    private final Actividad salida;
    // </editor-fold>
}
