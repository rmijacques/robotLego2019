package connexionCommunication;

import java.util.Date;

import plateau.Plateau;
import robot.Orientation;
import robot.Robot;
import userInterface.Carte;
import userInterface.Logs;

public class LogRobot {
	String message;
	
	
	public static String construireMessageLog(Robot r,String mouv) {
		Date date = new java.util.Date();
		int idRob = r.getId();
		int posL = r.getPosition().getX();
		int posC = r.getPosition().getY();
		String mouvement = "mouv="+mouv;

		
		return "date"+"."+idRob+"."+posL+"."+posC+"."+mouvement+"."+r.getDirection();
	}
	
	public static Robot construireRobotDepuisMessage(String message,Plateau plat,Carte crt,Logs log) {
		System.out.println("Message : " +message);
		String[] infos = message.split("\\.");
		for(String s:infos) {
			System.out.println("inf "+s);
		}
		int idRob = Integer.parseInt(infos[1]);
		int posL = Integer.parseInt(infos[2]);
		int posC = Integer.parseInt(infos[3]);
		String mouvement = infos[4];
		String orientation = infos[5];
		System.out.println(infos[5]);
		Robot rob = new Robot("dist_"+idRob,plat.getCaseByCoordinates(posL, posC),Orientation.stringToOrient(orientation), plat, crt, log, null,true);
		
		return rob;
	}
	
}
