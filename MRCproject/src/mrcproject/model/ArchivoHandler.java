/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrcproject.model;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ExtremeTech
 */
class ArchivoHandler extends DefaultHandler{
    
    public ArchivoHandler() {
    }

    public ArchivoHandler(ArrayList<Relacion> relaciones, ArrayList<Actividad> actividades) {
        this.relaciones = relaciones;
        this.actividades = actividades;
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch(qName){
            case "datos":
                break;
            case "Actividad":
                //actividades=new Actividad();
                break;
            case "Relacion":
                break;
        }
    }

    private ArrayList<Relacion> relaciones;
    private ArrayList<Actividad> actividades;
}
