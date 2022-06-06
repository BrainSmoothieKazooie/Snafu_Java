package mainGame;

import javax.swing.*;
import classes.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MainScreen extends JPanel implements ActionListener
{
	private ArrayList<Snake> snakes; 
	protected Window window;
	private int numberOfSnakesAlive;
	private boolean once = true;
	
	public MainScreen(Window window)
	{
		this.window = window;
		snakes = new ArrayList<>();
		
		setFocusable(true);
		setBackground(Color.black);
		setIgnoreRepaint(true);
		this.setSize(window.getWidth(), window.getHeight());
		
		addSprites();
		numberOfSnakesAlive = snakes.size();
		
		Timer timer = new Timer(1000/60, this);
		timer.start();
	}
	
	private void addSprites() 
	{
		//snakes.add(new PlayerSnake());
		//snakes.add(new AISnake(getWidth()/4, getHeight()/4));
		//snakes.add(new AISnake(getWidth()/2, getHeight()/2));
		snakes.add(new AISnake(getWidth()/3, getHeight()/3));
		
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
		if (isEnabled())
		{
			if (once )
			{
				window.getMusicHandler().play("Comfort Zone");
				once = !once;
			}
			update();
			checkCollisions();
			clean();
			repaint();
		}
	}

	private void clean() // Removes all Snakes that are not active.
	{
		for (int i = snakes.size()-1; i >= 0; i--)
		{
			if (!snakes.get(i).isVisible())
			{
				snakes.remove(i);
				numberOfSnakesAlive--;
			}
		}
	}

	private void update() 
	{
		for (int i = 0; i < snakes.size(); i++)
		{
			Snake snake = snakes.get(i);
			if (snake instanceof AISnake)
			{
				AISnake ai = (AISnake)snake;
				ai.determineDirection(snakes, i, this);
			}
			
			float[] arr = snake.getDirection().directionValues;
			snake.move(arr[0], arr[1]);
		}
		
	}
	
	private void checkCollisions() 
	{
    for (int i = 0; i < snakes.size(); i++)
    {
      Snake snake = snakes.get(i);
        if (isOutOfBounds(snake) || snake.hasCollidedWithTail())
          snake.setVisibilty(false);
          for (int j = 0; j < snakes.size(); j++)
            {
              if (j != i)
              {
                if (snake.hasCollided(snakes.get(j)))
                {
                   snake.setVisibilty(false);
                }
              }
            }
        }
	}
	
	public boolean isOutOfBounds(Snake snake)
	{
		Rectangle collider = snake.getCollider();
		return (collider.x < 0 || collider.x > getWidth()-collider.width || 
				    collider.y < 0 || collider.y > getHeight()-collider.height);
	}
	
	public boolean isOutOfBounds(Rectangle rect)
	{
		return (rect.x < 0 || rect.x > getWidth()-rect.width || 
				rect.y < 0 || rect.y > getHeight()-rect.height);
	}
}