package connexionCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.pc.comm.NXTCommException;
import robot.Robot;

/**
 * @author Pierre Gavrilov
 *
 */
public class Robot_bt {
	private Connexion cn;
	private DataInputStream DataIn;
	private DataOutputStream DataOut;
	private boolean objectif;
	private boolean lock;
	private String out;
	
	
	public DataOutputStream getDataOut() {
		return DataOut;
	}
	
	/**
	 * @param addr L'adresse du robot auquel on souhaite se connecter.
	 * @param name Le nom du robot auquel on souhaite se connecter.
	 */
	public Robot_bt (String addr, String name){
		this.lock = false;
		this.objectif = false;

		try {
			cn = new Connexion(addr, name);
		} catch (NXTCommException e) {
			e.printStackTrace();
		}
		DataIn = cn.getDataIn();
		DataOut = cn.getDataOut();
	}
	
	/**
	 * Cette classe interne permet de recevoir les messages du robot.
	 * Cependant elle ne reçoit rien !
	 * @deprecated voir {@link ConnectorCommunicator}
	 */
	public class Listener extends Thread {
		
		public void run() {
			while (!objectif) {
				out = "";
				try {
					if(lock)
						out = DataIn.readUTF();
				} catch (Exception e) {
					System.out.println("Erreur de lecture.\n");
				}
			}
		}
	}
	
	/**
	 * @return Renvoie le message écrit par le robot.
	 * Ne marche pas, car on ne reçoit rien de la part du robot.
	 */
	public String getOut() {
		String temp = out;
		out = "";
		return temp;
	}
	

	/**
	 * @param message Le message que l'on souhaite envoyer.
	 * Il suffit d'utiliser cette méthode pour envoyer un message.
	 */
	public void setMessage(String message) {
		new Thread(new Emission(DataOut,message)).start();
	}
	
	public void setRobotReception(Robot r) {
		cn.setRobotRecepetion(r);
	}
	
	
}
