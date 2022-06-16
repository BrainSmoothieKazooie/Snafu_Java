package mainGame;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;
import classes.*;
import classes.MusicHandler.*;

/*
 * OptionsScreen.java
 * 
 * The OptionsScreen contains three rhombuses
 * that represent the user's three options:
 * 
 * - Start a new game
 * - View the Original Game's manual
 * - Quit the application. 
 * 
 * rhombuses will be created in the createPolygon
 * method and their will be a label placed on top 
 * of the rhombuse that will be one of the values in 
 * the options String array. 
 * 
 * All labels will be placed on the top left corner
 * of the shape to allow for centered text. 
 * 
 * The user will be able to use the wasd keys or 
 * arrow keys to control this screen. Their
 * current position will be represented by a lighter
 * rhombuse placed underneath on the other polygons. 
 * 
 * In addition, a sound effect will be played when the 
 * user moves to another option.
 * 
 * Author: Andrew Tacoi
 */

@SuppressWarnings("serial")
public class OptionsScreen extends JPanel implements ScreenActions
{
    // *********************  Fields  *********************
   
    private final int PIXEL_SIZE;
    private MainScreen mainScreen;
	private int selectedButtonIndex;
	private String[] options = {"Start New Game", "Open Original Manual", "Quit"};
	private JLabel[] labels;
	private Polygon[] optionsPolygons; // These are the rhombuses that are behind the labels.
	private MusicHandler soundEffect;
	private Font font = new Font("impact", Font.PLAIN, 70);
	
	// *********************  Constructors  *********************
	
	public OptionsScreen(MainScreen screen)
	{
		setName("options screen");
		setBackground(new Color(243, 150, 0));
		setLayout(null);
        soundEffect = new MusicHandler(new File(getClass().getResource("/Resources/Sounds/Sound_Effects/Menu_Sound.wav").getPath()));
		mainScreen = screen;
		PIXEL_SIZE = mainScreen.getWidth()/6;
	}
	
	// *********************  Public Methods  *********************
	
	 @Override
	 public void step() 
	 { repaint(); }
    
	/* 
	 * Paints each rhombuse in the optionsPolygon and another polygon under the rhombuse that 
	 * the user has selected.
	 */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D graphics = (Graphics2D)g;
        graphics.setFont(mainScreen.textFont);
        
        for (int i = 0; i < optionsPolygons.length; i++)
        {
            if (selectedButtonIndex == i) // draws the users selected polygon
            {
                graphics.setColor(Color.cyan);
                Polygon underPolygon = new Polygon(optionsPolygons[i].xpoints, optionsPolygons[i].ypoints, optionsPolygons[i].npoints);
                underPolygon.translate(20, 20); // moves the rhombuse over by 20 pixels
                graphics.fillPolygon(underPolygon);
            }
            
            graphics.setColor(new Color(0f,0f,.5f, 1));
            graphics.fillPolygon(optionsPolygons[i]);
        }
    }
    
    public void checkOptionPressed() // Called when the user presses the Enter key
    {
        switch (options[selectedButtonIndex])
        {
            case "Start New Game":
                mainScreen.switchScreen("game setter screen");
                break;
                
            case "Open Original Manual":
                openURI();
                break;
                
            case "Quit":
                System.exit(0);
                break;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (KeyEvent.VK_UP == e.getKeyCode() || KeyEvent.VK_W == e.getKeyCode())
        {
            selectedButtonIndex--;
            
            if (selectedButtonIndex < 0) // Go to last option if it has gone past the first option
                selectedButtonIndex = options.length-1;
            
            try {
                soundEffect.play("Menu_Sound");
                
            } catch (MusicHandlerException e1) {

                e1.printStackTrace();
            }
        }
        else if (KeyEvent.VK_DOWN == e.getKeyCode() || KeyEvent.VK_S == e.getKeyCode())
        {
            selectedButtonIndex++;
            
            if (selectedButtonIndex >= options.length) // Go to first option if it has gone past the last option
                selectedButtonIndex = 0;
            
            try {
                soundEffect.play("Menu_Sound");
                
            } catch (MusicHandlerException e1) {
                
                e1.printStackTrace();
            }
        }
        
        if (KeyEvent.VK_ENTER == e.getKeyCode())
        {
            checkOptionPressed();
        }  
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    // *********************  Private Methods  *********************
	
    
    /* 
     * Adds each label by getting each string in the options array
     * and placing each label at the rhombuse's upper left corner point.
     */
    
	private void addLabels()
	{   
	    labels = new JLabel[options.length];
	    
	    for (int i = 0; i < options.length; i++)
	    {
	        labels[i] = new JLabel(options[i]);
	        labels[i].setFont(font);
	        labels[i].setForeground(Color.white);
	        add(labels[i]);
	        labels[i].setBounds(optionsPolygons[i].xpoints[1], optionsPolygons[i].ypoints[1], 1000, 200);
	    }
	}
	
	/* 
	 * Creates each rhombuse by getting four points, and creating a Polygon by using the Polygon class.
	 * 
	 * The four points in the x and y arrays 
	 * are the lower left corner, the upper left corner, 
	 * the upper right corner and the lower right corner.
	 * 
	 * Then each polygon is translated so that each one will be shifted down and to the right. 
	 * 
	 */
	private void createPolygons() 
	{
		int halfScreen = mainScreen.getHeight()/2;
		
		int[] x = {0, PIXEL_SIZE/2, PIXEL_SIZE * 2 + PIXEL_SIZE/2, PIXEL_SIZE * 2};
        int[] y = {halfScreen, halfScreen - PIXEL_SIZE, halfScreen - PIXEL_SIZE, halfScreen};
		
		Polygon rombusSize = new Polygon(x, y, x.length);
		
        int[] xPositions = {PIXEL_SIZE/2, PIXEL_SIZE/2 * 2, PIXEL_SIZE/2 * 3};
        
		int[] yPositions = {mainScreen.getHeight()/6 - PIXEL_SIZE - 35, mainScreen.getHeight()/6, 
		                    mainScreen.getHeight()/6 + PIXEL_SIZE + 35};
		
		optionsPolygons = new Polygon[xPositions.length];
		
		for (int i = 0; i < optionsPolygons.length; i++) // Adds and shifts each polygon.
		{
			optionsPolygons[i] = new Polygon(rombusSize.xpoints, rombusSize.ypoints, rombusSize.npoints);
			optionsPolygons[i].translate(xPositions[i], yPositions[i]);
		}
	}

	private void openURI() // Opens the Snafu manual URL
	{
	    try {
            java.awt.Desktop.getDesktop().browse(new URI("https://archive.org/details/intellivision-snafu/mode/2up"));
            
        } catch (IOException e) {
            
            e.printStackTrace();
        } catch (URISyntaxException e) {
            
            e.printStackTrace();
        }
    }

    @Override
	public void initializeScreen() 
	{        
        selectedButtonIndex = 0;
        
        if (!mainScreen.getMusicHandler().isPlaying()) // Plays only after the user has exited the TitleScreen
        {
            try {
                
                mainScreen.getMusicHandler().play("Comfort_Zone", true);
            } catch (MusicHandlerException e) {
                
                e.printStackTrace();
            }
        }
        
		createPolygons();
        addLabels();
	}
}
