package plateau;

/**
 * @author Rémi Jacques
 *
 */
public class Case implements Comparable<Case>{
	private String typeImage;
	private String patient;
	private String hopital;
	private String robot;
	private int x;
	private int y;
	


	
	/**
	 * @param typeImage Le type de la case, pour voir les différents types voir <a target="_blank" rel="noopener noreferrer" href="https://github.com/zebest321/robotLego2019/tree/master/imagesCases">ici</a>.
	 * @param x La position x de la case dans le graphe.
	 * @param y La position y de la case dans le graphe.
	 * @see <a target="_blank" rel="noopener noreferrer" href="https://github.com/zebest321/robotLego2019/tree/master/imagesCases">Types d'images.</a>
	 */
	public Case(String typeImage, int x, int y) {
		this.typeImage = typeImage;
		this.x = x;
		this.y = y;
		patient = "";
		hopital = "";
		robot = "";
	}
	

	public boolean equals(Object o) {
		Case c = (Case)o;
		if(getX() == c.getX() && getY() == c.getY())
			return true;
		else
			return false;	
	}

	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Case [typeImage=" + typeImage + ", patient=" + patient + ", hopital=" + hopital + ", x=" + y + ", y="
				+ x + "]";
	}
	
	/**
	 * @return Ne renvoie que les coordonnées x et y.
	 */
	public String toStringSimpl() {
		return "("+y+" , "+x+")";
	}

	public int compareTo(Case c) {
		if(x > c.getX())
			return 1;
		else if(x < c.getX())
			return -1;
		else if(y > c.getY())
			return 1;
		else if(y < c.getY())
			return -1;
		return 0;
	}
	
	/**
	 * @return Renvoie vrai si un patient est présent dans la case.
	 */
	public boolean hasPatient() {
		return !patient.equals("");
	}
	
	/**
	 * @return Renvoie vrai si un hôpital est présent dans la case.
	 */
	public boolean hasHopital() {
		return !hopital.equals("");
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}


	public String getPatient() {
		return patient;
	}

	public void prendrePatient() {
		patient = "";
	}
	
	public void addPatient() {
		patient = "patients/";
	}

	public String getHopital() {
		return hopital;
	}

	public void setHopital(String hopital) {
		this.hopital = hopital;
	}

	public String getTypeImage() {
		return typeImage;
	}
	
	public void setRobot(String rob) {
		this.robot = rob;
	}
	
	public String getRobot() {
		return robot;
	}
	
	public boolean isCase2() {
		return typeImage.length() == 2;
	}

	
	
}