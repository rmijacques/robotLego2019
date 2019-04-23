package robot;

import java.util.List;
import java.util.concurrent.TimeUnit;

import connexionCommunication.Robot_bt;
import ia.IA;
import plateau.Case;
import plateau.Plateau;
import userInterface.Carte;
import userInterface.Logs;

public class Robot {
	public Case getPosition() {
		return position;
	}

	public void setPosition(Case position) {
		this.position = position;
	}

	public Orientation getDirection() {
		return direction;
	}

	public void setDirection(Orientation direction) {
		this.direction = direction;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	Case position;
	Orientation direction;
	int nbVictime;
	public int getNbVictime() {
		return nbVictime;
	}

	public void setNbVictime(int nbVictime) {
		this.nbVictime = nbVictime;
	}

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
		return "Robot [position=" + position.toStringSimpl() + ", direction=" + direction + ", nom=" + nom + "]";
	}
	
	
	public void bouger(String command) {
		Case dest = plat.getNextCase(position,direction);
		List<String> orientPoss;
		Orientation temp;
		String typeImActuel = position.getTypeImage();
		log.addEvent(toString());
		
		if(command.equals("u")) {
			if(position.getTypeImage().length() ==3 ) {
				log.addEvent("Pas de Uturn sur une case à trois branches !!!");
				return;
			}
			log.addEvent("U turning");
			temp = Orientation.UTurn(direction,typeImActuel);
			robt.setMessage(command);
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			robt.setMessage("s");
			direction = temp;
			position.setRobot("R"+direction.toString());
			
			crt.updateCarte(plat);
		}
		else if(dest != null && !(!dest.isCase2() && command.equals("s"))) {
			log.addEvent("Moving from "+position.toStringSimpl()+" to "+dest.toStringSimpl() +" command =" + command);

			temp = Orientation.nouvOrientApresMouv(direction,command,dest.getTypeImage());
			orientPoss = Orientation.typeCasesPourOrient(temp);

			if(orientPoss.contains(dest.getTypeImage())){	
				robt.setMessage(command);
				direction = temp;
				position.setRobot("");
				dest.setRobot("R"+direction.toString());
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

	public void ia() {
		IA intellArt = new IA(this.plat, this);
	}
	
}
