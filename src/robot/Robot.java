package robot;

import java.util.List;

import connexionCommunication.Robot_bt;
import plateau.Case;
import plateau.Plateau;
import userInterface.Carte;
import userInterface.Logs;

public class Robot {
	Case position;
	Orientation direction;
	int nbVictime;
	Plateau plat;
	Carte crt;
	String nom;
	Logs log;
	Robot_bt robt;
	
	public Robot(String nom,Case depart,Orientation dir,Plateau plat,Carte crt,Logs log,Robot_bt robt) {
		nbVictime = 0;
		position = depart;
		direction = dir;
		this.nom = nom;
		this.plat = plat;
		this.crt = crt;
		this.log = log;
		this.robt = robt;
	}

	@Override
	public String toString() {
		return "Robot [position=" + position + ", direction=" + direction + ", nom=" + nom + "]";
	}
	
	//straight à changer
	
	public void bouger(String command) {
		Case dest = plat.getCaseSuivanteWithCommandAndOrientation(position,command,direction);
		List<String> orientPoss;
		Orientation temp;
		String typeImActuel = position.getTypeImage().split("R")[0];
		log.addEvent(toString());
		
		if(dest != null) {
			log.addEvent("Moving...");
			
			//On calcule la nouvelle orientation après le mouvement
			temp = Orientation.nouvOrientApresMouv(direction,command,typeImActuel);
			
			//On vérifie que la nouvelle orientation est possible dans la case destination
			orientPoss = Orientation.typeCasesPourOrient(temp);
			
			System.out.println(temp+" "+orientPoss+ dest.getTypeImage());
			
			if(orientPoss.contains(dest.getTypeImage())){	
				robt.setMessage(command);
				direction = temp;
				position.setTypeImage(position.getTypeImage().split("R")[0]);
				dest.setTypeImage(dest.getTypeImage()+"R"+direction.toString());
				position = dest;
				crt.updateCarte(plat);
			}
			else {
				log.addEvent("C'est une impasse vous ne pouvez pas aller par la !");
			}
		}
		else {
			log.addEvent("Mouvement impossible!!\nSi vous etes dans une configuration à 3 branche rappel que pour aller tout droit, il faut aller vers le côté sans branche!");
		}
		log.addEvent(toString());
	}
	
	
	
	
	public void pick() {
		Case c = position;
		if(c.hasPatient()) {
			nbVictime ++;
			c.setPatient("");
			log.addEvent("Patient récupéré sur la case :"+c.toString());
			crt.updateCarte(plat);
			robt.setMessage("t\n");
		}
		else
			log.addEvent("Pas de patients sur la case");
	}
	
	public void drop() {
		Case c = position;
		if(c.hasHopital() && nbVictime > 0) {
			
			log.addEvent("Patient déposé à l'hopital");
			crt.updateCarte(plat);
			robt.setMessage("d\n");
			plat.patientSauve(nbVictime);
			log.addEvent("Il reste "+plat.getNbPatients()+" à sauver !");
			nbVictime = 0;
		}
		else
			log.addEvent("Pas d'hopitaux sur la case ou pas de patient dans l'ambulance");
	}
}
