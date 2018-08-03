package mrcproject.model;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ExtremeTech
 */
class ArchivoHandler extends DefaultHandler {

    public ArchivoHandler() {
    }

    public ArrayList<Relacion> getRelaciones() {
        return relaciones;
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        switch (qName) {
            case "datos":
                break;
            case "Actividad":
                actividad = new Actividad(attributes.getValue("id").charAt(0),
                        Integer.parseInt(attributes.getValue("duracion")));
                actividades.add(actividad);
                break;
            case "Relacion":
                relacion = new Relacion(attributes.getValue("sucesor").charAt(0),
                        attributes.getValue("actividad").charAt(0));
                relaciones.add(relacion);
                break;
        }
    }

    private ArrayList<Relacion> relaciones;
    private ArrayList<Actividad> actividades;
    private Actividad actividad;
    private Relacion relacion;
}
