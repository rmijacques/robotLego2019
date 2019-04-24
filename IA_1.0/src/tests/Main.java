package tests;
import plateau.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

import connexionCommunication.Robot_bt;
import plateau.Plateau;
import userInterface.Carte;
import userInterface.Controller;
import userInterface.GestionRobots;
import userInterface.Logs;

public class Main extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2887664613212914716L;


	public Main() {
		super("Robot Lego");
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
	}
	
	
	public static void main(String [] args) {
		JFrame frame = new Main();
		Plateau plat;
		Logs log;
		Carte carte;
		GestionRobots gr;
		Controller ctrl;
		String nomFich;
		File fichCarte;
		Scanner scanCases;
		Robot_bt robt = null;
		
		
		int width;
		int height;
		String ligneCarte;
		String[] listeCasesLigne; 
		List<Case> lcases = new ArrayList<>();
		String nomRobot;
		String addrRobot;
		
		nomFich = (String)JOptionPane.showInputDialog(null,"Entrez le fichier de la carte :","Carte",JOptionPane.QUESTION_MESSAGE,null,null,"5X5FATTEST.txt");
		fichCarte = new File("./cartesTest/"+nomFich);
		
		nomRobot = (String)JOptionPane.showInputDialog(null,"Entrez le nom du robot :","Nom Robot",JOptionPane.QUESTION_MESSAGE,null,null,"glaedr");
		addrRobot = (String)JOptionPane.showInputDialog(null,"Entrez l'addresse bluetooth du robot :","Addr Bluetooth",JOptionPane.QUESTION_MESSAGE,null,null,"00:16:53:1C:15:FC");
		

			robt = new Robot_bt(addrRobot, nomRobot);
			robt.setMessage("colloc");
		
		
		try {
			scanCases = new Scanner(fichCarte);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			scanCases = null;
		}
		
		
		width = Integer.parseInt(scanCases.nextLine());
		height = Integer.parseInt(scanCases.nextLine());
		
		for(int i=0;i<width;i++) {
			ligneCarte = scanCases.nextLine();
			listeCasesLigne = ligneCarte.split(" ");
			
			for(int j=0;j<height;j++) {
				lcases.add(new Case(listeCasesLigne[j],i,j));
			}	
		}
		
		log = new Logs();
		plat = new Plateau(height,width,lcases);
		carte = new Carte(plat,log);
		ctrl= new Controller(log);
		gr = new GestionRobots(log,ctrl,plat,carte,robt);
		
		frame.getContentPane().add(gr);
		frame.getContentPane().add(Box.createHorizontalStrut(10));
		frame.getContentPane().add(new JSeparator(JSeparator.VERTICAL));
		frame.getContentPane().add(Box.createHorizontalStrut(10));
		frame.getContentPane().add(carte);
		frame.getContentPane().add(Box.createHorizontalStrut(10));
		frame.getContentPane().add(new JSeparator(JSeparator.VERTICAL));
		frame.getContentPane().add(Box.createHorizontalStrut(10));
		frame.getContentPane().add(ctrl);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		scanCases.close();
	}
	
	
	
}
