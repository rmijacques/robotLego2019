package ia;

import plateau.Case;

public class Chemin {
	Case source;
	Case dest;
	String chemin;
	
	public Chemin(Case source,Case dest,String s) {
		this.source = source;
		this.dest = source;
		this.chemin = s;
	}

	public Case getSource() {
		return source;
	}

	public void setSource(Case source) {
		this.source = source;
	}

	public Case getDest() {
		return dest;
	}

	public void setDest(Case dest) {
		this.dest = dest;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}
	
	
	
	
}
