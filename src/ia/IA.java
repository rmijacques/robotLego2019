package ia;

import java.util.List;

import plateau.Case;
import plateau.Plateau;
import robot.Robot;

public class IA {
	List<Case> victimes;
	List<Case> hopitaux;
	Plateau plat;
	Robot rob;
	
	public IA(Plateau p,Robot rob) {
		this.rob = rob;
		System.out.println(rob.toString());
		this.plat = p;
		victimes = plat.trouverCasesVictimes();
		System.out.println(victimes.toString());
		hopitaux = plat.trouverCasesHopitaux();
		faireDesChoses();
	}
	
	public void faireDesChoses() {
		String chemin;
		Chemin chem;
		String[] listeInst;
		while(victimes.size() > 0 || rob.getNbVictime() != 0) {
			if(rob.getNbVictime() == 0) {
				//On calcule 2X le chemin, à améliorer
				chem = trouverPlusProcheVictime();
				chemin = chem.getChemin();
				victimes.remove(chem.getDest());
				System.out.println("passe une fois");
				listeInst = chemin.split("\n");
				for(String s:listeInst) {
					rob.bouger(s);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				rob.pick();
				
			}
			else {
				chem = trouverPlusProcheHopital();
				chemin = chem.getChemin();
				listeInst = chemin.split("\n");
				for(String s:listeInst)
					rob.bouger(s);
				rob.drop();
			}
		}
	}
	
	public Chemin trouverPlusProcheVictime() {
		Recherche rech;
		String meillChemin ="";
		String chemin;
		Chemin ret = new Chemin(rob.getPosition(),null,null);
		
		for(Case c:victimes) {
			rech = new Recherche(plat, rob.getPosition(), c, rob.getDirection());
			chemin = rech.getInstructionsList();
			if(meillChemin.length() == 0 || chemin.length() < meillChemin.length()) {
				ret.setChemin(chemin);
				ret.setDest(c);
			}
		}
		return ret;
	}
	
	
	public Chemin trouverPlusProcheHopital() {
		Recherche rech;
		String meillChemin = "";
		String chemin;
		Chemin ret = new Chemin(rob.getPosition(),null,null);
		
		for(Case c:hopitaux) {
			rech = new Recherche(plat, rob.getPosition(), c, rob.getDirection());
			chemin = rech.getInstructionsList();
			if(meillChemin.length() == 0 || chemin.length() < meillChemin.length()) {
				ret.setChemin(chemin);
				ret.setDest(c);
			}
		}
		return ret;
	}
	
	
}
