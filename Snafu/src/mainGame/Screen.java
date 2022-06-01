package mainGame;

import javax.swing.*;

import classes.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Screen extends JPanel implements ActionListener
{
	private ArrayList<Snake> snakes; 
	private MusicHandler backgroundMusicHandler;
	protected JFrame window;
	
	public Screen(JFrame window)
	{
		this.window = window;
		snakes = new ArrayList<>();
		backgroundMusicHandler = new MusicHandler(new File("src/Sounds/Music/Rolling Down The Street, In My Katamari.wav"));
		
		backgroundMusicHandler.play("Rolling Down The Street, In My Katamari", true);
		
		setFocusable(true);
		setBackground(Color.black);
		
		addSprites();
		
		Timer timer = new Timer(100/60, this);
		timer.start();
	}
	
	private void addSprites() 
	{
		snakes.add(new PlayerSnake());
	}

	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D)g;
		
		for (Snake snake : snakes)
		{
			if (snake.isVisible())
				snake.draw(graphics);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		update();
    checkCollisions();
		repaint();
	}

	private void update() 
	{
		int count = 0;
		for (Snake snake : snakes)
		{
			if (snake.isVisible())
			{
				snake.move();
			}
		}
		
	}
	
	private void checkCollisions() 
	{
		for (int i = snakes.size()-1; i >= 0; i--)
		{
      for (int j = i-1; j >= 0; j--)
      {
        Snake snake = snakes.get(j);
  			if (snake.getCollider().intersects(snakes.get(i).getCollider()))
  				snake.setHealth(snake.getHealth() - 1);
  			
  			if (snake.getHealth() <= 0)
  				snakes.remove(j);
      }
		}
	}
}