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


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaseOrientation other = (CaseOrientation) obj;
		if (c == null) {
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		return true;
	}		
	
	
}
