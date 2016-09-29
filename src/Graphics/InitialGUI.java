package Graphics;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.*;

import PokePack.Organizer;

/**
 * This class creates the user interface of the Pokedex.
 * 
 * @date 06/5/2014
 * @author Victor Kesten
 */
public class InitialGUI {

	private JFrame frame; 								// The window frame where the UI is localized.
	private final String VERSION = "Version 1.0.0";
	private final String AUTHOR = "Victor Kesten";
	private ImageGenerator imagePanel; 					// The image panel that displays temporary Pokemon Picture.
	private JList<String> pokeList; 					// List of all Pokemon. Contains Pokemon of form "ID Name".
	private String[] listOfPoke; 						// List of all pokemon. Contains Pokemon of form "ID Name". Can be changed.
	private Organizer organize;							// This will be the Final Organizer. 
	@SuppressWarnings("unused")
	private InfoWindow infoWindow; 
	private ClassLoader classLoader;

	/**
	 * Simple InitialGUI constructor. Calls the makeFrame() method that pretty
	 * much takes care of everything.
	 */
	public InitialGUI() {
		classLoader = Thread.currentThread().getContextClassLoader();
		organize = new Organizer();	
		listOfPoke = organize.returnAllPokeNames();
		makeFrame();
	}

	/**
	 * Creates the frame for our initial Pokedex window. Frame includes
	 * everything that is inside
	 * 
	 */
	public void makeFrame() {
		frame = new JFrame("Pokedex"); // Create the frame with title Pokedex.
		makeMenuBar(frame); // Initializes the makeMenuBar() method for frame.
							// Creates menu bar options.
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		URL resource = classLoader.getResource("Resources" + "/" + "PokedexImages" + "/"
				+ "Pokeball.png");
		BufferedImage img = ReadImageFiles.loadImage(resource);
		frame.setIconImage(img);
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());   // Specify the layout manager with nice spacing
		
		// Initiating Top Label. 
		JPanel workingPanel = new JPanel();
		workingPanel.setLayout(new BorderLayout(0, 0));
		workingPanel.setBackground(new Color(255,218,185));
		workingPanel.add(new JLabel("Pokedex", SwingConstants.CENTER),
				BorderLayout.CENTER);
		contentPane.add(workingPanel, BorderLayout.NORTH);

		// This is the image seen on right side. 
		imagePanel = new ImageGenerator();
		resource = classLoader.getResource("Resources" + "/" + "PokedexImages" + "/"
				+ "dogeMeme.jpg");
		BufferedImage image = ReadImageFiles.loadImage(resource);	// dogeMeme is temporary 'starting' screen. 
		imagePanel.setImage(image);							// initializes the background image. 
		contentPane.add(imagePanel, BorderLayout.CENTER);

		// Bottom label that shows user and version.
		// Name to left, Version to right.
		workingPanel = new JPanel();
		workingPanel.setLayout(new BorderLayout(0, 0));
		workingPanel.add(new JLabel("  " + AUTHOR), BorderLayout.WEST);
		workingPanel.add(new JLabel(VERSION + "  "), BorderLayout.EAST);
		workingPanel.setBackground(new Color(255,218,185));
		contentPane.add(workingPanel, BorderLayout.SOUTH);

		// SCROLL PANE AND POKELIST ###########################################

