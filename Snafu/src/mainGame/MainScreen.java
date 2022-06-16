package mainGame;

import javax.swing.*;

import classes.MusicHandler;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/*
 * MainScreen.java
 * 
 * A class that represents the MainScreen that controls all other screens
 * and contains a MusicHandler for the game's background music. 
 * 
 * All screens are stored in a CardLayout, where each can be switched 
 * by using the switchScreen method. 
 * 
 * All JPanel's that wish to be active will be enabled, while the
 * rest of the cards will be disabled. This is done through the
 * setAsActive method. 
 * 
 * Every JPanel added to the CardLayout will inherit all the methods
 * from the ScreenActions interface in order to guarantee that each method
 * can use this classes's ActionListener and KeyListener. 
 * 
 * The General flow of the application is... 
 * 
 * TitleScreen -> OptionsScreen -> GameSetter -> GameScreen
 *                              -> Open URL
 *                              -> Quit
 * 
 * Then after this is done the program will flow like this:
 * 
 * OptionsScreen -> GameSetter -> GameScreen
 *               -> Open URL
 *               -> Quit
 *
 * Author: Andrew Tacoi
 */

public class MainScreen extends JPanel implements ActionListener, KeyListener
{
    // *********************  Fields  *********************
    
	private MusicHandler backgroundMusicHandler;
	private JPanel activeCard; // The JPanel currently active
	protected Timer timer; // Main Timer of the Game
	protected Font textFont;
	
	public MainScreen()
	{	
		setPreferredSize(new Dimension(1200, 800));
		setSize(1200, 800);
		setFocusable(true);
		setBackground(Color.black);
        setLayout(new CardLayout());
		addKeyListener(this);

        backgroundMusicHandler = new MusicHandler(loadAllMusic());
		
		add(new TitleScreen(this), "title screen");
		add(new OptionsScreen(this), "options screen");
		add(new GameSetter(this), "game setter screen");
		
		setAsActive("title screen");
		
		textFont = new Font("impact", Font.PLAIN, 70);
		
		timer = new Timer(1000/60, this);
		timer.start();
	}
	
	// *********************  Public Methods  *********************

	/* 
	 * Every Card in the CardLayout will update by using this classe's Timer
	*  through the ScreenActions step() method.
	*/
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (activeCard instanceof ScreenActions)
		{
			ScreenActions activeScreen = (ScreenActions)activeCard;
			activeScreen.step();
		}
	}
	
	/* 
     * Every Card in the CardLayout will get input from the ScreenActions
     * method keyPressed() and keyReleased() in order to ensure the KeyListener
     * is always heard. 
    */
    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (activeCard instanceof ScreenActions)
        {
            ScreenActions activeScreen = (ScreenActions)activeCard;
            activeScreen.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if (activeCard instanceof ScreenActions)
        {
            ScreenActions activeScreen = (ScreenActions)activeCard;
            activeScreen.keyReleased(e);
        }
    }
	
	public void switchScreen(String screenName) // Used to switch to a different JPanel by using the given String
	{
		CardLayout cardLayout = (CardLayout) (getLayout());
		String lowerCasedName = screenName.toLowerCase();
		
		cardLayout.show(this, lowerCasedName);
		setAsActive(lowerCasedName); 
	}
    
    public MusicHandler getMusicHandler()
    { return backgroundMusicHandler; }
	
    @Override
    public void keyTyped(KeyEvent e) {}
    
    // *********************  Private Methods  *********************
	
	/* 
	 * Enables the Screen specified in the parameters by looping through
	 * each component, seeing if that component is a JPanel and then 
	 * determines if it should enable it based upon if the Screen's
	 * name matches the given name.
	 * 
	 * This is done to prevent multiple Screens from updating at the
	 * same time.
	 * 
	 * In addition, it will use the ScreenActions method, initalizeScreen()
	 * only if it is a ScreenActions.
	 * 
	*/
	private void setAsActive(String name)
	{
		for (int i = 0; i < getComponents().length; i++)
		{
			if (getComponent(i) instanceof JPanel)
			{
				JPanel panel = (JPanel)getComponent(i);
				
				if (panel.getName().equals(name))
				{
					panel.setEnabled(true);
					activeCard = panel;
				}
				
				else
					panel.setEnabled(false);
				
                if (panel.isEnabled() && panel instanceof ScreenActions && !(panel instanceof GameScreen)) // Does not run when the Card is a GameScreen.
                {
                    ScreenActions activeScreen = (ScreenActions)panel;
                    activeScreen.initializeScreen();
                }
			}
		}
	}
	
	private ArrayList<File> loadAllMusic() // Loads every music track in the music folder
    {
	    ArrayList<File> files = new ArrayList<>();
	    
	    String[] trackNames = {"Better_Call.wav", "Comfort_Zone.wav", "Rolling_Down_The_Street,_In_My_Katamari.wav",
	                           "Handsome_Guy.wav", "Dreemurr.wav", "Giygas.wav", "AbsoluteGarbage.wav"};
	    
        for (String trackName : trackNames)
        {
            files.add(new File(getClass().getResource("/Resources/Sounds/Music/" + trackName).getPath()));
        }
        
        return files;
    }
}
