

package ia;

import java.util.ArrayList;
import java.util.List;

import plateau.Case;
import plateau.CaseOrientation;
import plateau.Plateau;
import robot.Orientation;
import robot.Robot;

/**
 * @author JACQUES Rémi
 *
 */

public class IA {
	List<Case> victimes;
	List<Case> hopitaux;
	Plateau plat;
	Robot rob;
	Recherche rech;
	
	public IA(Plateau p,Robot rob) {
		this.rob = rob;
		this.plat = p;
		victimes = plat.trouverCasesVictimes();
		hopitaux = plat.trouverCasesHopitaux();
	}
	
	/**
	 * "Routine" de l'IA, tant qu'il y a des victimes sur le plateau on les trouve et on les amènent à l'hopital
	 */
	public void faireDesChoses() {
		Chemin chemim;
		String[] listeInst;
		
		while(victimes.size() > 0 || rob.getNbVictime() != 0) {
			if(rob.getNbVictime() == 0) {	
				chemim = trouverPlusProcheVictime(rob.getPosition(), rob.getDirection());
				victimes.remove(chemim.getDest());
				listeInst = chemim.getChemin().split("\n");
				
				if(! chemim.getDest().isCase2()) {
					listeInst = destCase3Branches(listeInst);	
				}
			
				rob.traiterMultiCommandes(listeInst);
				rob.pick();
			}
			else {
				chemim = trouverPlusProcheHopital(rob.getPosition(), rob.getDirection());
				listeInst = chemim.getChemin().split("\n");
				rob.traiterMultiCommandes(listeInst);
				rob.drop();
			}
		}
	}
	
	/**Traite le cas ou la dernière case est une case à 3 branches, il faut donc baser le choix de la derniere commande sur le nouveau chemin à suivre ensuite
	 * @param listeInst : Liste des instruction à effectuer
	 * @return : la liste des instruction à effectuer en effectuant le meilleur choix sur la dernière case
	 */
	public String[] destCase3Branches(String[] listeInst) {
		List<CaseOrientation> parcours = casesParcouruesEtOrientations(listeInst,rob.getPosition(),rob.getDirection());
		Case avantDer = parcours.get(parcours.size()-2).getC();
		Orientation avantDerO = parcours.get(parcours.size()-2).getOri();
		
		listeInst[listeInst.length-1] = trouverPlusProcheHopital(avantDer,avantDerO).getChemin().split("\n")[0];
		
		return listeInst;
	}
	
	
	
	/**
	 * @param listeInst : liste des commandes à effectuer
	 * @param cDepart : caseActuelle du robot
	 * @param oDepart : orientation actuelle du robot
	 * @return : un tableau contenant les coupkes case/orientation que le robot parcourrerait en suivant les instructions de listeInst
	 */
	public List<CaseOrientation> casesParcouruesEtOrientations(String[] listeInst, Case cDepart, Orientation oDepart){
		List <CaseOrientation>couples = new ArrayList<>();
		Case cTemp = cDepart;
		Orientation oTemp = oDepart;
		
		for(String s: listeInst) {
			couples.add(new CaseOrientation(cTemp, oTemp));
			cTemp = plat.getNextCase(cTemp,oTemp);
			oTemp = Orientation.nouvOrientApresMouv(oTemp, s, cTemp.getTypeImage());
		}
		couples.add(new CaseOrientation(cTemp, oTemp));
		
		return couples;
	}
	
	
	
	/**
	 * @param caseDepart: position de l'ambulance (fictive ou réelle) au début de la recherche
	 * @param orientDepart : orientation de l'ambulance (fictive ou réelle au déébut de la recherche
	 * @return Le chemin pour aller vers la plus proche victime
	 */
	public Chemin trouverPlusProcheVictime(Case caseDepart, Orientation orientDepart) {
		Recherche meilRech = null;
		String chemin;
		Chemin ret = new Chemin(rob.getPosition(),null,null);
		Recherche rech;
		
		for(Case c:victimes) {
			rech = new Recherche(plat, caseDepart, c, orientDepart);
			chemin = rech.getInstructionsList();
			
			if(meilRech == null || (rech.getPileMin() < meilRech.getPileMin())) {
				ret.setChemin(chemin);
				ret.setDest(c);
				meilRech = rech;
			}
		}
		return ret;
	}
	
	
	
	/**
	 * @param caseDepart: position de l'ambulance (fictive ou réelle) au début de la recherche
	 * @param orientDepart : orientationx de l'ambulance (fictive ou réelle au déébut de la recherche
	 * @return Le chemin pour aller vers le plus proche hopital
	 */
	public Chemin trouverPlusProcheHopital(Case caseDepart, Orientation orientDepart) {
		Recherche meilRech = null;
		String chemin;
		Chemin ret = new Chemin(caseDepart,null,null);
		Recherche rech;
		
		for(Case c:hopitaux) {
			rech = new Recherche(plat, caseDepart, c, orientDepart);
			chemin = rech.getInstructionsList();
			
			if(meilRech == null || (rech.getPileMin() < meilRech.getPileMin())) {
				ret.setChemin(chemin);
				System.out.println("chemin ="+chemin);
				ret.setDest(c);
				meilRech = rech;
			}
		}
		return ret;
	}
	

	
	
}
