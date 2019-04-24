package robot;

import java.util.List;
import java.util.concurrent.TimeUnit;

import connexionCommunication.Robot_bt;
import ia.IA;
import plateau.Case;
import plateau.Plateau;
import userInterface.Carte;
import userInterface.Logs;

/**
 * 
 * @author JACQUES Rémi
 *
 *Classe représentant un robot de façon abstraite
 */


public class Robot {
	private Plateau plat;
	private Carte carte;
	private String nom;
	private Logs log;
	private Robot_bt robBluetooth;
	private Case position;
	private Orientation direction;
	private int nbVictime;
	
	/**
	 * @param nom : Nom du robot
	 * @param depart : Case de départ du robot
	 * @param dir : Direction de départ du robot
	 * @param plat : Plateau contenant le robot
	 * @param crt : Carte liée au plateau contenant le robot
	 * @param log : Logs
	 * @param robt : Robot bluetooth lié à ce robot abstrait
	 */
	public Robot(String nom,Case depart,Orientation dir,Plateau plat,Carte crt,Logs log,Robot_bt robt) {
		nbVictime = 0;
		position = depart;
		direction = dir;
		this.nom = nom;
		this.plat = plat;
		this.carte = crt;
		this.log = log;
		this.robBluetooth = robt;
	}

	@Override
	public String toString() {
		return "Robot [position=" + position.toStringSimpl() + ", direction=" + direction + ", nom=" + nom + "]";
	}

	
	/**
	 * Traite une commande et l'envoi ou non au robot si elle est possible.
	 * @param command : commande à traiter.
	 */
	public void traiterCommande(String command) {
		Case dest = plat.getNextCase(position,direction);
		
		log.addEvent(toString());
		log.addEvent("Mouv = "+command);
		if(command.equals("u")) {
			traiterUturn();
		}
		else if(dest != null && !(!dest.isCase2() && command.equals("s"))) {
			traiterMouv(command,dest);
		}
		else {
			log.addEvent("Mouvement impossible!!\n"+command);
		}
		log.addEvent(toString());
	}
	
	
	
	/**
	 * Traite une liste de commandes dans l'ordre.
	 * @param commandes : tableau de commandes.
	 */
	public void traiterMultiCommandes(String[] commandes) {
		for(String s: commandes) {
			traiterCommande(s);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Traite uniquement la commande uturn.
	 */
	private void traiterUturn() {
		Orientation temp;
		String typeImActuel = position.getTypeImage();
		
		if(!(position.getTypeImage().length() ==3 )) {
			log.addEvent("U turning");
			temp = Orientation.UTurn(direction,typeImActuel);
			robBluetooth.setMessage("u");
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			robBluetooth.setMessage("s");
			direction = temp;
			position.setRobot("R"+direction.toString());
			carte.updateCarte(plat);
		}
		else {
			log.addEvent("Pas de Uturn sur une case à trois branches !!!");
		}
	}
	
	/**
	 * Traite tout mouvement qui n'est pas un uturn.
	 * @param command : commande a traiter.
	 * @param caseDest : case destination dans laquelle s'effectue le mouvement.
	 */
	private void traiterMouv(String command,Case caseDest) {
		Orientation oTemp = Orientation.nouvOrientApresMouv(direction,command,caseDest.getTypeImage());
		List<String> typeCasesPossibles = Orientation.typeCasesPourOrient(oTemp);
		
		if(typeCasesPossibles.contains(caseDest.getTypeImage())){	
			log.addEvent("Moving from "+position.toStringSimpl()+" to "+caseDest.toStringSimpl() +" command =" + command);
			direction = oTemp;
			position.setRobot("");
			caseDest.setRobot("R"+direction.toString());
			position = caseDest;
			envoyerCommande(command);
			carte.updateCarte(plat);
		}
		else {
			log.addEvent("C'est une impasse vous ne pouvez pas aller par la !");
		}
	}
	
	
	/**
	 * On envoie une commande au robot via Bluetooth, a ce stade, on est sur que la commande peut être effectuée sans erreur.
	 * @param commande: commande à envoyer au robot.
	 */
	private void envoyerCommande(String commande) {
		if(robBluetooth != null)
			robBluetooth.setMessage(commande);
		else
			log.addEvent("Robot bluetooth non connecté à cette instance !!!");
	}
	
	/**
	 * Envoi au robot la command "p" : prendre un patient. Fonctionne seulement si l'on est sur une case patient.
	 */
	public void pick() {
		Case c = position;
		if(c.hasPatient()) {
			nbVictime ++;
			c.prendrePatient();
			log.addEvent("Patient récupéré sur la case :"+c.toStringSimpl());
			carte.updateCarte(plat);
			robBluetooth.setMessage("t\n");
		}
		else
			log.addEvent("Pas de patients sur la case");
	}
	
	
	/**
	 * Envoi au robot la command "d" : déposer un patient. Fonctionne seulement si l'on est sur une case hopital.
	 */
	public void drop() {
		Case c = position;
		if(c.hasHopital() && nbVictime > 0) {
			
			log.addEvent("Patient déposé à l'hopital");
			robBluetooth.setMessage("d\n");
			plat.patientSauve(nbVictime);
			log.addEvent("Il reste "+plat.getNbPatients()+" à sauver !");
			nbVictime = 0;
		}
		else
			log.addEvent("Pas d'hopitaux sur la case ou pas de patient dans l'ambulance");
	}

	/**
	 * Lance l'IA du robot qui s'arrête une fois tous les patients récupérés.
	 */
	public void ia() {
		IA intellArt = new IA(this.plat, this);
		
		intellArt.faireDesChoses();
	}
	
	public Case getPosition() {
		return position;
	}

	public Orientation getDirection() {
		return direction;
	}

	public String getNom() {
		return nom;
	}

	public int getNbVictime() {
		return nbVictime;
	}

}


