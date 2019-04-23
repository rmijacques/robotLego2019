package connexionCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;



public class Robot_bt {
	private Connexion cn;
	private String addr;
	private String name;
	protected DataInputStream DataIn;
	protected DataOutputStream DataOut;
	public ConnectorCommunicator test;
	public Listener ln;
	protected boolean objectif;
	protected boolean lock;
	protected String message;
	protected String out;
	
	public Robot_bt (String addr, String name){
		this.addr = addr;
		this.name = name;
		this.lock = false;
		this.message = "";
		this.objectif = false;
		
		this.test = new ConnectorCommunicator();
//		this.ln = new Listener();
		test.start();
//		ln.start();
	}
	
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
	
	public class ConnectorCommunicator extends Thread{
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
	
	public String getOut() {
		String temp = out;
		out = "";
		return temp;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
