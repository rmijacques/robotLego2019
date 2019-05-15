package plateau;

import java.util.ArrayList;
import java.util.List;

import robot.Orientation;

/**
 * @author Rémi Jacques
 *
 */
public class Plateau {
	private GraphePlateau gp;
	private Case[][] cases;
	private int hauteur;
	private int largeur;
	private int nbPatients;
	private volatile List<Case> victimes;
	
	public List<Case> getVictimes() {
		return victimes;
	}

	public void setVictimes(List<Case> victimes) {
		this.victimes = victimes;
	}

	/**
	 * @param l La largeur du plateau.
	 * @param h La hauteur du plateau.
	 * @param listeCases La liste des cases du plateau.
	 */
	public Plateau(int l, int h, List<Case> listeCases) {
		cases = new Case[l][h];
		
		for(Case c:listeCases) {
			cases[c.getX()][c.getY()] = c;
		}
		gp = new GraphePlateau(l,h,cases);
		
		nbPatients = 0;
		hauteur = h;
		largeur = l;
		victimes = trouverCasesVictimes();
	}

	public GraphePlateau getGp() {
		return gp;
	}

	public Case[][] getCases() {
		return cases;
	}

	public int getHauteur() {
		return hauteur;
	}

	public int getLargeur() {
		return largeur;
	}
	
	/**
	 * @param x Coordonnée x.
	 * @param y Coordonnée y.
	 * @return Renvoie une case selon les coordonnées x et y.
	 */
	public Case getCaseByCoordinates(int x,int y) {
		return cases[x][y];
	}
	
	/**
	 * Renvoie la case suivante en fonction d'une case et de l'orientation.
	 * @param c La case sur laquelle on se trouve.
	 * @param orient L'orientation actuelle.
	 * @return Renvoie la case suivante.
	 */
	public Case getNextCase(Case c,Orientation orient) {
		int x = c.getX();
		int y = c.getY();
		
		switch(orient){
		case EAST:
			if(cases[x].length > y+1)
				return cases[x][y+1];
			break;
		case WEST:
			if(y-1 >= 0)
				return cases[x][y-1];
			break;
		case NORTH:
			if(x-1 >= 0)
				return cases[x-1][y];
			break;
		case SOUTH:
			if(x+1 < cases.length)
				return cases[x+1][y];
			break;
		}
		return null;
	}
	
	/**
	 * Rajoute un patient.
	 */
	public void addPatient() {
		nbPatients++;
		victimes = trouverCasesVictimes();
	}
	
	/**
	 * @param nbVictime Le nombre de patients sauvés.
	 */
	public void patientSauve(int nbVictime) {
		nbPatients = nbPatients - nbVictime;
	}
	
	public int getNbPatients() {
		return nbPatients;
	}

	/**
	 * @return Renvoie la liste des cases où un patient se trouve.
	 */
	public List<Case> trouverCasesVictimes() {
		List<Case> victimes = new ArrayList<>();
		
		for(Case[] c: cases) {
			for(Case c2: c) {
				if(c2.hasPatient())
					victimes.add(c2);
			}
		}
		
		return victimes;
	}
	
	/**
	 * @return Renvoie la liste des case où un hôpital se trouve.
	 */
	public List<Case> trouverCasesHopitaux() {
		List<Case> hopitaux = new ArrayList<>();
		
		for(Case[] c: cases) {
			for(Case c2: c) {
				if(c2.hasHopital())
					hopitaux.add(c2);
			}
		}
		return hopitaux;
	}
}

