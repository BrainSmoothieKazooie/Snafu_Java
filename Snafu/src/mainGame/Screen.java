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
		this.setSize(window.getWidth(), window.getHeight());
		
		addSprites();
		
		Timer timer = new Timer(1000/60, this);
		timer.start();
	}
	
	private void addSprites() 
	{
		snakes.add(new PlayerSnake());
		snakes.add(new AISnake());
		
		for (Snake snake : snakes)
		{
			if (snake instanceof PlayerSnake)
			{
				addKeyListener((PlayerSnake)snake);
			}
		}
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
		checkCollisions();
		update();
		repaint();
	}

	private void update() 
	{
		for (Snake snake : snakes)
		{
			float[] arr = snake.getDirection().directionValues;
			snake.move(arr[0], arr[1]);
		}
		
	}
	
	private void checkCollisions() 
	{
		outOfBounds();
		for (int i = 0; i < snakes.size()-1; i++)
		{
			for (int j = i+1; j < snakes.size(); j++)
			{
	    	  	Snake snake = snakes.get(i);
	  			if (snake.getCollider().x >= snakes.get(j).getCollider().x && snake.getCollider().x <= snakes.get(j).getCollider().x+snakes.get(j).getCollider().width
	  				&& snake.getCollider().y >= snakes.get(j).getCollider().y && snake.getCollider().y <= snakes.get(j).getCollider().y+snakes.get(j).getCollider().height)
	  			{
	  				if (snake instanceof AISnake)
	  				{
	  					AISnake ai = (AISnake)snake;
	  					ai.changeDirection();
	  				}
	  				snake.setHealth(snake.getHealth() - 1);
	  			}
	  			if (snake.getHealth() <= 0)
	  				snakes.remove(i);
			}
		}
	}
	
	private void outOfBounds()
	{
		for (Snake snake : snakes)
		{
			Rectangle collider = snake.getCollider();
			if (collider.x <= 0 || collider.x >= getWidth()-collider.width || 
				collider.y <= 0 || collider.y >= getHeight()-collider.height)
			{
				if (snake instanceof AISnake)
  				{
  					AISnake ai = (AISnake)snake;
  					ai.changeDirection();
  				}
				else
					snake.setHealth(snake.getHealth() - 1);
			}
		}
	}
}