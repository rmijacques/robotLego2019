package robot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JACQUES Rémi
 *
 */
public enum Orientation {
	EAST,WEST,NORTH,SOUTH;
	
	
	/**
	 * @param orient : orientation de départ du robot
	 * @param mouv : mouvement à effectuer
	 * @param typeCase : type de la case dans laquelle effectuer le mouvement
	 * @return nouvelle orientation après le mouvement mouv
	 */
	public static Orientation nouvOrientApresMouv(Orientation orient,String mouv,String typeCase) {
		switch(mouv) {
		case "l":
			if(typeCase.length() == 3) {
				if((typeCase.equals("GDB") && orient == Orientation.EAST)
				|| (typeCase.equals("GHB") && orient == Orientation.SOUTH)
				|| (typeCase.equals("GHD") && orient == Orientation.WEST)
				|| (typeCase.equals("HDB") && orient == Orientation.NORTH))
					return orient;
			}
			return leftTurn(orient);		
		case "r":
			if(typeCase.length() == 3) {
				if((typeCase.equals("GDB") && orient == Orientation.WEST)
				|| (typeCase.equals("GHB") && orient == Orientation.NORTH)
				|| (typeCase.equals("GHD") && orient == Orientation.EAST)
				|| (typeCase.equals("HDB") && orient == Orientation.SOUTH))
					return orient;
			}
			return rightTurn(orient);
		case "s":
			if((typeCase.equals("GH") && orient == SOUTH)|| (typeCase.equals("HD")&& orient == WEST)  
					|| (typeCase.equals("DB")&& orient == NORTH) || (typeCase.equals("GB")&& orient == EAST))
				return rightTurn(orient);
			else if ((typeCase.equals("GH") && orient == EAST)|| (typeCase.equals("HD")&& orient == SOUTH)  
					|| (typeCase.equals("DB")&& orient == WEST) || (typeCase.equals("GB")&& orient == NORTH))
				return leftTurn(orient);
			return orient;
		case "u":
			return UTurn(orient,typeCase);
		default :
			return orient;
		}
	}
	
	/**
	 * @param orient : orientation avant le Uturn
	 * @param typeCase : type de la case dans laquelle effectuer le UTurn
	 * @return nouvelle orientation après un Uturn
	 */
	public static Orientation UTurn(Orientation orient,String typeCase) {
		List<String> tpCasesPoss = new ArrayList<>();
		for(Orientation o : Orientation.values()) {
			tpCasesPoss = typeCasesPourOrient(o);
			if (o != orient) {
				for(String s : tpCasesPoss) {
					if(s.equals(typeCase))
						return o;
				}
			}	
		}
		return null;
	}
	
	
	/**
	 * @param orient : Orientation avant un virage à gauche
	 * @return oriantation après un virage à gauche
	 */
	public static Orientation leftTurn(Orientation orient) {
		switch(orient) {
		case EAST:
			return NORTH;
		case SOUTH:
			return EAST;
		case WEST:
			return SOUTH;
		case NORTH:
			return WEST;
		default :
			return null;
		}
		
	}
	
	/**
	 * @param orient : Orientation avant un virage à droite
	 * @return oriantation après un virage à droite
	 */
	public static Orientation rightTurn(Orientation orient) {
		switch(orient) {
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		case NORTH:
			return EAST;
		default :
			return null;
		}
	}
	
	
	/**
	 * @param ori: Orientation à vérifier
	 * @return une liste de cases ou il est possible d'avoir l'orientation ori
	 */
	public static List<String> typeCasesPourOrient(Orientation ori){
		List<String> ret = new ArrayList<>();
		
		switch(ori){
		case EAST:
			ret.add("HD");
			ret.add("GD");
			ret.add("DB");
			ret.add("GDB");
			ret.add("GHD");
			ret.add("HDB");
			break;
		case WEST:
			ret.add("GB");
			ret.add("GD");
			ret.add("GH");
			ret.add("GDB");
			ret.add("GHB");
			ret.add("GHD");
			break;
			
		case NORTH:
			ret.add("GH");
			ret.add("HB");
			ret.add("HD");
			ret.add("GHB");
			ret.add("GHD");
			ret.add("HDB");
			break;
		case SOUTH:
			ret.add("GB");
			ret.add("DB");
			ret.add("GDB");
			ret.add("GHB");
			ret.add("HDB");
			ret.add("HB");
			break;

		default:
			break;
		}
		return ret;
	}
}
