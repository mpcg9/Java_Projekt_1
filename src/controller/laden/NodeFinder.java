package controller.laden;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import model.dijkstraNetwork.Graph;

public class NodeFinder {
	public static String[] findNodes(String filename, Graph g){
		try{
			//Einlesen der Datei mit JDOM:
			File file = new File(filename);
			SAXBuilder sax = new SAXBuilder();
			Document doc = sax.build(file);
			Element root = doc.getRootElement();
			Namespace ns = root.getNamespace();
			
			//Einlesen der Positionen:
			List<Element> positionElemente = root.getChild("Document",ns).getChild("Folder",ns).getChildren("Placemark",ns);
			
			
			String[] returnString = {null, null};
			for(Element e : positionElemente){
				String coordinates;
				double[] xyz;
				switch(e.getChildText("name",ns)){
					case "Start":
						coordinates = e.getChild("Point",ns).getChildText("coordinates",ns);
						xyz = coordinatesFromString(coordinates);
						returnString[0] = g.getNearestNode(xyz[0], xyz[1]);
						break;
					case "Ziel":
						coordinates = e.getChild("Point",ns).getChildText("coordinates",ns);
						xyz = coordinatesFromString(coordinates);
						returnString[1] = g.getNearestNode(xyz[0], xyz[1]);
						break;
					default:
						break;
				}
			}
			
			return returnString;
		}
		catch(IOException e) {e.printStackTrace();}
		catch(JDOMException e) {e.printStackTrace();}
		return new String[2];
	}
	
	private static double[] coordinatesFromString(String input){
		String[] values = input.split(",");
		double[] output = new double[values.length];
		for (int i = 0; i < values.length; i++){
			output[i] = Double.parseDouble(values[i]);
		}
		return output;
	}
}

