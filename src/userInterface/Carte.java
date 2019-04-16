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
	
	public Carte(Plateau plat) {
		super();
		this.plat = plat;
		boutonsCarte = new JButton[plat.getHauteur()][plat.getLargeur()];
		setMaximumSize(new Dimension(plat.getHauteur()*100,plat.getLargeur()*100));
		setLayout(new GridLayout(plat.getHauteur(),plat.getLargeur()));
		fabriquerLaCarte(plat.getCases(),plat.getHauteur(),plat.getLargeur());
	}
	
	public void fabriquerLaCarte(Case[][] cases,int h,int l) {
		JButton boutonCase;
		ImageIcon imageCase;
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<l;j++) {
				imageCase = new ImageIcon("./imagesCases/"+cases[i][j].getTypeImage()+".png");
				boutonCase = new BoutonCase(imageCase,i,j);
				boutonCase.addActionListener(this);
				boutonCase.setPreferredSize(new Dimension(100,100));
				boutonsCarte[i][j] = boutonCase;
				add(boutonCase);
			}
		}
	}
	
	public void updateCarte(Plateau plat) {
		JButton boutonCase;
		ImageIcon imageCase;

		int h = plat.getHauteur();
		int l = plat.getLargeur();
		
		Case[][] cases = plat.getCases();
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<l;j++) {
				remove(boutonsCarte[i][j]);
				imageCase = new ImageIcon("./imagesCases/"+cases[i][j].getHopital()+cases[i][j].getPatient()+cases[i][j].getTypeImage()+".png");
				boutonCase = new BoutonCase(imageCase,i,j);
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
			c.setPatient("patients/");
			plat.addPatient();;
		}
		else if(!c.hasHopital()) {
			c.setPatient("");
			plat.patientSauve(1);
			c.setHopital("hopitaux/");
		}
		else{
			c.setHopital("");
		}
		System.out.println(plat.getGp().toString());
		updateCarte(plat);
	}
	

}
