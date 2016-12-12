package controller.protokoll;
import java.io.*;

public class ProtokollDatei implements Protokollierbar{
	//Attribute
	private String protokoll;
	private String dateiname;
	
	//Konstruktoren
	public ProtokollDatei(String name){
		this.dateiname = name;
	}
	/*public ProtokollDatei(FileWriter instanz){
		this.datei = instanz;
	}*/
	
	//Methoden
	//Methode f�r Protokollieren Polaren Anh�ngens
	public void protokollierePolaresAnhaengen(double deltaX, double deltaY, double xNeu, double yNeu){
		String was = "\nNeupunkt mittels polarem Anhaengen erzeugt: \n"; //�berschrift
		//benutzerfreundlicher String
		String zeichenkette = "dX = "+deltaX +"\ndY = "+deltaY+"\nX = "+xNeu+"\nY = "+yNeu+"\n";
		//Hinzuf�gen zum Protokoll
		protokoll += was + zeichenkette;
	}
		
	//Methode f�r Protokollieren der Richtungswinkelberechnung
	public void protokolliereRichtungswinkelBerechnung(double horRichtung, double horRichtungKorr, double richtungswinkelNeu, double brechungswinkel){
		String was = "\nRichtungswinkel berechnet: \n"; //�berschrift
		//benutzerfreundlicher String
		String zeichenkette = "Richtung(Anschluss) = "+horRichtung+"\nRichtung(Neu) = "+horRichtungKorr+"\nRichtungswinkel(Neupunkt) = "+richtungswinkelNeu+"\nBrechungswinkel = "+brechungswinkel+"\n";
		//Hinzuf�gen zum Protokoll
		protokoll += was + zeichenkette;
	}
		
	//Methode f�r Protokollieren der Horizontalstreckenberechnung
	public void protokolliereHorizontalstreckenBerechnung(double schraegstrecke, double horizontalstrecke){
		String was = "\nHorizontalstrecke berechnet: \n"; //�berschrift
		//benutzerfreundlicher String
		String zeichenkette = "Schraegstrecke = "+schraegstrecke+"\nHorizontalstrecke = "+horizontalstrecke+"\n";
		//Hinzuf�gen zum Protokoll
		protokoll += was + zeichenkette;
	}
		
	//Methode zur Ausgabe des Protokolls
	public void protokollErstellen(){
		try {
			FileWriter fw = new FileWriter("./dateiname.txt", false);
			fw.write(protokoll);
			fw.close();
		    System.out.println(fw);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

}
