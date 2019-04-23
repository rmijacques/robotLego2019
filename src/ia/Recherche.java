package ia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plateau.*;
import robot.Orientation;

public class Recherche {
    
    
    private Case caseActu;
    private Case caseDest;
    private Map<Case, Integer> graphe;
    private Map<Case, List<Case>> gpia;
    private Orientation orient;
    private String instructionsList;
    private int pileMin;
    private Plateau p;
    
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
        trouver(0, this.caseActu, this.orient, "");
    }
    // Initialisation du graphe de recherche.
    public void init() {
        for(Case gpCase : gpia.keySet()) {
            if(gpCase.equals(caseActu))
                graphe.put(gpCase, 0);
            else
                graphe.put(gpCase, 123456);//123456 = +infini
        }
    }
    
    //Algorithme de recherche.
    public void trouver(int tailleChemin, Case caseAct, Orientation orientT, String instructions) {
        Case next = p.getNextCase(caseAct, orientT);
        Case nextUturn = null;
        if(caseAct.isCase2())
            nextUturn = p.getNextCase(caseAct, Orientation.UTurn(orientT, caseAct.getTypeImage()));
        if(caseAct.equals(caseDest)) {
            if(tailleChemin<pileMin || pileMin == 0) {
                instructionsList = instructions;
                System.out.println("trouvé");
            }
        }
        else {
            if(next.isCase2())
                if(tailleChemin+1<graphe.get(next)) {
                    graphe.put(next, tailleChemin+1);
                    trouver(tailleChemin+1, next, Orientation.nouvOrientApresMouv(orientT, "s", next.getTypeImage()), instructions+"s\n");
                }
                else
                    return;
            else if(!next.isCase2()) {
                if(tailleChemin+1<graphe.get(next)) {
                    graphe.put(next, tailleChemin+1);
                    trouver(tailleChemin+1, next, Orientation.nouvOrientApresMouv(orientT, "l", next.getTypeImage()), instructions+"l\n");
                    trouver(tailleChemin+1, next, Orientation.nouvOrientApresMouv(orientT, "r", next.getTypeImage()), instructions+"r\n");
                }
                
                else
                    return;
            }
            if(nextUturn!=null) {
                if(nextUturn.isCase2())
                    if(tailleChemin+2<graphe.get(nextUturn)) {
                        graphe.put(nextUturn, tailleChemin+2);
                        trouver(tailleChemin+2, nextUturn, Orientation.nouvOrientApresMouv(Orientation.UTurn(orientT, caseAct.getTypeImage()), "s", nextUturn.getTypeImage()), instructions+"u\ns\n");
                    }
                    else
                        return;
                else if(!nextUturn.isCase2()) {
                    if(tailleChemin+2<graphe.get(nextUturn)) {
                        graphe.put(nextUturn, tailleChemin+2);
                        trouver(tailleChemin+2, nextUturn, Orientation.nouvOrientApresMouv(Orientation.UTurn(orientT, caseAct.getTypeImage()), "l", nextUturn.getTypeImage()), instructions+"u\nl\n");
                        trouver(tailleChemin+2, nextUturn, Orientation.nouvOrientApresMouv(Orientation.UTurn(orientT, caseAct.getTypeImage()), "r", nextUturn.getTypeImage()), instructions+"u\nr\n");
                    }
                    
                    else
                        return;
                }
            }
        }

        
    }

    public String getInstructionsList() {
        return instructionsList;
    }
    
}





