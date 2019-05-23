package connexionCommunication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

import plateau.Case;
import plateau.Plateau;
import robot.Orientation;
import robot.Robot;
import userInterface.Carte;
import userInterface.Logs;
//TODO: AJOUTER PRENDRE ET DEPOSER
public class ServerUdp implements Runnable{
    public static final int DEFAULT_PORT = 23647;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private byte[] buff;
    private Plateau plat;
    private Carte crt;
    private Logs log;
    private List<Integer> listeIds;

	
	public ServerUdp(Plateau plat,Carte crt,Logs log) {
		this.plat = plat;
		this.crt = crt;
		this.log = log;
		this.listeIds = new ArrayList<>();
	}
	
	public void run() {
		String infos;
		Robot  rob;
		int idRob;
		Case temp;
		buff = new byte[255];
		try {
			socket = new DatagramSocket(DEFAULT_PORT);
		
		}
		catch(Exception e){
			System.out.println("Impossible de créer socket");
		}
		
		packet = new DatagramPacket(buff,255);
		
		while(true) {
			try {
				socket.receive(packet);
				log.addEvent("Message recu = "+(new String(packet.getData())));
				infos = new String(packet.getData());
				idRob = Integer.parseInt(infos.split("\\.")[1]);
				if(listeIds.contains(idRob)) {
					String[] data = infos.split("\\.");
					int posL = Integer.parseInt(data[2]);
					int posC = Integer.parseInt(data[3]);
					String mouvement = data[4];
					String orientation = data[5];
					
					rob = plat.getRobById(idRob);
					if(mouvement.contains("non")) {
						temp = plat.getCaseByCoordinates(posL, posC);
						System.out.println("ayayaya"+rob.toString());
						rob.getPosition().setRobot("");
						rob.setPosition(temp);
						rob.setDirection(Orientation.stringToOrient(orientation));
						temp.setRobot("R"+rob.getDirection().toString());
					}
						
				}
				else {
					rob = LogRobot.construireRobotDepuisMessage(infos, plat, crt, log);
					rob.getPosition().setRobot("R"+rob.getDirection().toString());
					listeIds.add(Integer.parseInt(infos.split("\\.")[1]));
					plat.ajouterRobot(rob,true);
				}
				crt.updateCarte(plat);
				
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("Probleme reception des donnees");
			}
			System.out.println("Attente");
		}
		
	}
}
