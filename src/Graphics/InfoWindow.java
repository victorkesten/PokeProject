package Graphics;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.*;

import PokePack.Pokemon;
import PokePack.Evolution;

/**
 * This class is used for creating the information window that opens when the
 * user double clicks on a Pokemon from the list or on the picture itself.
 * 
 * @date 12/5/2014
 * @author Victor
 * 
 */
public class InfoWindow {

	private JFrame frame;	// Working Frame. 
	private Pokemon pokemon;	// Working pokemon. 
	private Pokemon pokemonDesc;	// Pokemon description
	private JTextArea textArea;		// The work-with text area.
	private ImageGenerator imagePanel;
	private ImageGenerator imagePanel2;
	private Evolution evolution;	// Evolution information. 
	private ClassLoader classLoader;
	private Container contentPane;

	/**
	 * The constructor expects a Pokemon ID integer and will create the page
	 * based on the number it is given.
	 * 
	 * @param pokeID
	 */
	public InfoWindow(int pokeID) {
		classLoader = Thread.currentThread().getContextClassLoader();
		pokemon = new Pokemon(pokeID);
		pokemonDesc = new Pokemon(pokeID, "");
		evolution = new Evolution(pokeID);

		createFrame();

	}

	/**
	 * This method creates the frame by specifying window size and window
	 * location.
	 */
	private void createFrame() {
		frame = new JFrame("Pokedex - " + pokemon.getName());
		makeMenuBar(frame);
		frame.setResizable(false);

		initialize();

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() / 3;
		int y = (int) rect.getMaxY() / 3;
		frame.setLocation(x, y);

		frame.pack();									// Needed to sort the content.
		frame.setVisible(true);							// Shows the UI. 
	}

