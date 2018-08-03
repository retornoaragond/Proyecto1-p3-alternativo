package mrcproject.model;

import java.io.File;
import java.io.IOException;
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

    public Grafo carga(String name) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        File file = new File("datos.xml");
        ArchivoHandler handler = new ArchivoHandler();
        saxParser.parse(file, handler);
        return null;
    }
}
