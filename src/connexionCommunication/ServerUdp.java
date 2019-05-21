package connexionCommunication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import plateau.Plateau;
import robot.Robot;
import userInterface.Carte;
import userInterface.Logs;

public class ServerUdp implements Runnable{
    public static final int DEFAULT_PORT = 23647;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private byte[] buff;
    private Plateau plat;
    private Carte crt;
    private Logs log;

	
	public ServerUdp(Plateau plat,Carte crt,Logs log) {
		this.plat = plat;
		this.crt = crt;
		this.log = log;
	}
	
	public void run() {
		String infos;
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
				System.out.println("Recu : "+new String(packet.getData())+" sur le port "+packet.getPort());
				infos = new String(packet.getData());
				Robot rob = LogRobot.construireRobotDepuisMessage(infos, plat, crt, log);
				rob.getPosition().setRobot("R"+rob.getDirection().toString());
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
