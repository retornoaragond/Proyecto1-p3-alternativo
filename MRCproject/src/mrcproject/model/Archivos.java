package mrcproject.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author ExtremeTech
 */
public class Archivos {

    public Archivos() {
    }

    public HashMap<String, Actividad> carga(String path) throws Exception {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new File(path));
        doc.getDocumentElement().normalize();
        HashMap actividades = new HashMap();
        ArrayList<Relacion> relaciones = new ArrayList();

        NodeList as = doc.getElementsByTagName("Actividad");
        for (int i = 0; i < as.getLength(); i++) {
            Node n = as.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element a = (Element) n;
                actividades.put(a.getAttribute("id"), new Actividad(a.getAttribute("id"), 
                        Integer.parseInt(a.getAttribute("duracion"))));
            }
        }

        as = doc.getElementsByTagName("Relacion");
        for (int i = 0; i < as.getLength(); i++) {
            Node n = as.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element a = (Element) n;
                relaciones.add(new Relacion(a.getAttribute("actividad"), a.getAttribute("sucesor")));
            }
        }
        asociacion(actividades, relaciones);
        return (HashMap)actividades;
    }

    public void asociacion(HashMap<String, Actividad> actividades, ArrayList<Relacion> rel) {
        //asigna los elementos de las relaciones a cada actividad correspondiente
        Actividad a;
        for (Relacion r : rel) {
            a = actividades.get(r.getSalida());
            a.getSalidas().add(r);
            a = actividades.get(r.getDestino());
            a.getEntradas().add(r);
        }
    }
}
