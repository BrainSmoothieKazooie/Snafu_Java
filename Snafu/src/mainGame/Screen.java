package mainGame;

import javax.swing.*;

import classes.MusicHandler;
import classes.Sprite;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class Screen extends JPanel implements ActionListener, KeyListener
{
	private Window window;
	private MusicHandler musicHandler;
	private Container cPane;   
	private ArrayList<Rectangle> points;
	private final int PIXEL_SIZE = 20;
	
	public Screen(Window window)
	{
		this.window = window;

		points = new ArrayList<>();
		for (int i = 0; i <= window.getWidth(); i += PIXEL_SIZE)
		{
			for (int j = 0; j <= window.getHeight(); j += PIXEL_SIZE)
			{
				points.add(new Rectangle(j, i, PIXEL_SIZE, PIXEL_SIZE));
			}
		}
		System.out.println(points);
		
		cPane = window.getContentPane();    
		cPane.setLayout(new CardLayout());
		
		ArrayList<File> musicPaths = loadAllMusic();
		musicHandler = new MusicHandler(musicPaths);

//		 //Adding all screens in the game
//		TitleScreen titleScreen = new TitleScreen(window);
//		titleScreen.setName("title screen");
//		
//		OptionsScreen optionsScreen = new OptionsScreen(this);
//		optionsScreen.setName("options screen");
		
//		MainScreen mainScreen = new MainScreen();
//		mainScreen.setName("main screen");

//		cPane.add(titleScreen, titleScreen.getName());
//		cPane.add(optionsScreen, optionsScreen.getName());
//		cPane.add(mainScreen, mainScreen.getName());
//		setAsActive(titleScreen.getName());
		
		setVisible(true);
		
		setFocusable(true);
		addKeyListener(this);
		setBackground(Color.black);
		
		Timer timer = new Timer(1000/60, this);
		timer.start();
	}
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
//			if (once )
//			{
//				getMusicHandler().play("Comfort Zone");
//				once = !once;
//			}
//			update();
//			checkCollisions();
//			clean();
			repaint();
	}
	
	protected void Input(KeyEvent e)
	{
		System.out.println(e.getKeyChar());
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
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D)g;
//		
//		for (Snake snake : snakes)
//		{
//			if (snake.isVisible())
//				snake.draw(graphics);
//		}
		
		for (Rectangle point : points)
		{
			graphics.setColor(Color.white);
			graphics.fillRect(point.x, point.y, point.width, point.height);
			graphics.setColor(Color.black);
			graphics.drawRect(point.x, point.y, point.width, point.height);
		}
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
	
	public MusicHandler getMusicHandler()
	{
		return musicHandler;
	}
}
