package connexionCommunication;

import java.io.DataOutputStream;
import java.io.IOException;

import robot.Robot;

public class Emission implements Runnable {
	DataOutputStream output;
	Robot rob;
	String message;
	
	public Emission(DataOutputStream dis,String message) {
		this.output = dis;
		this.message = message;
	}
	@Override
	public void run() {
		try {
			output.writeBytes(message+"\n");
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

}
