package ia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plateau.*;
import robot.Orientation;

/**
 * @author Pierre Gavrilov
 *
 */
public class Recherche {
    private Case caseActu;
    private Case caseDest;
    private Map<Case, Integer> graphe;
    private Map<Case, List<Case>> gpia;
    private Orientation orient;
    private String instructionsList;
	private int pileMin;
    private Plateau p;
    
    /**
     * Algorithme de recherche basé sur l'algorithme de Djikstra.
     * Il calcule le chemin le plus court jusqu'à une case donnée, et stocke la suite d'instructions trouvée dans instructionsList.
     * Il suffit d'initialiser la classe pour générer le meilleur chemin.
     * Pour récupérer la suite d'instructions, voir {@link ia.Recherche#getInstructionsList()}.
     * @see ia.Recherche#getInstructionsList()
     * @param p
     * Le plateau sur lequel on se déplace.
     * @param caseActu
     * La case de départ de la recherche.
     * @param caseDest
     * La case que l'on souhaite atteindre
     * @param orient
     * L'orientation de départ de la recherche.
     */
	public Recherche(Plateau p, Case caseActu, Case caseDest, Orientation orient) {
        this.orient = orient;
        this.p = p;
        this.gpia = p.getGp().getGraphe();
        this.graphe = new HashMap<>();
        this.caseActu = caseActu;
        this.caseDest = caseDest;
        this.pileMin = 0;
        instructionsList = "";
        init();
        trouver(0, this.caseActu, this.orient, "" );
    }

	// Initialisation du graphe de recherche.
    private void init() {
        for(Case gpCase : gpia.keySet()) {
            if(gpCase.equals(caseActu))
                graphe.put(gpCase, 0);
            else
                graphe.put(gpCase, 123456);//123456 = +infini
        }
    }
    
    //Algorithme de recherche.
 
    private void trouver(int tailleChemin, Case caseAct, Orientation orientT, String instructions) {	
    	Case next = p.getNextCase(caseAct, orientT);
        Case nextUturn = null;
        if(caseAct.isCase2())
            nextUturn = p.getNextCase(caseAct, Orientation.UTurn(orientT, caseAct.getTypeImage()));
        if(caseAct.equals(caseDest)) {
            if(pileMin == 0) {
                instructionsList = instructions;
                pileMin = tailleChemin;
            }
            else if(tailleChemin<pileMin) {
            	instructionsList = instructions;
            	pileMin = tailleChemin;
            }
            return;	
        }
        else {
            if(next.isCase2()) {
                if(tailleChemin+1<graphe.get(next)) {
                    graphe.put(next, tailleChemin+1);
                    trouver(tailleChemin+1, next, Orientation.nouvOrientApresMouv(orientT, "s", next.getTypeImage()), instructions+"s\n");
                }

            }
            else if(!next.isCase2()) {
                if(tailleChemin+1<graphe.get(next)) {
                    graphe.put(next, tailleChemin+1);
                    trouver(tailleChemin+1, next, Orientation.nouvOrientApresMouv(orientT, "l", next.getTypeImage()), instructions+"l\n");
                    trouver(tailleChemin+1, next, Orientation.nouvOrientApresMouv(orientT, "r", next.getTypeImage()), instructions+"r\n");
                }

            }
            
            if(nextUturn!=null) {
                if(nextUturn.isCase2()) {
                    if(tailleChemin+2<graphe.get(nextUturn)) {
                        graphe.put(nextUturn, tailleChemin+2);
                        trouver(tailleChemin+2, nextUturn, Orientation.nouvOrientApresMouv(Orientation.UTurn(orientT, caseAct.getTypeImage()), "s", nextUturn.getTypeImage()), instructions+"u\ns\n");
                    }

                }
                else if(!nextUturn.isCase2()) {
                    if(tailleChemin+2<graphe.get(nextUturn)) {
                        graphe.put(nextUturn, tailleChemin+2);
                        trouver(tailleChemin+2, nextUturn, Orientation.nouvOrientApresMouv(Orientation.UTurn(orientT, caseAct.getTypeImage()), "l", nextUturn.getTypeImage()), instructions+"u\nl\n");
                        trouver(tailleChemin+2, nextUturn, Orientation.nouvOrientApresMouv(Orientation.UTurn(orientT, caseAct.getTypeImage()), "r", nextUturn.getTypeImage()), instructions+"u\nr\n");
                    }

                }
            }
            return;
        }

        
    }

    /**
     * @return
     * Renvoie la suite d'instructions, séparées par une retour chariot.
     */
    public String getInstructionsList() {
        return instructionsList;
    }
    
    /**
     * @return Renvoie la suite d'instructions, sous forme de String.
     */
    public int getPileMin() {
		return pileMin;
	}

    
}





