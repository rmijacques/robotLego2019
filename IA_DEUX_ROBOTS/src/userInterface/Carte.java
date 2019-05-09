package userInterface;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import plateau.Case;
import plateau.Plateau;


public class Carte extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9145081978949472790L;
	JButton[][] boutonsCarte;
	Plateau plat;
	Logs log;
	
	public Carte(Plateau plat,Logs log) {
		super();
		this.plat = plat;
		boutonsCarte = new JButton[plat.getHauteur()][plat.getLargeur()];
		setMaximumSize(new Dimension(plat.getHauteur()*100,plat.getLargeur()*100));
		setLayout(new GridLayout(plat.getHauteur(),plat.getLargeur()));
		fabriquerLaCarte(plat.getCases(),plat.getHauteur(),plat.getLargeur());
		this.log = log;
	}
	
	public void fabriquerLaCarte(Case[][] cases,int h,int l) {
		JButton boutonCase;
		ImageIcon imageCase;
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<l;j++) {
				imageCase = new ImageIcon("./imagesCases/"+cases[i][j].getPatient()+cases[i][j].getHopital()+cases[i][j].getTypeImage()+cases[i][j].getRobot()+".png");
				boutonCase = new BoutonCase("",imageCase,i,j);
				boutonCase.addActionListener(this);
				boutonCase.setPreferredSize(new Dimension(100,100));
				boutonsCarte[i][j] = boutonCase;
				add(boutonCase);
			}
		}
	}
	
	public synchronized void updateCarte(Plateau plat) {
		int h = plat.getHauteur();
		int l = plat.getLargeur();
		Case[][] cases = plat.getCases();
		JButton boutonCase;
		ImageIcon imageCase;
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<l;j++) {
				remove(boutonsCarte[i][j]);
				imageCase = new ImageIcon("./imagesCases/"+cases[i][j].getPatient()+cases[i][j].getHopital()+cases[i][j].getTypeImage()+cases[i][j].getRobot()+".png");
				boutonCase = new BoutonCase("",imageCase,i,j);
				boutonCase.addActionListener(this);
				boutonCase.setPreferredSize(new Dimension(100,100));
				boutonsCarte[i][j] = boutonCase;
				add(boutonCase);
			}
		}
		this.updateUI();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		BoutonCase b = (BoutonCase) e.getSource();
		int x = b.posX;
		int y = b.posY;
		Case c = plat.getCaseByCoordinates(x, y);
		if(!c.hasPatient() && !c.hasHopital()) {
			c.addPatient();
			plat.addPatient();;
			log.addEvent("Patient ajouté sur la case "+c.toStringSimpl());
		}
		else if(!c.hasHopital()) {
			log.addEvent("Patient enlevé sur la case "+c.toStringSimpl());
			c.prendrePatient();
			plat.patientSauve(1);
			c.setHopital("hopitaux/");
			log.addEvent("Hopital ajouté sur la case "+c.toStringSimpl());
		}
		else{
			log.addEvent("Hopital enlevé sur la case "+c.toStringSimpl());
			c.setHopital("");
			c.prendrePatient();
		}
		System.out.println(plat.getGp().toString());
		updateCarte(plat);
	}
	

}
