package model.dijkstraNetwork;

import java.util.LinkedList;

import model.dijkstraData.Kante;
import model.dijkstraData.Knoten;

public class DijkstraRoute {
	private Knoten endKnoten;
	private Knoten gesamtZiel;
	private double routenLaenge;
	private double distanzZumZiel;
	private DijkstraRoute wegZuvor;
	private Kante neueKante;
	private String weightType;
	
	public DijkstraRoute(DijkstraRoute wegZuvor, Kante neueKante) {
		this.weightType = wegZuvor.getWeightType();
		this.wegZuvor = wegZuvor;
		this.neueKante = neueKante;
		this.endKnoten = neueKante.getEnde();
		this.gesamtZiel = wegZuvor.getGesamtZiel();
		this.distanzZumZiel = -1;
		this.routenLaenge = wegZuvor.getRoutenLaenge() + neueKante.getGewicht(weightType);
	}
	
	public DijkstraRoute(String weightType, Knoten startKnoten, Knoten zielKnoten){
		this.neueKante = null;
		this.wegZuvor = null;
		this.weightType = weightType;
		this.endKnoten = startKnoten;
		this.gesamtZiel = zielKnoten;
		this.routenLaenge = 0;
		this.distanzZumZiel = -1;
	}

	/**
	 * @return the endKnoten
	 */
	public Knoten getEndKnoten() {
		return endKnoten;
	}

	/**
	 * @return the routenLaenge
	 */
	public double getRoutenLaenge() {
		return routenLaenge;
	}

	/**
	 * @return the weg
	 */
	public LinkedList<Kante> getWeg() {
		if(this.wegZuvor == null){
			return new LinkedList<Kante>();
		}
		else{
			LinkedList<Kante> weg = this.wegZuvor.getWeg();
			weg.add(this.neueKante);
			return weg;
		}
	}

	/**
	 * @return the weightType
	 */
	public String getWeightType() {
		return weightType;
	}

	public Knoten getGesamtZiel() {
		return gesamtZiel;
	}

	public double getDistanzZumZiel() {
		if(this.distanzZumZiel < 0){
			this.distanzZumZiel = this.endKnoten.distance(gesamtZiel);
		}
		return this.distanzZumZiel;
		
	}
	
	public double getGeometricalDijkstraWeight(){
		return this.getDistanzZumZiel() + routenLaenge;
	}
}
