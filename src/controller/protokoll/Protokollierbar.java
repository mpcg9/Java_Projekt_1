package controller.protokoll;

public interface Protokollierbar {

	//Methode f�r Protokollieren Polaren Anh�ngens
	void protokollierePolaresAnhaengen(double deltaX, double deltaY, double xNeu, double yNeu);

	//Methode f�r Protokollieren der Richtungswinkelberechnung
	void protokolliereRichtungswinkelBerechnung(double horRichtung, double horRichtungKorr, double richtungswinkelNeu,
			double brechungswinkel);

	//Methode f�r Protokollieren der Horizontalstreckenberechnung
	void protokolliereHorizontalstreckenBerechnung(double schraegstrecke, double horizontalstrecke);

	//Methode zur Ausgabe des Protokolls
	void protokollErstellen();

}