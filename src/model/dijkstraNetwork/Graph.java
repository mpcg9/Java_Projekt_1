package model.dijkstraNetwork;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import controller.laden.Transform;
import model.dijkstraData.Kante;
import model.dijkstraData.Knoten;

public class Graph {
	//Attribute
	private HashMap<String, Knoten> knotenMap;
	private HashMap<Knoten, LinkedList<Kante>> adjazenzListe;
	
	//Konstruktoren
	public Graph(){
		this.knotenMap = new HashMap<String, Knoten>();
		this.adjazenzListe = new HashMap<Knoten, LinkedList<Kante>>(); //Erstelle leere HashMaps
	}
	public Graph(HashMap<String, Knoten> knotenMap, HashMap<Knoten, LinkedList<Kante>> adjazenzListe){
		this.knotenMap = knotenMap;
		this.adjazenzListe = adjazenzListe;
	}
	
	//Methoden
	
	/*
	public void loadFromFile(String pfad){
		try{
			FileReader fr = new FileReader(pfad);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine(); //Zeile einlesen
			while (line != null){
				if(!line.contains("#")){ //auskommentierte Zeilen nicht beachten
					if(line.contains("Knoten")){ //für Knoten wird folgendes getan:
						String [] zeile = line.split(","); //Zeile an den Kommata trennen
						String id = zeile[1].trim();
						double x = Double.valueOf(zeile[2].trim());
						double y = Double.valueOf(zeile[3].trim()); //id, x und y einlesen
						Knoten knoten = new Knoten(id, x, y); //neuen knoten aus erhaltenen Werten erstellen
						this.knotenMap.put(id, knoten); //Knoten in die knotenMap einfügen
						LinkedList<Kante> kantenliste = new LinkedList<Kante>();
						this.adjazenzListe.put(knoten, kantenliste);
					}
				}
				line = br. readLine(); //nächste Zeile einlesen
			}
			
			String linie = br.readLine(); //Zeile neu von oben einlesen
			while(linie != null){
				if(!linie.contains("#")){ //auskommentierte Zeilen nicht beachten
					if(linie.contains("Kante")){ //für Kanten wird folgendes getan:
						String [] zeile = linie.split(","); //Zeile an den Kommata trennen
						String anfangID   = zeile[1];
						Knoten anfang     = new Knoten(zeile[1].trim());
						Knoten ende       = new Knoten(zeile[2].trim());
						double[] gewicht = new double[1];
						gewicht[0]        = Double.valueOf(zeile[3].trim());
						int gewichtTyp    = Integer.valueOf(zeile[4].trim());
						String id         =                 zeile[5].trim();
						String name       =                 zeile[6].trim(); //einlesen der einzelnen Elemente
						Kante kante = new Kante(id, name, anfang, ende, gewicht, gewichtTyp); //Instanz von Kante mit eingelesenen Werten erstellen
						LinkedList<Kante> kantenliste = adjazenzListe.get(knotenMap.get(anfangID)); //hole passende Liste aus der Adjazenzliste
						kantenliste.addFirst(kante); //füge der Liste die Kante hinzu
						adjazenzListe.put(knotenMap.get(anfangID),  kantenliste); //Füge die Liste wieder in die AdjazenzListe ein
					}
				}
				linie = br.readLine(); //nächste Zeile einlesen
			}
			br.close();
			fr.close();
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	/**/
	
	/**
	 * Dijkstra-Algorithmus mit Geometrie
	 * @param startKnotenName Von
	 * @param endKnotenName Nach
	 * @return Liste der Kanten vom Start zum Endknoten. Leere Liste, falls keine Route gefunden werden konnte.
	 */
	public LinkedList<Kante> berechneRoute(String startKnotenName, String endKnotenName, String gewichtTyp){
		Knoten start = knotenMap.get(startKnotenName);
		Knoten ende = knotenMap.get(endKnotenName);
		
		DijkstraRoute startRoute = new DijkstraRoute(gewichtTyp, start, ende);
		GreenList green = new GreenList(startRoute, true);
		
		while(green.hasAccess()){
			DijkstraRoute nextRoute = green.pop();
			if(nextRoute.getEndKnoten().equals(ende)) return nextRoute.getWeg();
			if(this.adjazenzListe.containsKey(nextRoute.getEndKnoten())){
				LinkedList<Kante> anschlussKanten = this.adjazenzListe.get(nextRoute.getEndKnoten());
				LinkedList<DijkstraRoute> anschlussRouten = new LinkedList<DijkstraRoute>();
				for(Kante k : anschlussKanten){
					anschlussRouten.add(new DijkstraRoute(nextRoute, k));
				}
				green.add(anschlussRouten);
			}
		}
		
		System.out.println("Konnte keine Route finden :(");
		return new LinkedList<Kante>();
	}
	
	/**
	 * Innere Klasse, die die "grüne" Liste der noch abzuarbeitenden Knoten des Dijkstra-Algorithmus repräsentiert.
	 * @author Florian
	 */
	private class GreenList{
		private LinkedList<DijkstraRoute> green;
		private HashMap<Knoten, DijkstraRoute> kuerzesteRouten;
		private boolean usesGeometry;
		