	/**
	 * Creates the information contained inside the frame. Loads and reads files
	 * from the resource directory.
	 */
	private void initialize() {

		URL resource = classLoader.getResource("Resources" + "/"
				+ "PokedexSmallImages" + "/" + pokemon.getID() + ".png");
		BufferedImage img = ReadImageFiles.loadImage(resource);
		frame.setIconImage(img);

		String type = pokemon.getType();
		String[] a = type.split("_");
		type = a[0].toLowerCase();

		contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel workingPanel = new JPanel();
		workingPanel.setLayout(new BorderLayout(0, 0));
		workingPanel.setBackground(colorPicker());
		JLabel tempA = new JLabel("Pokedex", SwingConstants.CENTER);
		if (type.matches("fighting") || type.matches("psychic")
				|| type.matches("poison") || type.matches("dragon")) {
			tempA.setForeground(Color.WHITE);
		}
		workingPanel.add(tempA, BorderLayout.CENTER);
		contentPane.add(workingPanel, BorderLayout.NORTH);

		// Text Panels displaying the information. 

		workingPanel = new JPanel();
		workingPanel.setBackground(Color.WHITE);

		textArea = new JTextArea("Name: ");
		textAreaEdit();
		textArea.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		workingPanel.add(textArea);

		textArea = new JTextArea("" + pokemon.getName());
		textAreaEdit();
		textArea.setFont(new Font("TimesNewRoman", Font.ITALIC, 12));
		textArea.setBounds(new Rectangle(275, 10)); 	//Original 320, 20 without image.
		workingPanel.add(textArea);

		imagePanel = new ImageGenerator();
		smallImage();
		workingPanel.add(imagePanel);

		textArea = new JTextArea("Description: ");
		textAreaEdit();
		textArea.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		workingPanel.add(textArea);

		textArea = new JTextArea("''" + pokemonDesc.description() + "''");
		textAreaEdit();
		textArea.setBounds(new Rectangle(320, 100));
		textArea.setFont(new Font("TimesNewRoman", Font.ITALIC, 12));
		workingPanel.add(textArea);

		textArea = new JTextArea("Type: ");
		textAreaEdit();
		textArea.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		workingPanel.add(textArea);

		checkType();
		workingPanel.add(imagePanel);
		workingPanel.add(imagePanel2);

		textArea = new JTextArea("");
		textAreaEdit();
		textArea.setBounds(new Rectangle(250, 20));
		workingPanel.add(textArea);

		textArea = new JTextArea("ID: ");
		textAreaEdit();
		textArea.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		workingPanel.add(textArea);

		textArea = new JTextArea(getSpecialID());
		textAreaEdit();
		textArea.setBounds(new Rectangle(320, 100));
		textArea.setFont(new Font("TimesNewRoman", Font.ITALIC, 12));
		workingPanel.add(textArea);

		textArea = new JTextArea("Evolutions: ");
		textAreaEdit();
		textArea.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		workingPanel.add(textArea);

		int i = 0;
		int counter = 0;
		String evolutionString = "";
		while (i <= evolution.returnMax() / 2) {
			evolutionString += evolution.returnEvoID(counter) + " ";
			counter = counter + 2;
			i++;
		}
		String t[] = evolutionString.split("-- ! -->");
		evolutionString = t[0];

		textArea = new JTextArea(evolutionString);
		textAreaEdit();
		textArea.setBounds(new Rectangle(320, 100));
		textArea.setFont(new Font("TimesNewRoman", Font.ITALIC, 12));
		workingPanel.add(textArea);

		workingPanel.setPreferredSize(new Dimension(450, 250));
		contentPane.add(workingPanel, BorderLayout.CENTER);

		workingPanel = new JPanel();
		workingPanel.setLayout(new BorderLayout(0, 0));
		workingPanel.setBackground(colorPicker());

		// Labels so you can click next or previous Pokemon.

		JLabel left = new JLabel(" <   " + getSpecialID(pokemon.getID(), 0));
		left.setFont(new Font("TimesNewRoman", Font.BOLD + Font.ITALIC, 12));
		if (type.matches("fighting") || type.matches("psychic")
				|| type.matches("poison") || type.matches("dragon")) {
			left.setForeground(Color.WHITE);
		}
		if (pokemon.getID() != 1) {
			left.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					if (evt.getClickCount() == 1) {

						InfoWindow newPokemon = new InfoWindow(
								pokemon.getID() - 1);
						newPokemon.setXY(frame.getX(), frame.getY());
						close();
					}
				}
			});
			workingPanel.add(left, BorderLayout.WEST);
		}

		JLabel right = new JLabel(getSpecialID(pokemon.getID(), 1) + "  >  ");
		right.setFont(new Font("TimesNewRoman", Font.BOLD + Font.ITALIC, 12));
		if (type.matches("fighting") || type.matches("psychic")
				|| type.matches("poison") || type.matches("dragon")) {
			right.setForeground(Color.WHITE);
		}
		if (pokemon.getID() != 151) {
			right.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					if (evt.getClickCount() == 1) {

						InfoWindow newPokemon = new InfoWindow(
								pokemon.getID() + 1);
						newPokemon.setXY(frame.getX(), frame.getY());
						close();
					}
				}
			});
			workingPanel.add(right, BorderLayout.EAST);
		}

		JLabel center = new JLabel(getSpecialID(), SwingConstants.CENTER);
		center.setFont(new Font("TimesNewRoman", Font.BOLD, 12));

		if (type.matches("fighting") || type.matches("psychic")
				|| type.matches("poison") || type.matches("dragon")) {
			center.setForeground(Color.WHITE);
		}

		workingPanel.add(center, BorderLayout.CENTER);
		contentPane.add(workingPanel, BorderLayout.SOUTH);

	}

	/**
	 * Sets the x and y coordinates on the screen.
	 * 
	 * @param a
	 *            x Coordinate
	 * @param b
	 *            y Coordinate
	 */
	private void setXY(int a, int b) {
		frame.setLocation(a, b);
	}

	/**
	 * This converts the pokemon ID to a String with the necessary amount of '0'
	 * in front of them.
	 * 
	 * @return ID as a String.
	 */
	private String getSpecialID() {
		String ID = "" + pokemon.getID();
		if (pokemon.getID() < 10) {
			ID = "00" + pokemon.getID();
		} else if (pokemon.getID() < 100) {
			ID = "0" + pokemon.getID();
		}
		return ID;
	}

	/**
	 * This special ID does the same thing as above but prioritizes left or
	 * right. Used for the JLabels that represent next or previous.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private String getSpecialID(int a, int b) {
		String ID;
		if (b == 0) {
			a--;
			ID = "" + a;
			if (a < 10) {
				ID = "00" + a;
			} else if (a < 100) {
				ID = "0" + a;
			}
		} else {
			a++;
			ID = "" + a;
			if (a < 10) {
				ID = "00" + a;
			} else if (a < 100) {
				ID = "0" + a;
			}
		}
		return ID;
	}

	/**
	 * This picks the color of the window depending on the type of the Pokemon.
	 * 
	 * @return
	 */
	private Color colorPicker() {
		String type = pokemon.getType();
		String[] a = type.split("_");
		type = a[0].toLowerCase();
		Color color = new Color(252, 145, 50);
		switch (type) {
		case "bug":
			color = new Color(125, 176, 6);
			break;
		case "dark":
			color = new Color(78, 78, 78);
			break;
		case "dragon":
			color = new Color(138, 43, 226);
			break;
		case "electric":
			color = new Color(248, 255, 23);
			break;
		case "fighting":
			color = new Color(178, 34, 34);
			break;
		case "fire":
			color = new Color(252, 145, 50);
			break;
		case "flying":
			color = new Color(255, 182, 193);
			break;
		case "ghost":
			color = new Color(147, 112, 219);
			break;
		case "grass":
			color = new Color(50, 205, 50);
			break;
		case "ground":
			color = new Color(205, 175, 149);
			break;
		case "ice":
			color = new Color(0, 255, 255);
			break;
		case "normal":
			color = new Color(205, 200, 177);
			break;
		case "poison":
			color = new Color(160, 32, 240);
			break;
		case "psychic":
			color = new Color(208, 32, 144);
			break;
		case "rock":
			color = new Color(189, 183, 107);
			break;
		case "steel":
			color = new Color(205, 201, 201);
			break;
		case "water":
			color = new Color(135, 206, 250);
			break;
		}
		return color;
	}

	/**
	 * Used to set standard edits to the textAreas that follow throughout the
	 * program.
	 */
	private void textAreaEdit() {
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setOpaque(false);
		textArea.setEditable(false);
	}

	/**
	 * This checks the Pokemon type and loads the type Image displayed on the
	 * Information Window. Called by Initializer.
	 */
	private void checkType() {
		String type = pokemon.getType();
		String[] a = type.split("_");
		type = a[0].toLowerCase();
		imagePanel = new ImageGenerator();
		imagePanel2 = new ImageGenerator();
		URL resource = classLoader.getResource("Resources" + "/" + "TypeImages"
				+ "/" + type + ".png");
		BufferedImage image = ReadImageFiles.loadImage(resource);
		imagePanel.setImage(image);

		//Some Pokemon have two types and this makes sure and loads both if that's the case.

		if (!a[1].matches("none")) {
			type = a[1].toLowerCase();
			resource = classLoader.getResource("Resources" + "/" + "TypeImages"
					+ "/" + type + ".png");
			BufferedImage image2 = ReadImageFiles.loadImage(resource);
			imagePanel2.setImage(image2);
		} else {
			resource = classLoader.getResource("Resources" + "/" + "TypeImages"
					+ "/" + "none.png");
			BufferedImage image2 = ReadImageFiles.loadImage(resource);
			imagePanel2.setImage(image2);
		}
	}

	/**
	 * This sets the small Pokemon image you see in the right corner.
	 */
	public void smallImage() {
		URL resource = classLoader.getResource("Resources" + "/"
				+ "PokedexSmallImages" + "/" + pokemon.getID() + ".png");
		BufferedImage image = ReadImageFiles.loadImage(resource);
		imagePanel.setImage(image);
	}

	/**
	 * This method takes care of creating the MenuBar. 
	 * 
	 * @param frame
	 */
	private void makeMenuBar(JFrame frame) {
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();

		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);

		JMenu menu;
		JMenuItem item;

		//create the File menu
		menu = new JMenu("File");
		menubar.add(menu);

		item = new JMenuItem("Close");
		item.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_W, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});

		menu.add(item);

		// a Quit function with shortcut Ctrl + Q (cmd + Q) 
		item = new JMenuItem("Quit");
		item.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		menu.add(item);
	}

	/*
	 * Command to quit the program.
	 */
	private void quit() {
		System.exit(0);
	}

	/**
	 * Closes the current window. Called internally.
	 */
	private void close() {
		frame.setVisible(false); //you can't see me!
		frame.dispose(); //Destroy the JFrame object
	}
}
