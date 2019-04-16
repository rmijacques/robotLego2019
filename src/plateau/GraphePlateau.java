package plateau;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import robot.Orientation;

//Stocke le plateau sous forme de map pour le faire passer par les algos de plus court chemin
public class GraphePlateau {
	private Map<Case, List<Mouvement>> graphe;
	
	public GraphePlateau(int hauteur,int largeur,Case[][] cases) {
		graphe = remplirGraphe(hauteur,largeur,cases);
		
	}
	
	//Construit un graphe des la carte avec une matrice 2D contenant les cases
	private Map<Case, List<Mouvement>> remplirGraphe(int hauteur,int largeur,Case[][] cases){
		this.graphe = new TreeMap<>();

		for(int i=0; i < hauteur; i++) {
			for(int j=0; j< largeur; j++) {
					graphe.put(cases[i][j], trouverPotos(hauteur,largeur,cases,i,j));
			}
		}
		
		return graphe;
		
	}


	private List<Mouvement> trouverPotos(int h,int l,Case[][] cases,int i,int j){
		List<Mouvement> mouvPossibles = new ArrayList<>();
		
		switch(cases[i][j].getTypeImage())
		{
		case "GD":
			if(j-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.EAST,cases[i][j-1]));
				mouvPossibles.add(new Mouvement("s",Orientation.WEST,cases[i][j-1]));
			}
			if(j+1 < l) {
				mouvPossibles.add(new Mouvement("u",Orientation.WEST,cases[i][j+1]));
				mouvPossibles.add(new Mouvement("s",Orientation.EAST,cases[i][j+1]));
			}
			break;
		case "HB":
			if(i-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.SOUTH,cases[i-1][j]));
				mouvPossibles.add(new Mouvement("s",Orientation.NORTH,cases[i-1][j]));
			}
			if(i+1 < h) {
				mouvPossibles.add(new Mouvement("u",Orientation.NORTH,cases[i+1][j]));
				mouvPossibles.add(new Mouvement("s",Orientation.SOUTH,cases[i+1][j]));
			}
			break;
		case "DB":
			if(j+1 < l) {
				mouvPossibles.add(new Mouvement("u",Orientation.WEST,cases[i][j+1]));
				mouvPossibles.add(new Mouvement("r",Orientation.NORTH,cases[i][j+1]));
			}
			if(i+1 < h) {
				mouvPossibles.add(new Mouvement("u",Orientation.NORTH,cases[i+1][j]));
				mouvPossibles.add(new Mouvement("l",Orientation.WEST,cases[i+1][j]));
			}
			break;
		case "GB":
			if(j-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.EAST,cases[i][j-1]));
				mouvPossibles.add(new Mouvement("l",Orientation.NORTH,cases[i][j-1]));
			}
			if(i+1 < h) {
				mouvPossibles.add(new Mouvement("u",Orientation.NORTH,cases[i+1][j]));
				mouvPossibles.add(new Mouvement("r",Orientation.EAST,cases[i+1][j]));
			}
			break;
		case "GDB":
			if(j-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.EAST,cases[i][j-1]));
				mouvPossibles.add(new Mouvement("l",Orientation.NORTH,cases[i][j-1]));
				mouvPossibles.add(new Mouvement("r",Orientation.WEST,cases[i][j-1]));
			}
			if(j+1 < l) {
				mouvPossibles.add(new Mouvement("u",Orientation.WEST,cases[i][j+1]));
				mouvPossibles.add(new Mouvement("l",Orientation.EAST,cases[i][j+1]));
				mouvPossibles.add(new Mouvement("r",Orientation.NORTH,cases[i][j+1]));
			}
			if(i+1 < h) {
				mouvPossibles.add(new Mouvement("u",Orientation.NORTH,cases[i+1][j]));
				mouvPossibles.add(new Mouvement("r",Orientation.EAST,cases[i+1][j]));
				mouvPossibles.add(new Mouvement("l",Orientation.WEST,cases[i+1][j]));
			}
			break;
		case "GH": 
			if(j-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.EAST,cases[i][j-1]));
				mouvPossibles.add(new Mouvement("r",Orientation.SOUTH,cases[i][j-1]));
			}
			if(i-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.SOUTH,cases[i-1][j]));
				mouvPossibles.add(new Mouvement("l",Orientation.EAST,cases[i-1][j]));
			}
			break;
		case "GHB":
			if(j-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.EAST,cases[i][j-1]));
				mouvPossibles.add(new Mouvement("r",Orientation.SOUTH,cases[i][j-1]));
				mouvPossibles.add(new Mouvement("l",Orientation.NORTH,cases[i][j-1]));
			}
			if(i-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.SOUTH,cases[i-1][j]));
				mouvPossibles.add(new Mouvement("l",Orientation.EAST,cases[i-1][j]));
				mouvPossibles.add(new Mouvement("r",Orientation.NORTH,cases[i-1][j]));
			}
			if(i+1 < h) {
				mouvPossibles.add(new Mouvement("u",Orientation.NORTH,cases[i+1][j]));
				mouvPossibles.add(new Mouvement("r",Orientation.EAST,cases[i+1][j]));
				mouvPossibles.add(new Mouvement("l",Orientation.SOUTH,cases[i+1][j]));
			}
			break;
		case "GHD":
			if(j-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.EAST,cases[i][j-1]));
				mouvPossibles.add(new Mouvement("l",Orientation.WEST,cases[i][j-1]));
				mouvPossibles.add(new Mouvement("r",Orientation.SOUTH,cases[i][j-1]));
			}
			if(i-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.SOUTH,cases[i-1][j]));
				mouvPossibles.add(new Mouvement("l",Orientation.EAST,cases[i-1][j]));
				mouvPossibles.add(new Mouvement("r",Orientation.WEST,cases[i-1][j]));
			}
			if(j+1 < l) {
				mouvPossibles.add(new Mouvement("u",Orientation.WEST,cases[i][j+1]));
				mouvPossibles.add(new Mouvement("r",Orientation.EAST,cases[i][j+1]));
				mouvPossibles.add(new Mouvement("l",Orientation.SOUTH,cases[i][j+1]));
			}
			break;
		case "HD":
			if(i-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.SOUTH,cases[i-1][j]));
				mouvPossibles.add(new Mouvement("r",Orientation.WEST,cases[i-1][j]));
			}
			if(j+1 < l) {
				mouvPossibles.add(new Mouvement("u",Orientation.WEST,cases[i][j+1]));
				mouvPossibles.add(new Mouvement("l",Orientation.SOUTH,cases[i][j+1]));
			}
			break;
		case "HDB":
			if(i-1 >= 0) {
				mouvPossibles.add(new Mouvement("u",Orientation.SOUTH,cases[i-1][j]));
				mouvPossibles.add(new Mouvement("l",Orientation.NORTH,cases[i-1][j]));
				mouvPossibles.add(new Mouvement("r",Orientation.WEST,cases[i-1][j]));
			}
			if(j+1 < l) {
				mouvPossibles.add(new Mouvement("u",Orientation.WEST,cases[i][j+1]));
				mouvPossibles.add(new Mouvement("l",Orientation.SOUTH,cases[i][j+1]));
				mouvPossibles.add(new Mouvement("r",Orientation.NORTH,cases[i][j+1]));
			}
			if(i+1 < h) {
				mouvPossibles.add(new Mouvement("u",Orientation.NORTH,cases[i+1][j]));
				mouvPossibles.add(new Mouvement("r",Orientation.SOUTH,cases[i+1][j]));
				mouvPossibles.add(new Mouvement("l",Orientation.WEST,cases[i+1][j]));
			}
			break;
		default :
			System.out.println("ben nan");
			
		}
		
		return mouvPossibles;
	}
	
	public String toString() {
		String ret ="";
		for(Map.Entry<Case, List<Mouvement>> e: graphe.entrySet()) {
			System.out.println(e.getKey().toString() + " -> " + e.getValue().toString());
		}
		return ret;
	}
	
	public List<Mouvement> getMouvementsWithCase(Case c) {
		return graphe.get(c);
	}
	
	
}
