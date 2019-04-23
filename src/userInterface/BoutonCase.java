package userInterface;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoutonCase extends JButton{
	int posX;
	int posY;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5840119001037147708L;

	public BoutonCase(String p,ImageIcon img,int x,int y) {
		super(p,img);
		this.posX = x;
		this.posY = y;
	}
	

}