		// Add scrollable list of Pokemon. When list is interacted with, certain things happen.
		JPanel listPane = new JPanel();
		pokeList = new JList<String>(listOfPoke);
		pokeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 
		pokeList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				generateImage();
			}
		});
		
		//Click on image and get window
		imagePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					try {
						int n = pokeList.getSelectedIndex();
						infoWindow(n);
					} catch (NumberFormatException e){
						e.printStackTrace();
					}
					
				}
			}
		});

		// Double click mouse listener. 
		pokeList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					int n = pokeList.getSelectedIndex();
					infoWindow(n);
				}
			}
		});

		//This is the keyListener for the ENTER key. 
		pokeList.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					int n = pokeList.getSelectedIndex();
					// use n as the pokemon to display with a new method.
					infoWindow(n);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		// The scroll pane. 
		JScrollPane scrollPane = new JScrollPane(pokeList);
		scrollPane.setPreferredSize(new Dimension(125, 238));
		listPane.setBackground(new Color(255,218,185));	//Changeable color. 
		listPane.add(scrollPane);

		contentPane.add(listPane, BorderLayout.WEST);

		frame.pack();									// Needed to sort the content.
		frame.setVisible(true);							// Shows the UI. 
	}

	/**
	 * This method will run the InfoWindow class and provide us with a new frame
	 * that shows pokemon information like type and evolutions.
	 * 
	 * @param n
	 */
	public void infoWindow(int n) {
		infoWindow = new InfoWindow(n);
	}

	/**
	 * This method takes care of creating the MenuBar. TODO: Maybe split it up
	 * into categories.
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

		// create the File menu
		menu = new JMenu("File");
		menubar.add(menu);

		//Search Pokemon function. 
		item = new JMenuItem("Search Pokemon...");
		item.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_F, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tempID = searchPokemon();
				if (tempID == 0) {
					error();
				} else if (tempID == -1) {
					//do nothing. 
				} else {
					pokeList.setSelectedIndex(tempID);
					pokeList.ensureIndexIsVisible(pokeList.getSelectedIndex());
					int n = pokeList.getSelectedIndex();
					infoWindow(n);
				}
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

		// create the Help option on the Menu Bar. 
		// Shortcut Ctrl + T (Cmd + T)
		menu = new JMenu("Help");
		menubar.add(menu);

		// The "How to use" option under Help. 
		item = new JMenuItem("How to use...");
		item.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_T, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				howToUse();
			}
		});
		menu.add(item);

		// A Standard 'About this app' type of deal. 
		item = new JMenuItem("About Pokedex...");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about();
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

	/*
	 * Opens a message dialog containing application information.
	 */
	private void about() {
		URL resource = classLoader.getResource("Resources" + "/" + "PokedexImages" + "/"
				+ "aboutUnown.png");
		final ImageIcon icon = new ImageIcon(resource);
		JOptionPane.showMessageDialog(frame,
				"Pokedex\n This is an alpha version\n of a Pokedex.\n"
						+ VERSION + "\n" + AUTHOR, "About Pokedex",
				JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/*
	 * Opens a message dialog showing instructions on how to use the Pokedex.
	 */
	private void howToUse() {
		URL resource = classLoader.getResource("Resources" + "/" + "PokedexImages" + "/"
				+ "aboutUnown.png");
		final ImageIcon icon = new ImageIcon(resource);
		JOptionPane
				.showMessageDialog(
						frame,
						"Double click or press enter\n on a pokemon for more\n information.",
						"How to use Pokedex", JOptionPane.INFORMATION_MESSAGE,
						icon);
	}

	/*
	 * Opens a message dialog showing user that the Pokemon requested does not
	 * exist.
	 */
	private void error() {
		URL resource = classLoader.getResource("Resources" + "/" + "PokedexImages" + "/"
				+ "Pokeball.png");
		final ImageIcon icon = new ImageIcon(resource);
		JOptionPane.showMessageDialog(frame, "This Pokemon does not exist!",
				"Error", JOptionPane.ERROR_MESSAGE, icon);
	}

	/*
	 * Search function for finding pokemon.
	 * 
	 * @return returns their ID number.
	 */
	private int searchPokemon() {
		String a = JOptionPane.showInputDialog(frame,
				"Enter pokemon in search box:\n", "Find",
				JOptionPane.QUESTION_MESSAGE);
		if (a == null || a.matches("")) {
			//If cancel, return nothing. 
			return -1;
		}
		try{
			int e = Integer.parseInt(a);
			if (e <151){
				return e;
			} else {
				return 0;
			}
		} catch(NumberFormatException e){
			String[] tempList = listOfPoke;
			String work = a.toLowerCase().trim();
			for (int i = 1; i <= 151; i++) {
				String pokemon = tempList[i];
				String[] array = pokemon.split("\\s");
				pokemon = array[1];
				if (work.equals(pokemon.toLowerCase().trim())) {
					return i;
				}
			}
		}
		return 0;
	}

	/*
	 * Generates the image shown in the image panel based off selection index in
	 * the pokeList.
	 */
	private void generateImage() {
		int n = pokeList.getSelectedIndex();
		URL resource = classLoader.getResource("Resources"
				+ "/"
				+ "PokedexImages" + "/" + n + ".png");
		BufferedImage image = ReadImageFiles.loadImage(resource);
		if (image == null) {
			resource = classLoader.getResource("Resources"
					+ "/"
					+ "PokedexImages" + "/" + "0.png");
			image = ReadImageFiles
					.loadImage(resource);
		}
		imagePanel.setImage(image);
	}
}
