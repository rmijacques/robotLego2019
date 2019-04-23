package plateau;

import robot.Orientation;

//Un mouvement possible c'est à dire une direction et une case destination
public class Mouvement {
	String mouv;
	Case dest;
	Orientation orient;
	
	public Mouvement(String mouv,Orientation o,Case gp) {
		this.mouv = mouv;
		this.dest = gp;
		this.orient = o;
	}
	
	public String toString() {
		return "("+mouv+" "+orient.toString()+",("+dest.getX()+","+dest.getY()+"))";
	}

	public void setMouv(String mouv) {
		this.mouv = mouv;
	}

	public void setDest(Case dest) {
		this.dest = dest;
	}

	public String getMouv() {
		return mouv;
	}

	public Case getDest() {
		return dest;
	}
	
	public Orientation getOrient() {
		return orient;
	}
	
	
	
}
