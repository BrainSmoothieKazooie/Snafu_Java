package classes;

import java.awt.event.*;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class PlayerSnake extends Snake implements KeyListener
{
    public PlayerSnake(int x, int y)
    {
        collider.x = x;
        collider.y = y;
        direction = Direction.SOUTH;
    }
    
    public PlayerSnake(int x, int y, HashMap<Point, Rectangle> positions, Color color, int pixelSize)
    {
    	super(x, y, positions, color, pixelSize);
    }
  	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) && direction != Direction.SOUTH)
			direction = Direction.NORTH;
      		
		else if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) && direction != Direction.NORTH)
			direction = Direction.SOUTH;
      		
		else if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) && direction != Direction.WEST)
			direction = Direction.EAST;
      		
		else if ((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) && direction != Direction.EAST)
			direction = Direction.WEST;
	}
  	
	@Override
	public void keyReleased(KeyEvent e) 
	{}

    @Override
	public void keyTyped(KeyEvent e) 
	{}
}