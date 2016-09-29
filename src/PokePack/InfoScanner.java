package PokePack;

import java.io.InputStream;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This will scan the <insert_name>.txt document and reads ALL Pokemon related
 * information.
 * 
 * @author Victor
 * @date 4/21/2014
 */
public class InfoScanner {

	private Scanner scanner;
	private ArrayList<String> listOfPokemon;
	private String description;
	private ArrayList<String> listOfEvolutions;
	private final int TITLE_SKIP = 4;

	/**
	 * Takes the FILENNAME as a filepath within the src folder and the
	 * linenumber (In our case Pokemon ID Number + 4).
	 * 
	 * @param filename
	 * @param lineNumber
	 */
	public InfoScanner(String filename, int lineNumber) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream is = classLoader.getResourceAsStream(filename);

		listOfPokemon = new ArrayList<String>();
		listOfEvolutions = new ArrayList<String>();

		scanner = new Scanner(is);

		for (int i = 1; i < lineNumber + TITLE_SKIP; i++) {		// The 4 is to ignore title in .txt.
			scanner.nextLine();
		}
	}

	/**
	 * this Scans the Pokemon so that the reader is on the correct line.
	 */
	public void scanPokemon() {
		Scanner temp = scanner;
		while (temp.hasNext()) {
			String tempWord = temp.next();

			if (tempWord.startsWith("#")) {
				String tempWithout = tempWord.substring(1);
				listOfPokemon.add(tempWithout);
			}

			if (temp.hasNext(">/")) {
				break;
			}
		}
	}

	// Copies and scans each line for a description. 
	public void scan() {
		Scanner temp = scanner;
		if (temp.hasNextLine()) {
			description = temp.nextLine();
		}
	}
	
	// Returns the description.
	public String description() {
		scan();
		return description;
	}

	/*
	 * Returns ID of Pokemon
	 */
	public int convertID() {
		int ID = Integer.parseInt(listOfPokemon.get(0));
		return ID;
	}

	/*
	 * Returns Name of Pokemon
	 */
	public String convertName() {
		String name = listOfPokemon.get(1);
		return name;
	}

	/*
	 * Returns the type of Pokemon
	 */
	public String convertType() {
		String type = listOfPokemon.get(2);
		return type;
	}

	/*
	 * Returns the type2 of Pokemon
	 */
	public String convertType2() {
		String type = listOfPokemon.get(3);
		return type;
	}

	public ArrayList<String> getEvolutionInfo() {
		Scanner temp = scanner;
		while (temp.hasNext()) {
			listOfEvolutions.add(temp.next());
			if (temp.hasNext(">/")) {
				break;
			}
		}
		return listOfEvolutions;
	}
}
