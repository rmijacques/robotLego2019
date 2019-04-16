package connexionCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


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
			byte[] data;
			int length;
			out="";
				try {
//					length = DataIn.readByte() +  DataIn.readByte();
//					System.out.println(length);
//					data = new byte [length];
					if(DataIn.available()!=0) {
						System.out.println(DataIn.readByte());
						System.out.println("je suis rentre");
					}
//					DataIn.read(data);
//					out = new String(data);
				} catch (IOException e) {
					System.out.println("Erreur de lecture.\n");;
				}
		}
		
		private void write() {
			if(dataToWrite())
				try {
//					DataOut.writeInt(message.length());
//					DataOut.writeInt(0);
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
		
		private boolean dataToRead() throws IOException{
			return DataIn.available()>0;
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
