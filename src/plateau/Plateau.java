package plateau;

import java.util.List;

import robot.Orientation;

public class Plateau {
	GraphePlateau gp;
	Case[][] cases;
	int hauteur;
	int largeur;
	int nbPatients;
	
	public Plateau(int l, int h, List<Case> listeCases) {
		cases = new Case[l][h];
		
		for(Case c:listeCases) {
			cases[c.getX()][c.getY()] = c;
		}
		gp = new GraphePlateau(l,h,cases);
		gp.toString();
		
		nbPatients = 0;
		hauteur = h;
		largeur = l;
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
	
	public Case getCaseByCoordinates(int x,int y) {
		return cases[x][y];
	}
	
	public Case getCaseSuivanteWithCommandAndOrientation(Case c,String command,Orientation orient) {
		List<Mouvement> listeMoves = gp.getMouvementsWithCase(c);
		
		for(Mouvement m: listeMoves) {
			if(m.getMouv().equals(command) && m.getOrient() == orient)
				return m.getDest();
		}

		return null;
	}
	
	public void addPatient() {
		nbPatients++;
	}
	
	public void patientSauve(int nbVictime) {
		nbPatients = nbPatients - nbVictime;
	}
	
	public int getNbPatients() {
		return nbPatients;
	}
}

