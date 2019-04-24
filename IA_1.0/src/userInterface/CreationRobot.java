package userInterface;

	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connexionCommunication.Robot_bt;
import plateau.Case;
import plateau.Plateau;
import robot.Orientation;
import robot.Robot;

public class CreationRobot extends JInternalFrame implements ActionListener{
	JPanel jPanelConteneurCrea;
	JTextField jTFAddresseBluetooth;
	JTextField jTFNomRobot;

	JTextField jTFXDepart;
	JTextField jTFYDepart;
	JComboBox<robot.Orientation> jCBOrientation;
	JButton jBAjouterRobot;
	JButton jBAjouterRobotTelecommande;

	Logs log;
	Plateau plateau;
	Carte carte;
	Controller controller;
	GestionRobots gestionRobots;
	Robot_bt robt;

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7039655929210412344L;

	public CreationRobot(Plateau plat,Logs log,Carte carte,GestionRobots gr,Robot_bt robt) {
		super("Nouveau Robot");
		Orientation []ori = {Orientation.EAST,Orientation.WEST,Orientation.NORTH,Orientation.SOUTH};
		JLabel jLBNom = new JLabel("Nom du robot :");
		JLabel jLBX = new JLabel("Coordonnée Y du robot :");
		JLabel jLBY = new JLabel("Coordonnée X du robot :");
	
		this.log = log;
		this.carte = carte;
		this.gestionRobots = gr;
		this.plateau = plat;
		this.controller = gr.controller;
		this.robt = robt;
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));

		jPanelConteneurCrea = new JPanel();
		jPanelConteneurCrea.setLayout(new BoxLayout(jPanelConteneurCrea, BoxLayout.Y_AXIS));
		
		jTFXDepart = new JTextField();
		jTFYDepart = new JTextField();
		jCBOrientation = new JComboBox<>(ori);
		jTFAddresseBluetooth = new JTextField();
		jTFNomRobot = new JTextField();

		jTFNomRobot.setText("Glaedr");
		
		
		jBAjouterRobot = new JButton("ajouter robot bluetooth");
		jBAjouterRobot.addActionListener(this);
		jBAjouterRobot.setActionCommand("robBlue");
		
		jBAjouterRobotTelecommande = new JButton("ajouter robot telecommande");
		jBAjouterRobotTelecommande.addActionListener(this);
		jBAjouterRobotTelecommande.setActionCommand("robSim");
		
		jPanelConteneurCrea.add(jLBNom);
		jPanelConteneurCrea.add(jTFNomRobot);

		jPanelConteneurCrea.add(jLBY);
		jPanelConteneurCrea.add(jTFYDepart);
		jPanelConteneurCrea.add(jLBX);
		jPanelConteneurCrea.add(jTFXDepart);
		jPanelConteneurCrea.add(jCBOrientation);
		jPanelConteneurCrea.add(jBAjouterRobotTelecommande);

		add(jPanelConteneurCrea);
		
		this.pack();
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Orientation orient;
		String nom;
		int x;
		int y;
		Case cas;
		Robot rbt;
		
		//création d'un nouveau robot simulé dans le logiciel, MAJ de la carte
		switch (e.getActionCommand()) {
		case "robSim":
			nom = jTFNomRobot.getText();
			orient = (Orientation) jCBOrientation.getSelectedItem();
			x = Integer.parseInt(jTFXDepart.getText());
			y = Integer.parseInt(jTFYDepart.getText());
			cas = plateau.getCaseByCoordinates(x, y);
			
			rbt = new Robot(nom,cas,orient,plateau,carte,log,robt);
			
			cas.setRobot("R"+orient.toString());
			
			controller.setRobot(rbt);
			log.addEvent("Nouveau Robot :"+rbt.toString());
			carte.updateCarte(plateau);
			dispose();
			break;
			
		case "robBlue":
			nom = jTFNomRobot.getText();
			orient = (Orientation) jCBOrientation.getSelectedItem();
			x = Integer.parseInt(jTFXDepart.getText());
			y = Integer.parseInt(jTFYDepart.getText());
			cas = plateau.getCaseByCoordinates(x, y);
			
			rbt = new Robot(nom,cas,orient,plateau,carte,log,robt);
			
			cas.setRobot("R"+orient.toString());
			
			controller.setRobot(rbt);
			log.addEvent("Nouveau Robot :"+rbt.toString());
			carte.updateCarte(plateau);
			dispose();
			break;
		}
	}
}
