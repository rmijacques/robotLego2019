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

	public void run() {
		String message;
		while(true) {
			try {
				message = String.valueOf(input.readChar());
				if(!message.equals("0")) {
					System.out.println("Envoyé par rob : "+message);
					if(rob!=null) {
						System.out.println("sent to bot");
						rob.setMessage(message);
					}
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	public void setRobot(Robot r) {
		this.rob = r;
	}

}
