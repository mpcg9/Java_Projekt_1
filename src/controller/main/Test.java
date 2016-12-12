package controller.main;

import java.util.LinkedList;

import controller.laden.GraphFactory;
import model.dijkstraData.Kante;
import model.dijkstraNetwork.Graph;
import view.kml.RouteInKml;

public class Test {
	public static final Controller CONTROLLER = new Controller();
	
	public static void main(String[] args) {
		
		CONTROLLER.loadGraph("lib/bonn.xml");
		CONTROLLER.findNodes("Route.kml");
		CONTROLLER.findRoute("dist");
		CONTROLLER.saveKML("Test.kml");
	}

}
