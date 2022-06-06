package classes;

import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import classes.Snake.Direction;
import mainGame.MainScreen;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class PlayerSnake extends Snake implements KeyListener
{
    public PlayerSnake(int x, int y, MainScreen main)
    {
    	super(main);
        collider.x = x;
        collider.y = y;
        direction = Direction.SOUTH;
    }
    
    public void draw(Graphics2D graphics)
    {
      super.draw(graphics);
    }
      
    @Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
    	
	}
    
    public void move(float dx, float dy)
    {

  		pastPositions.put(new Point(collider.x, collider.y), 
  	  			new Rectangle(collider.x, collider.y, collider.width, collider.height));
  		mainScreen.getAllPositions().put(new Point(collider.x, collider.y), 
  	  			new Rectangle(collider.x, collider.y, collider.width, collider.height));
  	  
  	  collider.x += dx * speed;
    	  collider.y += dy * speed;
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
	{
		// TODO Auto-generated method stub
    		
	}
}