package plateau;
import robot.Orientation;

public class CaseOrientation {
	private Case c;
	private Orientation ori;
	
	public CaseOrientation(Case c, Orientation ori) {
		super();
		this.c = c;
		this.ori = ori;
	}


	public Case getC() {
		return c;
	}

	public Orientation getOri() {
		return ori;
	}		
}
