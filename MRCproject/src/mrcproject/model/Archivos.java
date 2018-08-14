package mrcproject.model;

import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author Esteban Espinoza Fallas  402290345
 * @author Carlos Vargas Alfaro     402170927
 */

public class Archivos {

    public Archivos(){
    }

    public HashMap<String, Actividad> carga(String path) {// método que carga el archivo xml con las actividades
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = null;
            HashMap actividades = new HashMap();
                doc = docBuilder.parse(new File(path));
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
                        temp = (Actividad) actividades.get(a.getAttribute("actividad"));
                        temp.getSalidas().add((Actividad) actividades.get(a.getAttribute("sucesor")));
                        temp = (Actividad) actividades.get(a.getAttribute("sucesor"));
                        temp.getEntradas().add((Actividad) actividades.get(a.getAttribute("actividad")));
                    }
                }
            doc.getDocumentElement().normalize();
            return (HashMap) actividades;
        } catch (Exception ex) {
            System.out.println("No se encontro el archivo");
            System.out.println("No se pudo cargar el archivo");
        }
        return null;
    }
}
