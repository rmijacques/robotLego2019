package connexionCommunication;
import java.io.DataInputStream;
import java.io.IOException;

import robot.Robot;

public class Reception implements Runnable {
	DataInputStream input;
	Robot rob;
	
	public Reception(Robot r, DataInputStream in) {
		this.rob = r;
		this.input = in;
	}
	//TODO: Ameiliorer le caca ici présent qui marche ap
	public void run() {
		String message;
		while(true) {
			try {
				while(rob == null || rob.getMessage()!=""); 
					message = String.valueOf(input.readChar());
					if(!message.equals("0"))
						rob.setMessage(message);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setRobot(Robot r) {
		this.rob = r;
	}

}
