package controller.protokoll;

public class ProtokollKonsole implements Protokollierbar { //neue Klasse ProtokollKonsole
	//Attribut
	private String protokoll;
	
	//Konstruktor
	public ProtokollKonsole(){
		this.protokoll = "";
	}
	
	//Methode f�r Protokollieren Polaren Anh�ngens
	/* (non-Javadoc)
	 * @see protokoll.Protokollierbar#protokollierePolaresAnhaengen(double, double, double, double)
	 */
	@Override
	public void protokollierePolaresAnhaengen(double deltaX, double deltaY, double xNeu, double yNeu){
		String was = "\nNeupunkt mittels polarem Anhaengen erzeugt: \n"; //�berschrift
		//benutzerfreundlicher String
		String zeichenkette = "dX = "+deltaX +"\ndY = "+deltaY+"\nX = "+xNeu+"\nY = "+yNeu+"\n";
		//Hinzuf�gen zum Protokoll
		protokoll += was + zeichenkette;
	}
	
	//Methode f�r Protokollieren der Richtungswinkelberechnung
	/* (non-Javadoc)
	 * @see protokoll.Protokollierbar#protokolliereRichtungswinkelBerechnung(double, double, double, double)
	 */
	@Override
	public void protokolliereRichtungswinkelBerechnung(double horRichtung, double horRichtungKorr, double richtungswinkelNeu, double brechungswinkel){
		String was = "\nRichtungswinkel berechnet: \n"; //�berschrift
		//benutzerfreundlicher String
		String zeichenkette = "Richtung(Anschluss) = "+horRichtung+"\nRichtung(Neu) = "+horRichtungKorr+"\nRichtungswinkel(Neupunkt) = "+richtungswinkelNeu+"\nBrechungswinkel = "+brechungswinkel+"\n";
		//Hinzuf�gen zum Protokoll
		protokoll += was + zeichenkette;
	}
	
	//Methode f�r Protokollieren der Horizontalstreckenberechnung
	/* (non-Javadoc)
	 * @see protokoll.Protokollierbar#protokolliereHorizontalstreckenBerechnung(double, double)
	 */
	@Override
	public void protokolliereHorizontalstreckenBerechnung(double schraegstrecke, double horizontalstrecke){
		String was = "\nHorizontalstrecke berechnet: \n"; //�berschrift
		//benutzerfreundlicher String
		String zeichenkette = "Schraegstrecke = "+schraegstrecke+"\nHorizontalstrecke = "+horizontalstrecke+"\n";
		//Hinzuf�gen zum Protokoll
		protokoll += was + zeichenkette;
	}
	
	//Methode zur Ausgabe des Protokolls
	/* (non-Javadoc)
	 * @see protokoll.Protokollierbar#protokollErstellen()
	 */
	@Override
	public void protokollErstellen(){
		System.out.println(this.protokoll);
	}

}
