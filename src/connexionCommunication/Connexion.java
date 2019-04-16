package connexionCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTInfo;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

public class Connexion {
	private NXTConnector com;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private NXTInfo robot; 
	
	public Connexion (String addr, String name) throws NXTCommException{
		com = new NXTConnector();
		robot = new NXTInfo(NXTCommFactory.BLUETOOTH, name, addr);
		
		com.addLogListener(new NXTCommLogListener(){
			public void logEvent(String message) {
				System.out.println("BTSend Log.listener: "+message);
			}

			public void logEvent(Throwable throwable) {
				System.out.println("BTSend Log.listener - stack trace: ");
				 throwable.printStackTrace();
			}
		} );
		
//		try {
		if(!com.connectTo("btspp://"+addr))
			System.err.println("Erreur de connection");
		else {
			robot= com.getNXTInfo();
			System.out.println("Bluetooth connecté à "+robot.deviceAddress);
			dataIn = new DataInputStream(com.getInputStream());
			dataOut = new DataOutputStream(com.getOutputStream());
			}
		}
	
	
	public DataInputStream getDataIn() {
		return dataIn;
	}
	public DataOutputStream getDataOut() {
		return dataOut;
	}
	
	public void close () {
		try {
			dataIn.close();
			dataOut.close();
			com.close();
		}
		catch (IOException e) {}
	}
}


