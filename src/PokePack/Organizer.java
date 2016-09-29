package PokePack;

import java.util.HashMap;

import PokePack.Pokemon;

/**
 * This class is meant to organize all Pokemon. Like put it into HashMaps and return names and all of that.
 * @author Victor
 * @date 03/5/2014
 */

public class Organizer {

	private HashMap<Integer, Pokemon> pokeMap;
	
	/**
	 * Takes all pokemon and put them in an Hashmap for easy access.
	 */
	public Organizer(){
		pokeMap = new HashMap<>();
		for (int i = 1; i <= 151; i++){
			pokeMap.put(i, new Pokemon(i));
		}	
	}
	/**
	 * This returns all the pokemon names in an organize String array.
	 * @return
	 */
	public String[] returnAllPokeNames(){
		String[] a = new String[152];
		for (int i = 1; i <= 151; i++){
			Pokemon b = pokeMap.get(i);
			a[i] = b.getID() + " " + b.getName();
		}
		return a;
	}
}
