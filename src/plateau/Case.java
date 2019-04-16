package plateau;

public class Case implements Comparable<Case>{
	String typeImage;
	String patient;
	String hopital;
	int x;
	int y;
	

	//TODO : reconfigurer type image
	
	public Case(String typeImage, int x, int y) {
		this.typeImage = typeImage;
		this.x = x;
		this.y = y;
		patient = "";
		hopital = "";
	}
	
	public boolean equals(Object o) {
		Case c = (Case)o;
		if(getX() == c.getX() && getY() == c.getY())
			return true;
		else
			return false;	
	}

	
	
	@Override
	public String toString() {
		return "Case [typeImage=" + typeImage + ", patient=" + patient + ", hopital=" + hopital + ", x=" + x + ", y="
				+ y + "]";
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
	
	public boolean hasPatient() {
		return !patient.equals("");
	}
	
	public boolean hasHopital() {
		return !hopital.equals("");
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void setTypeImage(String typeImage) {
		this.typeImage = typeImage;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
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

	
	
}
