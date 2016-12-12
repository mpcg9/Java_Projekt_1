package model.dijkstraData;

import java.awt.geom.Point2D;

public class Knoten extends Point2D.Double {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Attribute
	private String id;
	
	//Konstruktoren (erben von Oberklasse)
	public Knoten(){
		super();
		this.id = " ";
	}
	
	public Knoten(String wort){
		super();
		this.id = wort;
	}
	public Knoten(String id, double x, double y){
		super(x,y);
		this.id = id;
	}
	
	//Methoden
	/**
	 * Getter und toString
	 */
	public String getID() {
		return id;
	}
	public String toString() {
		return "Knoten [id=" + id + "]";
	}

}
