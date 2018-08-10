package mrcproject.model;

import java.util.HashMap;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Archivos {

    public Archivos() {
    }

    public HashMap<String, Actividad> carga(String path) throws Exception {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new File(path));
        doc.getDocumentElement().normalize();
        HashMap actividades = new HashMap();
        Actividad temp;

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
                temp = (Actividad)actividades.get(a.getAttribute("actividad"));
                temp.getSalidas().add((Actividad)actividades.get(a.getAttribute("sucesor")));
                temp = (Actividad)actividades.get(a.getAttribute("sucesor"));
                temp.getEntradas().add((Actividad)actividades.get(a.getAttribute("actividad")));
            }
        }
        return (HashMap)actividades;
    }
}
