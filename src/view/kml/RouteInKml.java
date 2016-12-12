package view.kml;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;

import org.jdom2.*;
import org.jdom2.output.*;

import model.dijkstraData.*;

public class RouteInKml {
	
	public static void saveKmlFile(String name, LinkedList<Kante> route, String filename){
		
		Element kml, document, placemark, nameElement, lineString, coordinates;
		String routeCoordinates = new String();
		
		kml = new Element("kml");
		Document kmlFile = new Document(kml);
		document = new Element("Document");
		kml.addContent(document);
		
		placemark = new Element("Placemark");
		document.addContent(placemark);
		
		nameElement = new Element("name");
		nameElement.addContent("Berechnete Route");
		placemark.addContent(nameElement);
		
		lineString = new Element("LineString");
		coordinates = new Element("coordinates");
		
		for(Kante k : route){
			for(Point2D.Double point : k.getGeometry()){
				routeCoordinates = routeCoordinates.concat(point.getX() + "," + point.getY() + "\n");
			}
		}
		
		String[] duplicateRemoveString = routeCoordinates.split("\n");
		routeCoordinates = new String();
		for(int i = 0; i < duplicateRemoveString.length - 1; i++){
			if(!duplicateRemoveString[i].contentEquals(duplicateRemoveString[i+1])){
				routeCoordinates = routeCoordinates.concat(duplicateRemoveString[i] + "\n");
			}
		}
		
		coordinates.addContent(routeCoordinates);
		lineString.addContent(coordinates);
		placemark.addContent(lineString);
		
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		
		try{
			FileWriter fw = new FileWriter(filename, false);
			out.output(kmlFile,  fw);
			
			System.out.println("New Route saved as KLM file!");
		} catch(IOException e){
			System.out.println("IO-Fehler!");
		} catch(Exception e1){
			System.out.println("Fehler!");
		}
	}
}
