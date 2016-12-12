package controller.main;

import java.util.LinkedList;

import controller.laden.GraphFactory;
import controller.laden.NodeFinder;
import model.dijkstraData.Kante;
import model.dijkstraNetwork.Graph;
import view.kml.RouteInKml;

public class Controller {
	private Graph graph;
	private LinkedList<Kante> routeActive;
	private String[] startEndNodes;
	
	public Controller(){
		graph = null;
		routeActive = null;
		startEndNodes = new String[2];
	}
	
	public void loadGraph(String filename){
		GraphFactory gf = new GraphFactory();
		this.graph = gf.leseXml(filename);
	}
	
	public void berechneRoute(String knotenIDStart, String knotenIDEnde, String gewichtTyp){
		this.routeActive = this.graph.berechneRoute(knotenIDStart, knotenIDEnde, gewichtTyp);
	}
	
	public void findRoute(String gewichtTyp){
		this.routeActive = this.graph.berechneRoute(startEndNodes[0], startEndNodes[1], gewichtTyp);
	}
	
	public void findNodes(String filename){
		this.startEndNodes = NodeFinder.findNodes(filename, this.graph);
	}
	
	public void saveKML(String filename){
		RouteInKml.saveKmlFile(filename, routeActive, filename);
	}
	
	public boolean startEndNodesFound(){
		return this.startEndNodes[0] != null && this.startEndNodes[1] != null;
	}
	
	public boolean graphIsLoaded(){
		return this.graph != null;
	}
	
	public boolean routeFound(){
		return this.routeActive != null && !this.routeActive.isEmpty();
	}
}
