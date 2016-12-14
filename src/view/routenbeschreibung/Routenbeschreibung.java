/**
 * @author Hendrik
 */
package view.routenbeschreibung;

import model.dijkstraData.Kante;
import model.dijkstraData.Knoten;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Routenbeschreibung {
	private static final double mZkm = 3000;		//Distanz in Metern, ab der die Entfernung in Kilometern statt in Metern angegeben wird
	private static final String [] Abbiegegrade = {" weiter geradeaus fahren"," halb"," nach "," scharf nach "};
	private static final String [] Richtungen = {"rechts","links"};
	private static final String [] Auf = {" auf ", " und ", " auf die ", " und der ", " auf den ", " und dem ", " weiter folgen"};

	/**
	 * @param routenbeschreibung
	 */
	public Routenbeschreibung() {
		super();
	}
	
	 public static String[] erzeugeRoutenbeschreibung(LinkedList<Kante> listeDerKantenAufDerRoute) {
		 int s = listeDerKantenAufDerRoute.size();
		 int abbiegegrad;
		 String was = null;
		 String wohin = null;
		 String wieWeit = null;
		 double Teilentfernung = 0;
		 double gesamtEntfernung = 0;
		 String [] Richtung_vorl = {"",""};
		 String [] route = new String[s+2];
		 
		 for (int i=0; i<(s-1); i++) {
			 Kante k = listeDerKantenAufDerRoute.get(i);
			 Kante n = listeDerKantenAufDerRoute.get(i+1);
			 Teilentfernung = k.getGewicht("dist");
			 gesamtEntfernung += Teilentfernung;
			 wieWeit = Routenbeschreibung.getStringWieWeit(Teilentfernung); // Entfernung in m oder km
			 wohin = Routenbeschreibung.getStringWohin(k.getName(), n.getName()); // abbiegender Straßenverlauf oder abbiegen auf andere Straße
			 Richtung_vorl[1] = Richtungen[k.nextIsToTheLeft(n)?1:0].concat(" abbiegen");
			 abbiegegrad = (int)Math.floor((k.getWinkelGradNaechst(n)+22.5)/45);
			 was = Abbiegegrade[abbiegegrad].concat(Richtung_vorl[abbiegegrad!=0 ? 1 : 0]);
			 route[i+2] = String.format("Bitte in " + wieWeit + was + wohin + ".");
		 }
		 gesamtEntfernung += listeDerKantenAufDerRoute.getLast().getGewicht("dist");
		 wieWeit = Routenbeschreibung.getStringWieWeit(listeDerKantenAufDerRoute.getLast().getGewicht("dist")); // Entfernung in m oder km
		 route[0] = String.format("Die Länge der berechneten Route beträgt: " + Routenbeschreibung.getStringWieWeit(gesamtEntfernung) + ".");
		 //route[1] = "Bitte in Richtung " + Routenbeschreibung.getHimmelsrichtung(listeDerKantenAufDerRoute.getFirst().getGeometry().get(0),listeDerKantenAufDerRoute.getFirst().getGeometry().get(1)) + " losfahren!";
		 route[1] = "Bitte losfahren!";
		 route[s+1] = String.format("In " + wieWeit + " haben sie ihr Ziel erreicht."); 
		 return route;
	 }
	 
	 private static String getHimmelsrichtung (Point2D.Double p1,Point2D.Double p2) {
		 String [] Himmelsrichtungen = {"Nord-Osten","Osten","Süd-Osten","Süden","Süd-Westen","Westen","Nord-Westen","Norden"};
		 double richtungGrad = (Math.atan2(p2.getY()-p1.getY(),p2.getX()-p1.getX())*360/Math.PI);
		 //double richtungGrad = (Math.atan2(p2.getY()-p1.getY(),p2.getX()-p1.getX())*360/Math.PI)+(360/16)+400;
		 //return Himmelsrichtungen[7-(int)Math.floor((richtungGrad%360)/(360/8))];
		 return String.format("%1$.3f°", richtungGrad);
	 }
	 
	 private static String getStringWohin (String aktuellerName, String nachsterName) {
		 int geschlecht = 0;		 
		 if (nachsterName == null) {
			 return "";
		 } else {
			 if(nachsterName.endsWith("eg") || nachsterName.endsWith("erg") || nachsterName.endsWith("latz")){
				 geschlecht = 2;
			 } else if (nachsterName.endsWith("traße") || nachsterName.endsWith("asse") || nachsterName.endsWith("llee") || nachsterName.endsWith("ücke")){
				 geschlecht = 1;
			 }
			 if (nachsterName.equals(aktuellerName)) {
				 return String.format(Auf[1+2*geschlecht] + "'" + nachsterName + "'" + Auf[6]);
			 } else {
				 return String.format(Auf[0+2*geschlecht] + "'" + nachsterName + "'");
			 }
		 }
	 }
	 
	 private static String getStringWieWeit (double distanz) {
		 if (distanz < mZkm) {
			 return String.format("%1$.0f m", distanz);
		 } else {
			 return String.format("%1$.1f km", distanz/1000);
		 }
	 }

}
