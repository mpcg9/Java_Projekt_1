package controller.protokoll;

public interface Protokollierbar {

	//Methode für Protokollieren Polaren Anhängens
	void protokollierePolaresAnhaengen(double deltaX, double deltaY, double xNeu, double yNeu);

	//Methode für Protokollieren der Richtungswinkelberechnung
	void protokolliereRichtungswinkelBerechnung(double horRichtung, double horRichtungKorr, double richtungswinkelNeu,
			double brechungswinkel);

	//Methode für Protokollieren der Horizontalstreckenberechnung
	void protokolliereHorizontalstreckenBerechnung(double schraegstrecke, double horizontalstrecke);

	//Methode zur Ausgabe des Protokolls
	void protokollErstellen();

}