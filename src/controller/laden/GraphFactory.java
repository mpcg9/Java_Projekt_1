package controller.laden;

import model.dijkstraNetwork.*;
import model.dijkstraData.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;

public class GraphFactory {
	
	//lol
	public GraphFactory(){
		
	}
	
	/**
	 * Liest aus einer XML-Datei einen Graphen ein.
	 * @param filename Name der XML-Datei
	 * @return Der Graph, welcher in der XML-Datei gespeichert ist.
	 */
	public Graph leseXml(String filename){
		try{
			//Einlesen der Datei mit JDOM:
			File file = new File(filename);
			SAXBuilder sax = new SAXBuilder();
			Document doc = sax.build(file);
			Element root = doc.getRootElement();
			
			//Einlesen aller Knoten und Kanten:
			List<Element> knotenElemente = root.getChild("vertices").getChildren("vertex");
			List<Element> kantenElemente = root.getChild("arcs").getChildren("arc");
			
			//Konvertieren aller Knoten in ein bearbeitbares Datenformat:
			HashMap<String, Knoten> knotenMap = KnotenFactory.makeKnoten(knotenElemente);
			
			//Erzeugen der Adjazenzliste:
			HashMap<Knoten, LinkedList<Kante>> adjazenzListe = new HashMap<Knoten, LinkedList<Kante>>();
			
			//Erzeugen der Kanten:
			KantenFactory kf = new KantenFactory(knotenMap, adjazenzListe);
			kf.makeKante(kantenElemente);
			
			return new Graph(knotenMap, adjazenzListe);
		}
		catch(IOException e) {e.printStackTrace();}
		catch(JDOMException e) {e.printStackTrace();}
		return new Graph();
	}
	
	private static class KnotenFactory{
		/**
		 * Erstellt aus einem JDOM-Element e einen Knoten
		 * @param knotenElement Knoten aus XML-Datei
		 * @return Der Knoten, welcher in der XML-Datei gespeichert wurde.
		 */
		public static Knoten makeKnoten(Element knotenElement){
			double x = Double.parseDouble(knotenElement.getAttributeValue("x"));
			double y = Double.parseDouble(knotenElement.getAttributeValue("y"));
			Point2D.Double p = new Point2D.Double(x, y);
			//System.out.println(x +"," + y);
			p = Transform.transformPoint(p, "EPSG:4326", "EPSG:31466");
			String id = knotenElement.getAttributeValue("id");
			//System.out.println(p.getX() +"," + p.getY() + "," + id);
			return new Knoten(id,p.getX(),p.getY());
		}
		
		
		public static HashMap<String, Knoten> makeKnoten(List<Element> knotenElementListe){
			HashMap<String, Knoten> knotenMap = new HashMap<String, Knoten>();
			for (Element e : knotenElementListe){
				Knoten k = makeKnoten(e);
				knotenMap.put(k.getID(),k);
			}
			return knotenMap;
		}
	}
	
	private class KantenFactory{
		private HashMap<String, Knoten> knotenSpeicher;
		private HashMap<Knoten, LinkedList<Kante>> adjazenzListe;
		
		/**
		 * Die KantenFactory erzeugt Kanten.
		 * @param knotenSpeicher Liste aller gespeicherten Knoten.
		 * @param adjazenzListe Adjazenzliste. Alle von der KantenFactory hinzugefügten Kanten werden hier direkt eingetragen.
		 */
		public KantenFactory(HashMap<String, Knoten> knotenSpeicher, HashMap<Knoten, LinkedList<Kante>> adjazenzListe) {
			this.knotenSpeicher = knotenSpeicher;
			this.adjazenzListe = adjazenzListe;
		}
		
		public Kante makeKante(Element kantenElement){
			String id = kantenElement.getAttributeValue("id");
			String startKnotenID = kantenElement.getAttributeValue("from");
			String endKnotenID = kantenElement.getAttributeValue("to");
			String name = kantenElement.getAttributeValue("name");
			
			List<Element> gewichtsElemente = kantenElement.getChild("weights").getChildren("weight");
			List<Element> geometrieElemente = kantenElement.getChild("geometry").getChildren("point");
			
			HashMap<String, Double> gewichte = new HashMap<String, Double>();
			for(Element g : gewichtsElemente){
				String key = g.getAttributeValue("key");
				double val = Double.parseDouble(g.getAttributeValue("value"));
				gewichte.put(key, val);
			}
			
			LinkedList<Point2D.Double> geometrie = new LinkedList<Point2D.Double>();
			for(Element p : geometrieElemente){
				int seq = Integer.parseInt(p.getAttributeValue("seq"));
				double x = Double.parseDouble(p.getAttributeValue("x"));
				double y = Double.parseDouble(p.getAttributeValue("y"));
				Point2D.Double point = new Point2D.Double(x, y);
				geometrie.add(seq, point);
			}
			
			Knoten startKnoten = knotenSpeicher.get(startKnotenID);
			Knoten endKnoten = knotenSpeicher.get(endKnotenID);
			
			Kante k = new Kante(id, name, startKnoten, endKnoten, gewichte, geometrie);
			adjazenzHinzufuegen(startKnoten, k);
			
			return k;
		}
		public HashMap<String, Kante> makeKante(List<Element> kantenElementListe){
			HashMap<String, Kante> kantenMap = new HashMap<String, Kante>();
			for (Element e : kantenElementListe){
				Kante k = makeKante(e);
				kantenMap.put(k.getId(), k);
			}
			return kantenMap;
		}
		
		/**
		 * Fügt der Adjazenzliste des Knotens key die Kante k hinzu.
		 * Falls der Knoten noch keine Adjazenzliste besitzt, so wird diese neu erzeugt.
		 * @param key
		 * @param k
		 */
		public void adjazenzHinzufuegen(Knoten key, Kante k){
			LinkedList<Kante> l;
			if(adjazenzListe.containsKey(key)){ 
				l = adjazenzListe.get(key);
				l.add(k); 
			}
			else{ 
				l = new LinkedList<Kante>();
				l.add(k);
			}
			adjazenzListe.put(key, l);
		}
	}
}
