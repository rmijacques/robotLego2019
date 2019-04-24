package connexionCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Pierre Gavrilov
 *
 */
public class Robot_bt {
	private Connexion cn;
	private String addr;
	private String name;
	private DataInputStream DataIn;
	private DataOutputStream DataOut;
	private ConnectorCommunicator con;
	private boolean objectif;
	private boolean lock;
	private String message;
	private String out;
	
	/**
	 * @param addr L'adresse du robot auquel on souhaite se connecter.
	 * @param name Le nom du robot auquel on souhaite se connecter.
	 */
	public Robot_bt (String addr, String name){
		this.addr = addr;
		this.name = name;
		this.lock = false;
		this.message = "";
		this.objectif = false;
		
		this.con = new ConnectorCommunicator();
		con.start();
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
	 * Cette classe interne permet de communiquer avec le robot.
	 */
	public class ConnectorCommunicator extends Thread{
		/* Le thread pricipal pour la communication.
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			try {
				cn = new Connexion(addr, name);
				DataIn = cn.getDataIn();
				DataOut = cn.getDataOut();
				lock = true;
//				DataOut.flush();
				while(!objectif) {
					listen();
					write();
				}
			}
			catch (Exception e) {
				System.err.println(e);
			}
		}
		
		private void listen() throws IOException{

			out="";
				try {

					if(DataIn.available()!=0) {
						System.out.println(DataIn.readByte());
						System.out.println("je suis rentre");
					}
				} catch (IOException e) {
					System.out.println("Erreur de lecture.\n");;
				}
		}
		
		private void write() {
			if(dataToWrite())
				try {
					DataOut.writeBytes(message+"\n");
					DataOut.flush();
					message = "";
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		public void close() {
			cn.close();
		}
		private boolean dataToWrite() {
			return message!="";
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
		this.message = message;
	}
	
	
}
