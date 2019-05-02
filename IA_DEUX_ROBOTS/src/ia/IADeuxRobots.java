package ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import plateau.Case;
import plateau.CaseOrientation;
import plateau.Plateau;
import robot.Orientation;
import robot.Robot;
import userInterface.Logs;

public class IADeuxRobots implements Runnable {
	private volatile Robot rAutonome;
	private volatile Robot rTelecommande;
	List<Case> hopitaux;
	private volatile Plateau plat;
	Boolean doNothing;
	Logs log;
	
	
	public IADeuxRobots(Robot rAuto,Robot rtel,Plateau plat,Logs log) {
		hopitaux = plat.trouverCasesHopitaux();
		this.rAutonome = rAuto;
		this.rTelecommande = rtel;
		this.plat =plat;
		this.log = log;
	}
	
	
	public void run() {
		Chemin cheminRobAutonome = new Chemin(null,null,null);
		Chemin cheminRobTelecommande = new Chemin(null,null,null);
		List<String> instructionsRobotAutonome = new ArrayList<>();
		List<String> instructionsRobotTel = new ArrayList<>();
		
		
		while(plat.getVictimes().size() > 0 || rAutonome.getNbVictime() != 0) {
			if(rTelecommande.getNbVictime() == 0)
				cheminRobTelecommande = trouverPlusProcheVictime(rTelecommande.getPosition(), rTelecommande.getDirection(),null);
			else
				cheminRobTelecommande = trouverPlusProcheHopital(rTelecommande.getPosition(), rTelecommande.getDirection());
			log.addEvent("Le meilleur chemin pour le robot Telecommande est :"+cheminRobTelecommande.getChemin());
			
			
			if(rAutonome.getNbVictime() == 0) {
				//On calcule le plus proche chemin pour les deux
				if(instructionsRobotAutonome.size() == 0) {
					cheminRobAutonome = trouverPlusProcheVictime(rAutonome.getPosition(), rAutonome.getDirection(),null);
					Collections.addAll(instructionsRobotAutonome,cheminRobAutonome.getChemin().split("\n"));
				}
		
				if(instructionsRobotTel.size() == 0) {
					Collections.addAll(instructionsRobotTel, cheminRobTelecommande.getChemin().split("\n"));
				}
				
				cheminRobAutonome = memeVictime(cheminRobAutonome, cheminRobTelecommande, instructionsRobotTel, instructionsRobotAutonome);
				verifierCollision(instructionsRobotAutonome, instructionsRobotTel);
				
				while(instructionsRobotAutonome.size() > 0 && !instructionsRobotAutonome.get(0).equals("t")) {
					rAutonome.traiterCommande(instructionsRobotAutonome.get(0));
					instructionsRobotAutonome.remove(0);
				}
				rAutonome.pick();
				
				if(instructionsRobotAutonome.size() != 0) 
					instructionsRobotAutonome.remove(0);

			}
			else {
				//On calcule le plus proche chemin pour les deux
				if(instructionsRobotAutonome.size() == 0) {
					cheminRobAutonome = trouverPlusProcheHopital(rAutonome.getPosition(), rAutonome.getDirection());
					Collections.addAll(instructionsRobotAutonome,cheminRobAutonome.getChemin().split("\n"));
				}
		
				if(instructionsRobotTel.size() == 0) {
					Collections.addAll(instructionsRobotTel, cheminRobTelecommande.getChemin().split("\n"));
				}
				
				//cheminRobAutonome = memeVictime(cheminRobAutonome, cheminRobTelecommande, instructionsRobotTel, instructionsRobotAutonome);
				verifierCollision(instructionsRobotAutonome, instructionsRobotTel);
				
				while(instructionsRobotAutonome.size() > 0 && !instructionsRobotAutonome.get(0).equals("d")) {
					rAutonome.traiterCommande(instructionsRobotAutonome.get(0));
					instructionsRobotAutonome.remove(0);
				}
				rAutonome.drop();
				
			}
		}
		
	}
	
	public Chemin memeVictime(Chemin cheminRobAutonome,Chemin cheminRobTelecommande,List<String> instructionsRobotTel,List<String> instructionsRobotAutonome) {
		if(cheminRobAutonome.getDest().equals(cheminRobTelecommande.getDest())) {
			//Si le robot autonome est le plus loin, on recalcule
			if(instructionsRobotAutonome.size() >= instructionsRobotTel.size()) {
				if(plat.getVictimes().size() == 1) {
					doNothing = true;
				}
				else {
					cheminRobAutonome = trouverPlusProcheVictime(rAutonome.getPosition(),rAutonome.getDirection() ,cheminRobAutonome.getDest());
					instructionsRobotAutonome.clear();
					Collections.addAll(instructionsRobotAutonome,cheminRobAutonome.getChemin().split("\n"));
				}
			}
			else {
				//Cas ou robot autonome est le plus proche(robot tel change de victime)
			}
			
		}
		return cheminRobAutonome;
	}
	
	
	
