package mainGame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import classes.*;

import javax.swing.*;
import javax.swing.Timer;

public class GameScreen extends JPanel implements ActionListener
{
	private HashMap<Point, Rectangle> positions; 
	private final int PIXEL_SIZE = 10;
	private ArrayList<Snake> snakes;
	private int numberOfSnakesAlive;

	public GameScreen()
	{
		setPreferredSize(new Dimension(1000, 1000));
		setSize(1000, 1000);
		setFocusable(true);
		setBackground(Color.black);
		
		
		positions = new HashMap<>();
		
		addSnakes();
		
		Timer timer = new Timer(1000/30, this);
		timer.start();
	}
	
	private void addSnakes()
	{
		snakes = new ArrayList<>();
		snakes.add(new PlayerSnake(0, 0, positions, Color.white, PIXEL_SIZE));
		snakes.add(new AISnake(500, 500, this, Color.GREEN, PIXEL_SIZE, Direction.WEST));
		snakes.add(new AISnake(700, 700, this, Color.yellow, PIXEL_SIZE, Direction.NORTH));
		snakes.add(new AISnake(800, 0, this, Color.CYAN, PIXEL_SIZE, Direction.WEST));
		
		for (int i = 0; i < snakes.size(); i++)
		{
			if (snakes.get(i) instanceof PlayerSnake)
				addKeyListener((PlayerSnake) snakes.get(i));
		}
		numberOfSnakesAlive = snakes.size();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (numberOfSnakesAlive > 1)
		{
			clean();
			update();
			checkCollisions();
		}
		repaint();
	}
	
	private void clean()
	{
		for (int i = snakes.size()-1; i >= 0; i--)
		{
			if (!snakes.get(i).isVisible())
			{
				snakes.get(i).removeAllPositions();
				snakes.remove(i);
				numberOfSnakesAlive--;
			}
		}
	}
	
	private void checkCollisions()
	{
		for (int i = 0; i < snakes.size(); i++)
		{
			Snake snake = snakes.get(i);
			
			if (snake.hasCollided(snake) || isOutOfBounds(snake))
			{
				snake.setVisible(false);
			}
			
			for (int j = 0; j < snakes.size(); j++)
			{
				if (j != i)
				{
					Snake otherSnake = snakes.get(j);
					if (snake.hasCollided(otherSnake.getPastPositions()))
						snake.setVisible(false);
				}
			}
		}
	}
	
	public boolean isOutOfBounds(Rectangle rect)
	{
		return (rect.x < 0 || rect.x > getWidth()-rect.width || 
				rect.y < 0 || rect.y > getHeight()-rect.height);
	}
	
	public boolean isOutOfBounds(Snake snake)
	{
		Rectangle collider = snake.getCollider();
		return (collider.x < 0 || collider.x > getWidth()-collider.width || 
				    collider.y < 0 || collider.y > getHeight()-collider.height);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (numberOfSnakesAlive > 1)
		{
			for (Snake snake : snakes) 
		    {
		    	snake.draw((Graphics2D)g);
		    }
		}
		else
		{
			Graphics2D graphics = (Graphics2D)g;
			graphics.setColor(Color.white);
			graphics.setFont(new Font("SansSerif", Font.PLAIN, 40));
			graphics.drawString("Game Over", getWidth()/2, getHeight()/2);
		}
	}
	
	private void update() 
	{
		for (int i = 0; i < snakes.size(); i++)
		{
			Snake snake = snakes.get(i);
			
			int[] arr = snake.getDirection().directionValues;
			snake.move(arr[0], arr[1]);
		}	
	 }
	
	public HashMap<Point, Rectangle> getPositions()
	{ return positions; }
}
