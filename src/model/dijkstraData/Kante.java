package model.dijkstraData;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;

public class Kante {
	//Attribute
	private String id;
	private String name;
	private Knoten anfang;
	private Knoten ende;
	private HashMap<String, Double> gewichte; 
	private LinkedList<Point2D.Double> geometry;
	
	//Konstruktoren
	public Kante(String id){
		this.id = id;
		this.name = "";
		this.anfang = null;
		this.ende = null;
		this.gewichte = new HashMap<String,Double>();
		this.geometry = new LinkedList<Point2D.Double>();
	}
	
	public Kante(String id, Knoten anfang, Knoten ende){
		this.id = id;
		this.name = "";
		this.anfang = anfang;
		this.ende = ende;
		this.gewichte = new HashMap<String,Double>();
		this.geometry = new LinkedList<Point2D.Double>();
	}
	
	public Kante(String id, String name, Knoten anfang, Knoten ende){
		this.id = id;
		this.name = name;
		this.anfang = anfang;
		this.ende = ende;
		this.gewichte = new HashMap<String,Double>();
		this.geometry = new LinkedList<Point2D.Double>();
	}
	
	public Kante(String id, String name, Knoten anfang, Knoten ende, HashMap<String, Double> gewichte){
		this.id = id;
		this.name = name;
		this.anfang = anfang;
		this.ende = ende;
		this.gewichte = gewichte;
		this.geometry = new LinkedList<Point2D.Double>();
	}
	
	public Kante(String id, String name, Knoten anfang, Knoten ende, HashMap<String, Double> gewichte, 
			LinkedList<java.awt.geom.Point2D.Double> geometry) {
		this.id = id;
		this.name = name;
		this.anfang = anfang;
		this.ende = ende;
		this.gewichte = gewichte;
		this.geometry = geometry;
	}

	//Methoden
	
	/**
	 * giving out the angle between this edge and the given edge in degree
	 * @param k the edge
	 * @return the angle in degree
	 */
	public double getWinkelGradNaechst(Kante k) {
		double ax = k.getGeometry().get(1).getX() - k.getGeometry().getFirst().getX();		//Indizesrichtig? 1=2tes Element?
		double ay = k.getGeometry().get(1).getY() - k.getGeometry().getFirst().getY();
		double bx = geometry.getLast().getX() - geometry.get(geometry.size()-2).getX();
		double by = geometry.getLast().getY() - geometry.get(geometry.size()-2).getY();
		return Math.acos((ax * bx + ay * by)/(k.getGeometry().getFirst().distance(k.getGeometry().get(1))*geometry.getLast().distance(geometry.get(geometry.size()-2))))*180/Math.PI;
	}
	
	/**
	 * method for deciding whether or not this knot lies to the left of the edge
	 * @param k the edge
	 * @return true if and only if this knot lies to the left of the given edge
	 */
	public boolean nextIsToTheLeft(Kante k) {
		double ax = geometry.getLast().getX() - geometry.get(geometry.size()-2).getX();		//Indizesrichtig? 1=2tes Element?
		double ay = geometry.getLast().getY() - geometry.get(geometry.size()-2).getY();
		double bx = k.getGeometry().get(1).getX() - geometry.getLast().getX();;
		double by = k.getGeometry().get(1).getY() - geometry.getLast().getY();
		return (ax * by - ay * bx > 0.0);
	}
	
	/*
	 * Getter/Setter-Methoden und toString
	 */
	
	/**
	 * Fügt der Kante ein Gewicht hinzu.
	 * @param type Type des Gewichts
	 * @param value Gewicht
	 */
	public void addGewicht(String type, double value){
		this.gewichte.put(type, value);
	}
	
	/**
	 * Liefert das Gewicht dieser Kante, welches den Typ type besitzt.
	 * @param type Gewünschtes Gewicht
	 * @return Wert des Gewichts.
	 */
	public double getGewicht(String type){
		return this.gewichte.get(type);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Knoten getAnfang() {
		return anfang;
	}
	public void setAnfang(Knoten anfang) {
		this.anfang = anfang;
	}
	public Knoten getEnde() {
		return ende;
	}
	public void setEnde(Knoten ende) {
		this.ende = ende;
	}
	public HashMap<String, Double> getGewichte() {
		return gewichte;
	}
	public void setGewichte(HashMap<String, Double> gewichte) {
		this.gewichte = gewichte;
	}

	/**
	 * @return the geometry
	 */
	public LinkedList<Point2D.Double> getGeometry() {
		return geometry;
	}

	/**
	 * @param geometry the geometry to set
	 */
	public void setGeometry(LinkedList<Point2D.Double> geometry) {
		this.geometry = geometry;
	}
}
