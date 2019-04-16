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
	JLabel control;
	Robot rob;
	
	
	
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
		control = new JLabel("Controlle :");
		
		up.setPreferredSize(new Dimension(50,50));
		right.setPreferredSize(new Dimension(50,50));
		left.setPreferredSize(new Dimension(50,50));
		down.setPreferredSize(new Dimension(50,50));
		control.setPreferredSize(new Dimension(50,50));
		pick.setPreferredSize(new Dimension(50,50));
		drop.setPreferredSize(new Dimension(50,50));
		
		up.setActionCommand("up");
		right.setActionCommand("right");
		down.setActionCommand("down");
		left.setActionCommand("left");
		pick.setActionCommand("pick");
		drop.setActionCommand("drop");
		
		up.addActionListener(this);
		down.addActionListener(this);
		left.addActionListener(this);
		right.addActionListener(this);
		pick.addActionListener(this);
		drop.addActionListener(this);
		
		
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
		add(Box.createRigidArea(new Dimension(50,50)));
		add(drop);
		
		setVisible(true);
	}
	
	public void setRobot(Robot r) {
		this.rob = r;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "up":
				System.out.println("up");
				log.addEvent("User wants to go STRAIGHT");
				rob.bouger("s");
				break;
			case "down" :
				log.addEvent("User wants to go UTURN");
				rob.bouger("u");
				break;
			case "left":
				log.addEvent("User wants to go LEFT");
				rob.bouger("l");
				break;
			case "right" :
				log.addEvent("User wants to go RIGHT");
				rob.bouger("r");
				break;
			case "pick":
				log.addEvent("User wants to PICK a patient");
				rob.pick();
				break;
			case "drop":
				log.addEvent("User wants to DROP a patient");
				rob.drop();
				break;
			default:
				;
			
		}
		
	}
}
