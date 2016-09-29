package PokePack;

import java.util.ArrayList;

/**
 * This class will deal with organizing and sorting all evolution. 
 * @author Victor
 * @date 13/5/2014
 *
 */
public class Evolution {
	 
	private InfoScanner infoScanner;
	private ArrayList<String> listOfEvolutions;
	
	/**
	 * Constructor requires ID of Pokemon and will use the InfoScanner class
	 * to scan the evolution.txt document and retrieve necessary information.
	 * @param ID
	 */
	public Evolution(int ID){
		listOfEvolutions = new ArrayList<String>();
		infoScanner = new InfoScanner("Resources"+"/"+"evolution.txt", ID);
		listOfEvolutions = infoScanner.getEvolutionInfo();
	}
	
	/**
	 * This returns the Evolution ID as a long string formatted in such a way
	 * so that it's ready to use. 
	 * TODO: Change it so that you can decide in Info Window how it's to be 
	 * formatted. This way you can change separate strings to have certain 
	 * color/boldness/font etc...
	 * @param a
	 * @return
	 */
	public String returnEvoID(int a){
		String temp = "";
		//First need to check if the word matches 'none.
		if(listOfEvolutions.get(a).matches("N/A")){
			return listOfEvolutions.get(a);
		}
		if(Integer.parseInt(listOfEvolutions.get(a)) < 10){
			temp += "00";
		} else if (Integer.parseInt(listOfEvolutions.get(a)) < 100) {
			temp += "0";
		}
		temp += listOfEvolutions.get(a);
		temp += " -- ";
		a++;
		if(listOfEvolutions.get(a).startsWith("L", 0)){
			if(listOfEvolutions.get(a).matches("Leafstone")){		
			} else {
				String[] b = listOfEvolutions.get(a).split("L");
				temp += "Level " + b[1];
				temp += " --> ";
				return temp;
			}	
		}
		temp += listOfEvolutions.get(a);
		temp += " --> ";
		return temp;
	}
	
	/**
	 * Returns max number of pokemon in array list.
	 * @return
	 */
	public int returnMax(){
		return listOfEvolutions.size()-1;
	}
}
