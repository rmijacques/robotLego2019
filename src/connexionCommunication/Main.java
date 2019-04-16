package connexionCommunication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Robot_bt rob = new Robot_bt("00:16:53:1C:15:FC", "Glaedr");
		TimeUnit.SECONDS.sleep(5);
		System.out.println(rob.getOut());
		rob.setMessage("colloc");
//		rob.DataOut.writeBytes("Controlleur_robot\n");
//		System.out.println(rob.message);

		System.out.println(rob.getOut());
		TimeUnit.SECONDS.sleep(10);
		rob.setMessage("s\nt\ns\ns\nd\n");
		System.out.println("message envoye");
//		TimeUnit.SECONDS.sleep(5);
		//rob.test.close();
	}

}
