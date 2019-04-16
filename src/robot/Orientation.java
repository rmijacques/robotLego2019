package robot;

import java.util.ArrayList;
import java.util.List;

public enum Orientation {
	EAST,WEST,NORTH,SOUTH;
	
	
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
			return orient;
		case "u":
			return UTurn(orient);
		default :
			return orient;
		}
	}
	
	public static Orientation UTurn(Orientation orient) {
		switch(orient) {
		case EAST:
			return WEST;
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		case NORTH:
			return SOUTH;
		default :
			return null;
		}
	}
	
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
	
	public static List<String> typeCasesPourOrient(Orientation ori){
		List<String> ret = new ArrayList<>();
		
		switch(ori){
		case EAST:
			ret.add("GB");
			ret.add("GD");
			ret.add("GH");
			ret.add("GDB");
			ret.add("GHB");
			ret.add("GHD");
			break;
		case WEST:
			ret.add("HD");
			ret.add("GD");
			ret.add("DB");
			ret.add("GDB");
			ret.add("GHD");
			ret.add("HDB");
			break;
		case NORTH:
			ret.add("GB");
			ret.add("DB");
			ret.add("GDB");
			ret.add("GHB");
			ret.add("HDB");
		case SOUTH:
			ret.add("GH");
			ret.add("HD");
			ret.add("GHB");
			ret.add("GHD");
			ret.add("HDB");
			break;
		default:
			break;
		}
		return ret;
	}
}
