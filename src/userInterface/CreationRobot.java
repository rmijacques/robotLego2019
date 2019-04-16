package userInterface;

	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
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
		
		this.log = log;
		this.carte = carte;
		this.gestionRobots = gr;
		this.plateau = plat;
		this.controller = gr.controller;
		this.robt = robt;
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));

		jPanelConteneurCrea = new JPanel();
		jPanelConteneurCrea.setLayout(new BoxLayout(jPanelConteneurCrea, BoxLayout.Y_AXIS));
		
		jTFXDepart = new JTextField("x");
		jTFYDepart = new JTextField("y");
		jCBOrientation = new JComboBox<>(ori);
		jTFAddresseBluetooth = new JTextField("addr");
		jTFNomRobot = new JTextField("nom");
		
		jBAjouterRobot = new JButton("ajouter robot bluetooth");
		jBAjouterRobot.addActionListener(this);
		jBAjouterRobot.setActionCommand("robBlue");
		
		jBAjouterRobotTelecommande = new JButton("ajouter robot simulé");
		jBAjouterRobotTelecommande.addActionListener(this);
		jBAjouterRobotTelecommande.setActionCommand("robSim");
		
		jPanelConteneurCrea.add(jTFNomRobot);
		jPanelConteneurCrea.add(jTFAddresseBluetooth);
		jPanelConteneurCrea.add(jTFXDepart);
		jPanelConteneurCrea.add(jTFYDepart);
		jPanelConteneurCrea.add(jCBOrientation);
		jPanelConteneurCrea.add(jBAjouterRobot);
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
			
			cas.setTypeImage(cas.getTypeImage()+"R"+orient.toString());
			
			controller.setRobot(rbt);
			log.addEvent("Nouveau Robot :"+rbt.toString());
			carte.updateCarte(plateau);
			dispose();
			break;
		}
	}
}
