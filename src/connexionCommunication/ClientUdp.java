package connexionCommunication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUdp implements Runnable {
    private String hostname= "localhost";
    private int port=23647;
    private InetAddress host;
    private DatagramSocket socket;
    private String message;
    DatagramPacket packet;
    
    public ClientUdp(String mess) {
    	message = mess;
    	System.out.println("Ici");
    }
    
	@Override
	public void run() {
        try
        {
        	System.out.println("Dans run");
            host = InetAddress.getByName(hostname);
			//socket = new DatagramSocket(DEFAULT_PORT);
			socket = new DatagramSocket(null);
            packet = new DatagramPacket (message.getBytes(),message.getBytes().length,host,port);
            socket.send (packet);
            System.out.println("Paquet Envoyé ");
        }
        catch(Exception e)
        {
        	System.out.println("echec");
            e.printStackTrace();
        }

	}

}
