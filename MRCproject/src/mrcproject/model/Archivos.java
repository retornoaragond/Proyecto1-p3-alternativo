package mrcproject.model;

import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author Esteban Espinoza Fallas 402290345
 * @author Carlos Vargas Alfaro 402170927
 */
public class Archivos {

    public Archivos() {
    }

    public HashMap<String, Actividad> carga(String path) {// método que carga el archivo xml con las actividades y relaciones
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
                            Integer.parseInt(a.getAttribute("duracion")),
                            Integer.parseInt(a.getAttribute("x")),
                            Integer.parseInt(a.getAttribute("y"))));
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
        } catch (IOException | NumberFormatException | ParserConfigurationException | SAXException ex) {
            System.out.println("No se pudo cargar el archivo");
        }
        return null;
    }

    public void generar(String ruta, HashMap<String, Actividad> proyecto) {
        
        try {
            DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fact.newDocumentBuilder();
            Document document = builder.newDocument();
            //
            Element datos = document.createElement("datos");
            document.appendChild(datos);
            Set<String> keys = proyecto.keySet();
            Actividad a;
            //ACTIVIDADES
            for (String key : keys) {
                a = proyecto.get(key);
                Element actividad = document.createElement("Actividad");
                datos.appendChild(actividad);
                actividad.setAttribute("id", a.getName());
                actividad.setAttribute("duracion", Integer.toString(a.getDtime()));
                actividad.setAttribute("x", Integer.toString(a.getX()));
                actividad.setAttribute("y", Integer.toString(a.getY()));
            }
            //RELACIONES
            for (String key : keys) {
                a = proyecto.get(key);
                for (Actividad act : a.getSalidas()) {
                    if (!("n_f".equals(act.getName()))) {
                        Element relacion = document.createElement("Relacion");
                        datos.appendChild(relacion);
                        relacion.setAttribute("actividad", a.getName());
                        relacion.setAttribute("sucesor", act.getName());
                    }
                }
            }
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer trans = factory.newTransformer();
            Source source = new DOMSource(document);
            File file = new File(ruta);
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            Result result = new StreamResult(pw);
            trans.transform(source, result);
        } catch (ParserConfigurationException | IOException | TransformerException ex) {
            System.err.println("Error al intentar generar el nuevo archivo...");
        }

        
    }
}
