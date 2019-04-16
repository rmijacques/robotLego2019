package userInterface;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import connexionCommunication.Robot_bt;
import plateau.Plateau;
import robot.Robot;

public class GestionRobots extends JPanel implements ActionListener{
	Logs log;
	JButton boutonCreerRobot;
	JScrollPane scrollPaneConteneurLogs;
	Plateau plateau;
	Carte carte;
	Robot robot;
	Controller controller;
	Robot_bt robt;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2322333442101325893L;

	public GestionRobots(Logs log,Controller controller,Plateau plat,Carte carte,Robot_bt robt) {
		this.carte = carte;
		this.plateau = plat;
		this.log = log;
		this.robt = robt;
		this.controller = controller;
		scrollPaneConteneurLogs = new JScrollPane(log);
		scrollPaneConteneurLogs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneConteneurLogs.setPreferredSize(new Dimension(600,700));
		
		boutonCreerRobot = new JButton("Ajouter un robot");
		boutonCreerRobot.setActionCommand("nouvRob");
		boutonCreerRobot.addActionListener(this);
		
		this.setMinimumSize(new Dimension(700,700));
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		add(Box.createVerticalStrut(10));
		add(boutonCreerRobot);
		add(Box.createVerticalStrut(10));
		add(scrollPaneConteneurLogs);
		this.setVisible(true);
	}
	
	public Logs getLog() {
		return log;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		if(com.equals("nouvRob"))
			add(new CreationRobot(plateau,log,carte,this,robt));
	}
	
	public void setRob(Robot r) {
		this.robot = r;
	}
}
