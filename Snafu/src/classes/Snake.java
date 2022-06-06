package classes;

import java.awt.*;

import mainGame.*;
import java.util.*;

import javax.swing.JPanel;

public abstract class Snake 
{
  protected boolean isVisible = true;
  private int health;
  protected Direction direction;
  protected int speed = 1;
  protected Rectangle collider = new Rectangle(10, 10);
  protected HashMap<Point, Rectangle> pastPositions;
  protected MainScreen mainScreen;
  
  public enum Direction 
  {
	    NORTH(new float[] {0, -1}),
	    SOUTH(new float[] {0, 1}),
	    EAST(new float[] {1, 0}),
	    WEST(new float[] {-1, 0});
		
	    public final float[] directionValues;

	    private Direction(float[] directionValues) 
	    {
	        this.directionValues = directionValues;
	    }
  }
  
  public Snake()
  {
    pastPositions = new HashMap<>();
    health = 1;
    direction = Direction.SOUTH;
  }

  public Snake(int tailLength)
  {
    pastPositions = new HashMap<>();
    health = 1;
  }
  
  public Snake(MainScreen main)
  {
	  pastPositions = new HashMap<>();
	  mainScreen = main;
  }

  public int getHealth()
  { return health; }
  
  public void setHealth(int h)
  { health = h; }
 
  public Direction getDirection()
  { return direction; }
  
  public void setDirection(Direction dir)
  { direction = dir; }

  public void move(float dx, float dy)
  {

		pastPositions.put(new Point(collider.x, collider.y), 
	  			new Rectangle(collider.x, collider.y, collider.width, collider.height));
		mainScreen.getAllPositions().put(new Point(collider.x, collider.y), 
	  			new Rectangle(collider.x, collider.y, collider.width, collider.height));
	  
	  collider.x += dx * speed;
  	  collider.y += dy * speed;
  }

  public void draw(Graphics2D graphics)
    {
      for (Map.Entry<Point, Rectangle> position : pastPositions.entrySet()) 
      {
    	Rectangle rect = position.getValue();
    	  
        graphics.setColor(Color.blue);
        graphics.fillRect(rect.x, rect.y, rect.width, rect.height);
      }
      graphics.setColor(Color.blue);
        graphics.fillRect(collider.x, collider.y, collider.width, collider.height);
    }
    
    protected void drawCollider(Graphics2D graphics)
    {
    	graphics.setColor(new Color(1f,0f,0f,.5f));
		graphics.fillRect(collider.x, collider.y, collider.width, collider.height);
    }
  public Rectangle getCollider()
  { return collider; }
  
  public void setCollider(Rectangle rect)
  { collider = new Rectangle(rect.x, rect.y, rect.width, rect.height); }

  public boolean isVisible()
  { return isVisible; }
  
  public void setVisibilty(boolean isVisible)
  { this.isVisible = isVisible; }

  public HashMap<Point, Rectangle> getPastPositions()
  { return pastPositions; }

  public boolean hasCollided(Snake snake)
  {
	    return hasCollided(snake.getCollider());	
  }
  
  public boolean hasCollided(Rectangle rect)
  {
	  Point point = new Point(rect.x, rect.y);
	    if (pastPositions.containsKey(point))
	    {
	    	Rectangle bounds = pastPositions.get(point);
	    	
		    if (rect.x + rect.width >= bounds.x && rect.x <= bounds.x + bounds.width)
		    {
		        return true;
		    }
		    if (rect.y + rect.height >= bounds.y && rect.y <= bounds.y + bounds.height) 
	    	{
	    		return true;
	    	}
	    }
	    return false;	
  }

public void removeAllPositions() 
{
	for (Map.Entry<Point, Rectangle> position : pastPositions.entrySet()) 
    {
		mainScreen.getAllPositions().remove(position.getKey());
    }
}
}
