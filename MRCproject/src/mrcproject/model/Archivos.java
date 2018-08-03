package mrcproject.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author ExtremeTech
 */
public class Archivos {

    public Archivos() {
    }

    public ArrayList<Actividad> carga(String name) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        File file = new File("datos.xml");
        ArchivoHandler handler = new ArchivoHandler();
        saxParser.parse(file, handler);
        asociacion(handler.getActividades(), handler.getRelaciones());//acomoda las relaciondes en las entradas y salidas de las actividades 
        return handler.getActividades();
    }

    public void asociacion(ArrayList<Actividad> act, ArrayList<Relacion> rel) {
        //asigna los elementos de las relacioens a cada actividad correspondiente
        Actividad a;
        for (Relacion r : rel) {
            a = busca_actividad(act, r.getSalida());
            a.getSalidas().add(r);
            a = busca_actividad(act, r.getDestino());
            a.getEntradas().add(r);
        }
    }

    public Actividad busca_actividad(ArrayList<Actividad> act, char name) {
        //busca la actividad segun el nombre que recive
        for (Actividad a : act) {
            if (a.getName() == name) {
                return a;
            }
        }
        return null;
    }
}
