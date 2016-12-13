package controller.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;


import view.gui.MeineGUI;

public class Main {

	public static final Controller CONTROLLER = new Controller();

	public static void main(String[] args) {
		CONTROLLER.loadGraph("lib/bonn.xml");
		
		// Erstellen der GUI
		MeineGUI gui = new MeineGUI("Routenplaner X-treme");
		
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gui.setLayout(new BorderLayout(5,5));
		JPanel westPanel = new JPanel(new GridLayout(6,1));
		gui.add(westPanel,BorderLayout.WEST);
		
		JButton kmlLoadFileButton = new JButton();
		kmlLoadFileButton.setText("Stra�ennetz laden");
		
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("dist");
		comboBox.addItem("car");
		
		
		/*
		JComboBox<String> comboBox1 = new JComboBox<String>();
		comboBox1.addItem("1");
		comboBox1.addItem("2");
		comboBox1.addItem("3");
		System.out.println(comboBox1.getSelectedIndex());
		System.out.println(comboBox1.getSelectedItem());

		JComboBox<String> comboBox2 = new JComboBox<String>();
		comboBox2.addItem("1");
		comboBox2.addItem("2");
		comboBox2.addItem("3");
		System.out.println(comboBox2.getSelectedIndex());
		System.out.println(comboBox2.getSelectedItem());
		
		
		westPanel.add(comboBox1);
		westPanel.add(comboBox2);
		*/
		
		JButton routeButton = new JButton();
		routeButton.setText("Route berechnen");
		
		JButton safeButton = new JButton();
		safeButton.setText("Route speichern als KML");
		
		westPanel.add(kmlLoadFileButton);
		westPanel.add(comboBox);
		westPanel.add(routeButton);
		westPanel.add(safeButton);
		
		JTextArea routedesc = new JTextArea();
		routedesc.setEditable(false);
		
		JScrollPane routedescWrapper = new JScrollPane();
		routedescWrapper.setViewportView(routedesc);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Stra�ennetz",        new JButton ("Stra�ennetz"));
		tabbedPane.addTab("Routenbeschreibung", routedescWrapper);
		tabbedPane.addTab("Route",              new JButton ("Route"));
		gui.add(tabbedPane);
		gui.pack();
		
		gui.setLocation(0, 0);
		gui.setSize(1200,800);
		gui.setVisible(true);
		
		// Hinzufuegen der Action Listener
		
		ActionListener kmlListener = new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent ae){
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML-Dateien", "xml");
				chooser.setFileFilter(filter);
				switch (chooser.showOpenDialog(null)) {
					case JFileChooser.APPROVE_OPTION:
						File file = chooser.getSelectedFile();
						CONTROLLER.loadGraph(file.getAbsolutePath());
						break;
					default:
						break;
				}
			}
		};
		kmlLoadFileButton.addActionListener(kmlListener);
		
		ActionListener routeListener = new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent ae){
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("KML-Dateien", "kml");
				chooser.setFileFilter(filter);
				switch (chooser.showOpenDialog(null)) {
					case JFileChooser.APPROVE_OPTION:
						File file = chooser.getSelectedFile();
						if(CONTROLLER.graphIsLoaded()){
							CONTROLLER.findNodes(file.getAbsolutePath());
							if(CONTROLLER.startEndNodesFound()){
								String[] descArray = CONTROLLER.findRoute((String) comboBox.getSelectedItem());
								String desc = new String();
								for(String s : descArray){
									desc = desc.concat(s + "\n");
								}
								routedesc.setText(desc);
							}
							else{
								JOptionPane.showMessageDialog(null , "Es konnte keine Route berechnet werden, da die eingegebenen Start- und Zielpunkte nicht gefunden werden konnten. Bitte �berpr�fe, ob du den Startpunkt als \"Start\" sowie den Zielpunkt als \"Ziel\" bezeichnet hast und ob sich beide Punkte in einem Ordner befinden!", "Start- oder Zielknoten nicht gefunden", 0);
							}
						}
						else{
							JOptionPane.showMessageDialog(null , "Es konnte keine Route berechnet werden, da kein Graph geladen wurde. Bitte lade einen Graphen ein!", "Graph nicht geladen", 0);
						}
						break;
					default:
						break;
				}
			}
		};
		routeButton.addActionListener(routeListener);
		
		ActionListener saveListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("KML-Dateien", "kml");
				chooser.setFileFilter(filter);
				switch (chooser.showOpenDialog(null)) {
					case JFileChooser.APPROVE_OPTION:
						File file = chooser.getSelectedFile();
						if(CONTROLLER.routeFound()){
							CONTROLLER.saveKML(file.getAbsolutePath());
						}
						else{
							JOptionPane.showMessageDialog(null , "Es konnte keine Route gespeichert werden, da noch keine Route berechnet wurde oder keine Route gefunden werden konnte!", "Keine Route gefunden!", 0);
						}
						break;
					default:
						break;
				}
			}
		};
		safeButton.addActionListener(saveListener);
	}

}
