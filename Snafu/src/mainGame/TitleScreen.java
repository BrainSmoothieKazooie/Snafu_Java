package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import classes.*;
import classes.MusicHandler.MusicHandlerException;

/**
 * TitleScreen.java
 * 
 * When the Title Screen is loaded, the games logo is moved for 
 * the amount of total time it takes to load it, represented
 * by the TITLE_LOADING_TIME variable. 
 * 
 * Then the user can load the options Screen by pressing enter. 
 *
 * Author: Andrew Tacoi
 */

@SuppressWarnings("serial")
public class TitleScreen extends JPanel implements ScreenActions
{
    // *********************  Fields  *********************
    
	private MainScreen mainScreen;
	private long titleTime;
	private final long TITLE_LOADING_TIME = 200;
	private Sprite logo;
	private boolean finishedLoading;
	private int speed = 5;
	
	// *********************  Constructors  *********************
	
	public TitleScreen(MainScreen screen)
	{
		setName("title screen");
		mainScreen = screen;
		setBackground(Color.black);
		logo = new Sprite(new ImageIcon("src/Sprites/Snake/Snafu_Box_Art.jpg").getImage());
		logo.resize(mainScreen.getWidth()/4, mainScreen.getHeight()/2);
	}
	
	// *********************  Public Methods  *********************
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(logo.getImage(), logo.getCollider().x, logo.getCollider().y, 
					logo.getCollider().width, logo.getCollider().height, null);
		
		if (finishedLoading)
		{
			Graphics2D graphics = (Graphics2D)g;
			graphics.setColor(Color.white);
			graphics.setFont(new Font("SansSerif", Font.PLAIN, 40));
			graphics.drawString("Snafu", mainScreen.getWidth()/4, mainScreen.getHeight()/2);
			graphics.drawString("By Andrew Tacoi", mainScreen.getWidth()/4, mainScreen.getHeight()/2+50);
			graphics.drawString("Press Enter to Start", mainScreen.getWidth()/4, mainScreen.getHeight()/2+100);
			
		}
	}

	@Override
	public void initializeScreen() 
	{
	    try {
            mainScreen.getMusicHandler().play("Rolling Down The Street, In My Katamari");
            
        } catch (MusicHandlerException e) {
            
            e.printStackTrace();
        }
		titleTime = 0;
		finishedLoading = false;
		logo.setCollider(new Rectangle(-logo.getCollider().width, mainScreen.getHeight()/2-logo.getCollider().height/2, 
										logo.getCollider().width, logo.getCollider().height));
	}
	
	@Override
    public void step() 
    {   
        if (titleTime <= TITLE_LOADING_TIME)    
        {
            Rectangle collider = logo.getCollider();
            logo.setCollider(new Rectangle(collider.x + 1 * speed, collider.y, collider.width, collider.height));
        }
        else
            finishedLoading = true;
        
        repaint();
        titleTime++;
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (KeyEvent.VK_ENTER == e.getKeyCode() && finishedLoading)
        {
            try {
                mainScreen.getMusicHandler().stop();
            } catch (MusicHandlerException e1) {
                e1.printStackTrace();
            }
            
            mainScreen.switchScreen("options screen");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
