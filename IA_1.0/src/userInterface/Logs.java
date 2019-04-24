package userInterface;


import javax.swing.JTextArea;


public class Logs extends JTextArea{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8377792190141612178L;

	public Logs(){
		super(10,20);
	}
	
	public void addEvent(String event) {
		append(event+"\n");
	}
}
