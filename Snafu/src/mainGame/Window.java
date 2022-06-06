package mainGame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import classes.MusicHandler;

public class Window extends JFrame
{	
	private MusicHandler musicHandler;
	private Container cPane;   
	
	public Window()
	{
		super("Snafu");
		cPane = getContentPane();    
		cPane.setLayout(new CardLayout());
		setResizable(false);
		setSize(500, 500);
		
		ArrayList<File> musicPaths = loadAllMusic();
		musicHandler = new MusicHandler(musicPaths);

		// Adding all screens in the game
		TitleScreen titleScreen = new TitleScreen(this);
		titleScreen.setName("title screen");
		
		OptionsScreen optionsScreen = new OptionsScreen(this);
		optionsScreen.setName("options screen");
		
		MainScreen mainScreen = new MainScreen(this);
		mainScreen.setName("main screen");

		cPane.add(titleScreen, titleScreen.getName());
		cPane.add(optionsScreen, optionsScreen.getName());
		cPane.add(mainScreen, mainScreen.getName());
		setAsActive(titleScreen.getName());
		
		setVisible(true);
	}
	
	private ArrayList<File> loadAllMusic() // Loads every music track in the music folder
	{
		File sourceFolder = new File("src/Sounds/Music");
		ArrayList<File> files = new ArrayList<>();
		
		for (File musicFile : sourceFolder.listFiles())
		{
			files.add(musicFile);
		}
		
		return files;
	}
	
	public void switchScreen(String screenName) // Used to switch to a different JPanel by using the given String
	{
		CardLayout cardLayout = (CardLayout) (cPane.getLayout());
		String lowerCasedName = screenName.toLowerCase();
		
		cardLayout.show(cPane, lowerCasedName);
		setAsActive(lowerCasedName);
	}
	
	private void setAsActive(String name)
	{
		for (Component panel : cPane.getComponents())
		{
			if (panel.getName() != null && !panel.getName().equals(name))
				panel.setEnabled(false);
			else
				panel.setEnabled(true);
		}
	}

	public static void main(String[] args)
	{
		new Window();
	}
	
	public MusicHandler getMusicHandler()
	{
		return musicHandler;
	}
}