		public GreenList(DijkstraRoute startRoute, boolean usesGeometry){
			this.green = new LinkedList<DijkstraRoute>();
			this.green.add(startRoute);
			this.kuerzesteRouten = new HashMap<Knoten, DijkstraRoute>();
			this.kuerzesteRouten.put(startRoute.getEndKnoten(), startRoute);
			this.usesGeometry = usesGeometry;
		}
		
		/**
		 * Fügt eine Liste von Kanten dieser Grünen Liste hinzu. Diese werden automatisch sortiert.
		 * Auch die Liste der kürzesten Routen wird automatisch aktualisiert.
		 * Routen, die längere Wege zu bestehenden Knoten liefern, werden automatisch aus der Liste entfernt (im Aufruf von pop()).
		 * @param l
		 */
		public void add(LinkedList<DijkstraRoute> l){
			
			naechsteRouteEinfuegen:
			for(DijkstraRoute neueRoute : l){
				Knoten zielKnoten = neueRoute.getEndKnoten();
				// Falls es eine kürzere Route zum Zielknoten der neuen Route gibt, verwerfe die neue Route und mache mit der nächsten weiter.
				if(this.kuerzesteRouten.containsKey(zielKnoten) && this.kuerzesteRouten.get(zielKnoten).getRoutenLaenge() < neueRoute.getRoutenLaenge()){
					continue naechsteRouteEinfuegen;
				}
				// Füge die neue Route als kürzeste Route zum Zielknoten hinzu.
				// Die laengeren Routen bleiben in der gruenen Liste vorerst bestehen, diese werden beim Aufruf ausgefiltert.
				this.kuerzesteRouten.put(zielKnoten, neueRoute);
				
				// Sortiere die neue Kante in die grüne Liste ein.
				if(this.usesGeometry){
					double sortierGewicht = neueRoute.getGeometricalDijkstraWeight();
					//Gehe durch alle Kanten der grünen Liste...
					for(DijkstraRoute listenElement : this.green){
						if(listenElement.getGeometricalDijkstraWeight() > sortierGewicht){
							//Füge das Element ein, sobald dessen Gewicht kleiner ist als das der (sortierten) Liste.
							this.green.add(this.green.indexOf(listenElement), neueRoute);
							//Sobald das Element eingefügt ist, sind wir fertig und koennen mit dem Einfuegen der naechsten Route beginnen.
							continue naechsteRouteEinfuegen;
						}
					}
					//Falls das Gewicht des Elements größer ist als alle anderen, füge das Element am Ende der Liste ein.
					this.green.add(neueRoute);
				}
				else{
					double sortierGewicht = neueRoute.getRoutenLaenge();
					for(DijkstraRoute listenElement : this.green){
						if(listenElement.getRoutenLaenge() < sortierGewicht){
							this.green.add(this.green.indexOf(listenElement), neueRoute);
							continue naechsteRouteEinfuegen;
						}
						this.green.add(neueRoute);
					}
				}
			}
		}
				
		/**
		 * Liefert die erste Route dieser Liste. Entfernt die Route aus der Liste.
		 * @return
		 */
		public DijkstraRoute pop(){
			DijkstraRoute output = green.pop();
			// Entfernen aller darauffolgender Routen aus der Liste, zu deren Ziel es bereits einen kuerzeren Weg gibt.
			while(this.hasAccess() && this.kuerzesteRouten.get(green.getFirst().getEndKnoten()) != green.getFirst()){
				green.removeFirst();
			}
			return output;
		}
		
		/**
		 * Überprüft, ob die grüne Liste Elemente enthält.
		 * @return true, falls die grüne Liste ein Element enthält.
		 */
		public boolean hasAccess(){
			return !(this.green.isEmpty());
		}
	}
	
	/**
	 * Findet den naechstgelegenen Knoten zur gegebenen Position (x, y in EPSG:4326 wie in Google Earth)
	 * @param x 
	 * @param y 
	 * @return ID des naechstgelegenen Knotens.
	 */
	public String getNearestNode(double x, double y){
		Point2D.Double p = new Point2D.Double(x, y);
		p = Transform.transformPoint(p, "EPSG:4326", "EPSG:31466");
		String nearest = null;
		double mindist = Double.MAX_VALUE;
		for(Entry<String, Knoten> k : this.knotenMap.entrySet()){
			double dist = k.getValue().distance(p);
			if(dist < mindist){
				mindist = dist;
				nearest = k.getKey();
			};
		}
		
		return nearest;
	}
	
	// Getter-/Settermethoden
	public HashMap<String, Knoten> getKnotenMap() {
		return knotenMap;
	}
	public void setKnotenMap(HashMap<String, Knoten> knotenMap) {
		this.knotenMap = knotenMap;
	}
	public HashMap<Knoten, LinkedList<Kante>> getAdjazenzListe() {
		return adjazenzListe;
	}
	public void setAdjazenzListe(HashMap<Knoten, LinkedList<Kante>> adjazenzListe) {
		this.adjazenzListe = adjazenzListe;
	}

}

