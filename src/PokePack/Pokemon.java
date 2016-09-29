package PokePack;

/**
 * This class uses InfoScanner and gathers all of the information needed
 * for each pokemon. 
 * @date 06/5/2014
 * @author Victor
 *
 */
public class Pokemon {
	
	private int ID;
	private String name;
	private String type;
	private String type2;
	private String description;
	// We will add base stats and all that at a later date. 
	
	private InfoScanner test;
	private InfoScanner desc;
	
	/**
	 * List ID number instead of name. 
	 * @param ID
	 * @param name
	 * @param type
	 * @param evo
	 */
	public Pokemon(int ID) {
		test = new InfoScanner("Resources"+"/"+"pokemon.txt", ID);
		test.scanPokemon();
		this.ID = test.convertID();
		this.name = test.convertName();
		this.type = test.convertType();
		this.type2 = test.convertType2();

	}
	
	/**
	 * Quick-fix-duct-tape constructor dealing with description. 
	 * @param ID
	 * @param a
	 */
	
	public Pokemon(int ID, String a){	
		desc = new InfoScanner("Resources"+"/"+"description.txt", ID);
		desc.scan();
		this.description = desc.description();
	}
	

	/*
	 * Returns the Pokemon ID as an int. 
	 */
	public int getID(){
		return ID;
	}
	
	/*
	 * Returns the Pokemon name as a String.
	 */
	public String getName(){
		return name;
	}
	
	/*
	 * Returns the Pokemon type as a String.
	 */
	public String getType(){
		return type + "_" + type2;
	}
	
	@Override
	public String toString(){
		return getID() + " " + getName();
	}
	
	//Description
	public String description(){
		return description;
	}
}
	
