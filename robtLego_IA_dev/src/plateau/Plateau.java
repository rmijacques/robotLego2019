package plateau;

import java.util.ArrayList;
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
		System.out.println(gp.toString());
		
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
	
	public void addPatient() {
		nbPatients++;
	}
	
	public void patientSauve(int nbVictime) {
		nbPatients = nbPatients - nbVictime;
	}
	
	public int getNbPatients() {
		return nbPatients;
	}

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

