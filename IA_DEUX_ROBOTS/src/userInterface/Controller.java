package userInterface;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import robot.Robot;

public class Controller extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4521070167771966011L;
	Logs log;
	JButton up;
	JButton down;
	JButton left;
	JButton right;
	JButton pick;
	JButton drop;
	JButton IA;
	JLabel control;
	Robot robotTelecommande;
	Robot robotAutonome;
	
	
	
	public Controller(Logs log) {
		super();
		this.log = log;
		
		setLayout(new GridLayout(5,3));
		setMaximumSize(new Dimension(150,200));
		up = new JButton("",new ImageIcon("./imagesDirections/up.png"));
		down = new JButton("",new ImageIcon("./imagesDirections/down.png"));
		left = new JButton("",new ImageIcon("./imagesDirections/left.png"));
		right = new JButton("",new ImageIcon("./imagesDirections/right.png"));
		pick = new JButton("",new ImageIcon("./imagesDirections/pick.png"));
		drop = new JButton("",new ImageIcon("./imagesDirections/drop.png"));
		IA = new JButton("IA");
		control = new JLabel("Controlle :");
		
		up.setPreferredSize(new Dimension(50,50));
		right.setPreferredSize(new Dimension(50,50));
		left.setPreferredSize(new Dimension(50,50));
		down.setPreferredSize(new Dimension(50,50));
		control.setPreferredSize(new Dimension(50,50));
		pick.setPreferredSize(new Dimension(50,50));
		drop.setPreferredSize(new Dimension(50,50));
		IA.setPreferredSize(new Dimension(50,50));
		
		up.setActionCommand("up");
		right.setActionCommand("right");
		down.setActionCommand("down");
		left.setActionCommand("left");
		pick.setActionCommand("pick");
		drop.setActionCommand("drop");
		IA.setActionCommand("ia");
		
		up.addActionListener(this);
		down.addActionListener(this);
		left.addActionListener(this);
		right.addActionListener(this);
		pick.addActionListener(this);
		drop.addActionListener(this);
		IA.addActionListener(this);
		
		add(Box.createRigidArea(new Dimension(50,50)));
		add(up);
		add(Box.createRigidArea(new Dimension(50,50)));
		
		add(left);
		add(Box.createRigidArea(new Dimension(50,50)));
		add(right);
		
		add(Box.createRigidArea(new Dimension(50,50)));
		add(down);
		add(Box.createRigidArea(new Dimension(50,50)));
		
		add(Box.createRigidArea(new Dimension(50,50)));
		add(Box.createRigidArea(new Dimension(50,50)));
		add(Box.createRigidArea(new Dimension(50,50)));
		
		add(pick);
		add(IA);
		add(drop);
		
		setVisible(true);
	}
	
	public void setRobotTelecommande(Robot r) {
		this.robotTelecommande = r;
	}
	
	public void setRobotAutonome(Robot r) {
		this.robotAutonome = r;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "up":
				log.addEvent("User wants to go STRAIGHT");
				robotTelecommande.traiterCommande("s");
				break;
			case "down" :
				log.addEvent("User wants to go UTURN");
				robotTelecommande.traiterCommande("u");
				break;
			case "left":
				log.addEvent("User wants to go LEFT");
				robotTelecommande.traiterCommande("l");
				break;
			case "right" :
				log.addEvent("User wants to go RIGHT");
				robotTelecommande.traiterCommande("r");
				break;
			case "pick":
				log.addEvent("User wants to PICK a patient");
				robotTelecommande.pick();
				break;
			case "drop":
				log.addEvent("User wants to DROP a patient");
				robotTelecommande.drop();
				break;
			case "ia" :
				log.addEvent("Yehaw");
				robotAutonome.ia();
			default:
				;
			
		}
		
	}
}
