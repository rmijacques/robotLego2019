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

/**
 * @author colloc
 *
 */

public class IADeuxRobots implements Runnable {
	private volatile Robot rAutonome;
	private volatile Robot rTelecommande;
	List<Case> hopitaux;
	private volatile Plateau plat;
	Boolean doNothing;
	Logs log;
	
	
	/**
	 * @param rAuto : Le robot telecommande
	 * @param rtel : le robot autonome
	 * @param plat : le plateau
	 * @param log : le log pour afficher les informations
	 */
	public IADeuxRobots(Robot rAuto,Robot rtel,Plateau plat,Logs log) {
		hopitaux = plat.trouverCasesHopitaux();
		this.rAutonome = rAuto;
		this.rTelecommande = rtel;
		this.plat = plat;
		this.log = log;
		this.doNothing = false;
	}
	
	public void run() {
		Chemin cheminRobAutonome = new Chemin(null,null,null);
		Chemin cheminRobTelecommande = new Chemin(null,null,null);
		String instructionRobotAutonome;
		Case destinationRobAuto = null;
		Case prochaineCaseAuto;

		while(plat.getVictimes().size() > 0 || rAutonome.getNbVictime()>0) {
			if(rTelecommande.getNbVictime() < 2)
				cheminRobTelecommande = trouverPlusProcheVictime(rTelecommande.getPosition(), rTelecommande.getDirection(),null);
			else
				cheminRobTelecommande = trouverPlusProcheHopital(rTelecommande.getPosition(), rTelecommande.getDirection());
			prochaineCaseAuto = plat.getNextCase(rAutonome.getPosition(), rAutonome.getDirection());
			if(rAutonome.getNbVictime() < 2 && plat.getVictimes().size() > 0) {
				cheminRobAutonome = trouverPlusProcheVictime(rAutonome.getPosition(), rAutonome.getDirection(),null);
				cheminRobAutonome = memeVictime(cheminRobAutonome, cheminRobTelecommande);
				destinationRobAuto = cheminRobAutonome.getDest();
				if(destinationRobAuto.equals(prochaineCaseAuto) && !destinationRobAuto.isCase2()) {
					instructionRobotAutonome = destVictimeCase3Branches(rAutonome.getPosition(), prochaineCaseAuto, rAutonome.getDirection(),"");
				}
				else {
					instructionRobotAutonome = cheminRobAutonome.getChemin().split("\n")[0];
				}
				if(!collisionProchaineCase()) {
					rAutonome.traiterCommande(instructionRobotAutonome);
				}
				if(destinationRobAuto.equals(rAutonome.getPosition())) {
					rAutonome.pick();
				}
				if(rAutonome.getPosition().hasHopital() && rAutonome.getNbVictime()> 0)
					rAutonome.drop();
				
				
			}
			else {
				cheminRobAutonome = trouverPlusProcheHopital(rAutonome.getPosition(), rAutonome.getDirection());
				destinationRobAuto = cheminRobAutonome.getDest();
				if(destinationRobAuto.equals(prochaineCaseAuto) && !destinationRobAuto.isCase2()) {
					instructionRobotAutonome = destHopitalCase3Branches(rAutonome.getPosition(),prochaineCaseAuto,rAutonome.getDirection());
				}
				else {
					instructionRobotAutonome = cheminRobAutonome.getChemin().split("\n")[0];
				}
				if(!collisionProchaineCase()) {
					rAutonome.traiterCommande(instructionRobotAutonome);
					if(rAutonome.getPosition().hasHopital()) {
						rAutonome.drop();
						if(rAutonome.getPosition().isCase2()) {
							fuir();
						}
						else if((plat.getVictimes().size() == 0  && rTelecommande.getNbVictime() > 0)){
							rAutonome.testerTout();
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(plat.getVictimes().size() == 0 && rAutonome.getNbVictime() == 0) {
							break;
						}
					}
				}
			}
		}
		
	}
	
	
	/**Detecte un cas de collision sur la prochain mouvement du robot autonome et l'empeche d'avancer tant qu'il y a collision
	 * @return true si collision sinon false
	 */
	public boolean collisionProchaineCase() {
		Case prochaineCaseAuto = plat.getNextCase(rAutonome.getPosition(), rAutonome.getDirection());
		Case prochaineCaseTel = plat.getNextCase(rTelecommande.getPosition(), rTelecommande.getDirection());
		if(prochaineCaseAuto.equals(prochaineCaseTel) || prochaineCaseAuto.equals(rTelecommande.getPosition()))
			return true;
		return false;
		
	}

	/**
	 * Quand le rob autonome n'a plus de victime à ramasser il doit s'enfuir de l'hopital par le meilleur trajet
	 * pour ne pas handicaper le robot télécommandé
	 */
	private void fuir() {
		Chemin chemRobotTel;
		List<CaseOrientation> parcours;
		List<String> listeInst = new ArrayList<>();
		Case avantCollision;
		Case avantAvantCollision;
		Case positionAuto = rAutonome.getPosition();
		Case nextCaseAuto = plat.getNextCase(rAutonome.getPosition(), rAutonome.getDirection());
		
		chemRobotTel = trouverPlusProcheHopital(rTelecommande.getPosition(), rTelecommande.getDirection());
		
		try {
			if(rTelecommande.getNbVictime() == 0 && plat.getVictimes().size() > 0 && chemRobotTel.getDest().equals(nextCaseAuto)) {
				synchronized(plat) {
					plat.wait();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		Collections.addAll(listeInst , chemRobotTel.getChemin().split("\n"));
		parcours = casesParcouruesEtOrientations(listeInst, rTelecommande.getPosition(), rTelecommande.getDirection(), rTelecommande);
		avantCollision = parcours.get(parcours.size()-2).getC();
		avantAvantCollision = parcours.get(parcours.size()-2).getC();

		if(positionAuto.isCase2()) {
			if(nextCaseAuto.equals(avantCollision)){
				rAutonome.traiterUturn();
				rAutonome.testerTout();
			}
			else {
				rAutonome.testerTout();
			}
		}
		else {
			if(!nextCaseAuto.equals(avantCollision)){
				rAutonome.testerTout();
			}
			else {
				if(avantAvantCollision.isCase2()) {
					rAutonome.traiterCommande("s");
					rAutonome.traiterCommande("u");
					rAutonome.testerTout();
				}
				else {
					
				}
					
			}
		}
		
		
	}


	/** Dans le cas ou le robot autonome et tel sont les plus proches de la meme victime, met à jour la victime du robot autonome
	 * @param cheminRobAutonome
	 * @param cheminRobTelecommande
	 * @param instructionsRobotTel
	 * @param instructionsRobotAutonome
	 * @return le nouveau chemin (ou pas) du robot autonome
	 */
	public Chemin memeVictime(Chemin cheminRobAutonome,Chemin cheminRobTelecommande) {
		if(cheminRobAutonome.getDest().equals(cheminRobTelecommande.getDest())) {
			//Si le robot autonome est le plus loin, on recalcule
			if(cheminRobAutonome.getChemin().length() >= cheminRobTelecommande.getChemin().length()) {
				if(plat.getVictimes().size() == 1) {
					doNothing = true;
				}
				else {
					cheminRobAutonome = trouverPlusProcheVictime(rAutonome.getPosition(),rAutonome.getDirection() ,cheminRobAutonome.getDest());
				}
			}		
		}
		return cheminRobAutonome;
	}
	
	/**Traite le cas ou la dernière case est une case à 3 branches, il faut donc baser le choix de la derniere commande sur le nouveau chemin à suivre ensuite
	 * @param listeInst : Liste des instruction à effectuer
	 * @return : la liste des instruction à effectuer en effectuant le meilleur choix sur la dernière case
	 */
	public String destVictimeCase3Branches(Case cActuelle,Case cVictime,Orientation oActuelle,String mouv) {
		String[] chem;
		
		chem = trouverPlusProcheHopital(cActuelle,oActuelle).getChemin().split("\n");
		
		if(!chem[0].equals("u")) {
			return chem[0];
		}
		return mouv;
	}
	
	
	/**Traite le case ou l'hopital est sur une case a 3 branche quand le robot télécommandé y va, choisi le mouvement à faire pour que l'entrée dans la case soit optimale par rapport au prochain objectif
	 * @param cActuelle
	 * @param cHopital
	 * @param orActuelle
	 * @return
	 */
	public String destHopitalCase3Branches(Case cActuelle,Case cHopital,Orientation orActuelle) {
		Chemin chemRobotTel;
		List<CaseOrientation> parcours;
		List<String> listeInst = new ArrayList<>();
		Case avantCollisionTel;
		Case destTel = (trouverPlusProcheHopital(rTelecommande.getPosition(), rTelecommande.getDirection())).getDest();
		
		
		try {
			if(rTelecommande.getNbVictime() == 0 && plat.getVictimes().size() > 0 && destTel.equals(cHopital)) {
				synchronized(plat) {
					plat.wait();
				}
			}
		} catch (InterruptedException e) {
				e.printStackTrace();
		}

		chemRobotTel = trouverPlusProcheHopital(rTelecommande.getPosition(), rTelecommande.getDirection());
		Collections.addAll(listeInst , chemRobotTel.getChemin().split("\n"));
		parcours = casesParcouruesEtOrientations(listeInst, rTelecommande.getPosition(), rTelecommande.getDirection(), rTelecommande);
		avantCollisionTel = parcours.get(parcours.size()-2).getC();


		if(nextCase(true,new CaseOrientation(cActuelle, orActuelle)) != null && nextCase(true,new CaseOrientation(cActuelle, orActuelle)).equals(avantCollisionTel))
			return "r";
		else
			return "l";
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
		synchronized(plat) {
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
				ret.setDest(c);
				meilRech = rech;
			}
		}
		return ret;
	}
	

	
	/**Renvoi la prochaine case apres un mouvement et une orientation
	 * @param leftOrRight : true si on tourne à gauche false si on tourne à droite
	 * @param caseOr :CaseOrientation avant le mouvement
	 * @return la case après le mouvement
	 */
	public Case nextCase(boolean leftOrRight, CaseOrientation caseOr) {
        Orientation orienTemp;
        Case caseTemp;
        
        if(leftOrRight)
            orienTemp = Orientation.nouvOrientApresMouv(caseOr.getOri(), "l", caseOr.getC().getTypeImage());
        else            
            orienTemp = Orientation.nouvOrientApresMouv(caseOr.getOri(), "r", caseOr.getC().getTypeImage());        
        caseTemp = plat.getNextCase(caseOr.getC(), caseOr.getOri());
        caseOr = new CaseOrientation(caseTemp, orienTemp);
        caseTemp = plat.getNextCase(caseTemp, orienTemp);
        return caseTemp;
    }
	
}