	public void verifierCollision(List<String> instructionsRobotAutonome,List<String> instructionsRobotTel) {
		List<CaseOrientation> parcoursAuto;
		List<CaseOrientation> parcoursTel;
		Case collision = null;
		Case aprCollision = null;
		
		parcoursAuto = casesParcouruesEtOrientations(instructionsRobotAutonome, rAutonome.getPosition(), rAutonome.getDirection(), rAutonome);
		parcoursTel = casesParcouruesEtOrientations(instructionsRobotTel, rTelecommande.getPosition(), rTelecommande.getDirection(), rTelecommande);
		collision = trouverCollision(parcoursTel, parcoursAuto);
		
		
		if(collision != null) {
			for(int i=0;i<parcoursAuto.size();i++) {
				if(parcoursAuto.get(i).getC().equals(collision) && i+1 < parcoursAuto.size()) {
					aprCollision = parcoursAuto.get(i+1).getC();
					break;
				}
			}
			log.addEvent("!!!!!!!!!!!!!   Attention collision prévue sur la case "+collision.toStringSimpl());
			if(aprCollision != null)
				log.addEvent("!!!!!!!!!!!!!   La case après la collision sera "+aprCollision.toStringSimpl());
		}
	}
	
	/**Traite le cas ou la dernière case est une case à 3 branches, il faut donc baser le choix de la derniere commande sur le nouveau chemin à suivre ensuite
	 * @param listeInst : Liste des instruction à effectuer
	 * @return : la liste des instruction à effectuer en effectuant le meilleur choix sur la dernière case
	 */
	public List<String> destVictimeCase3Branches(List<String> listeInst,Robot rob) {
		List<CaseOrientation> parcours = casesParcouruesEtOrientations(listeInst,rob.getPosition(),rob.getDirection(), rob);
		Case avantDer = parcours.get(parcours.size()-2).getC();
		Orientation avantDerO = parcours.get(parcours.size()-2).getOri();
		String[] chem;
		List<String> chemTemp = new ArrayList<>();
		
		chem = trouverPlusProcheHopital(avantDer,avantDerO).getChemin().split("\n");
		Collections.addAll(chemTemp, chem);
		
		if(!chem[0].equals("u")) {
			listeInst.set(listeInst.size()-1,chem[0]);
			listeInst.add("t");
			chemTemp.remove(0);
			listeInst.addAll(chemTemp);
		}

		return listeInst;
	}
	
	
	public List<String> destHopitalCase3Branches(List<String> listeInst,Robot rob) {
		List<CaseOrientation> parcours = casesParcouruesEtOrientations(listeInst,rob.getPosition(),rob.getDirection(), rob);
		Case avantDer = parcours.get(parcours.size()-2).getC();
		Orientation avantDerO = parcours.get(parcours.size()-2).getOri();
		String[] chem;
		List<String> chemTemp = new ArrayList<>();
		
		chem = trouverPlusProcheVictime(avantDer,avantDerO,null).getChemin().split("\n");
		Collections.addAll(chemTemp, chem);
		
		if(!chem[0].equals("u")) {
			listeInst.set(listeInst.size()-1,chem[0]);
			listeInst.add("d");
			chemTemp.remove(0);
			listeInst.addAll(chemTemp);
		}

		return listeInst;
	}
	
	
	
	/**
	 * @param listeInst : liste des commandes à effectuer
	 * @param cDepart : caseActuelle du robot
	 * @param oDepart : orientation actuelle du robot
	 * @return : un tableau contenant les coupkes case/orientation que le robot parcourrerait en suivant les instructions de listeInst
	 */
	public List<CaseOrientation> casesParcouruesEtOrientations(List<String> listeInst, Case cDepart, Orientation oDepart,Robot rob){
		List <CaseOrientation>couples = new ArrayList<>();
		Case cTemp = cDepart;
		Orientation oTemp = oDepart;
		
		for(String s: listeInst) {
			couples.add(new CaseOrientation(cTemp, oTemp));
			if(!s.equals("u"))
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
	public Chemin trouverPlusProcheVictime(Case caseDepart, Orientation orientDepart,Case notDis) {
		Recherche meilRech = null;
		String chemin;
		Chemin ret = new Chemin(caseDepart,null,null);
		Recherche rech;
		
		for(Case c:plat.getVictimes()) {
			if(notDis == null || !c.equals(notDis)) {
				rech = new Recherche(plat, caseDepart, c, orientDepart);
				chemin = rech.getInstructionsList();
				
				if(meilRech == null || (rech.getPileMin() <= meilRech.getPileMin())) {
					ret.setChemin(chemin);
					ret.setDest(c);
					meilRech = rech;
				}
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
	
	
	public Case trouverCollision(List<CaseOrientation> parcoursTel,List<CaseOrientation> parcoursAuto) {
		int i =0;
		while(i < parcoursTel.size() && i < parcoursAuto.size()) {
			if(parcoursAuto.get(i).getC().equals(parcoursTel.get(i).getC()))
				return parcoursAuto.get(i).getC();

			if(i<(parcoursTel.size()-1))
				if(parcoursAuto.get(i).getC().equals(parcoursTel.get(i+1).getC()))
					return parcoursAuto.get(i).getC();
			++i;
		}
		return null;
	}

	
//	public List<String> mouvEffectues(){
//		return tousLesMouvs;
//	}

	
}
