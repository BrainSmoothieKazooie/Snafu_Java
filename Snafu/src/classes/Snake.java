package classes;

import java.awt.*;
import java.util.*;

public abstract class Snake 
{
  protected boolean isVisible = true;
  private int health;
  protected Direction direction;
  protected int speed = 5;
  protected Rectangle collider = new Rectangle(10, 10);
  protected HashMap<Point, Rectangle> pastPositions;
  
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
	  for (int i = 0; i < speed; i++)
	  {	
		  int x = (int) (dx * speed);
		  int y = (int) (dy * speed);
		  pastPositions.put(new Point(collider.x+x, collider.y+y), 
			  			new Rectangle(collider.x+x, collider.y+y, collider.width, collider.height));
	  }
	  
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

  public boolean hasCollidedWithTail()
  {
	  Point point = new Point(collider.x, collider.y);
	    if (pastPositions.containsKey(point))
	    {
	    	Rectangle bounds = pastPositions.get(point);
	    	
		    if (bounds.x + bounds.width >= collider.x && bounds.x <= collider.x + collider.width)
		    {
		        return true;
		    }
		    if (bounds.y + bounds.height >= collider.y && bounds.y <= collider.y + collider.height) 
	    	{
	    		return true;
	    	}
	    }
	    return false;
  }

  public boolean hasCollided(Snake snake)
  {
	  
	  Point point = new Point(collider.x, collider.y);
	    if (snake.pastPositions.containsKey(point))
	    {
	    	Rectangle bounds = snake.pastPositions.get(point);
	    	
		    if (bounds.x + bounds.width >= collider.x && bounds.x <= collider.x + collider.width)
		    {
		        return true;
		    }
		    if (bounds.y + bounds.height >= collider.y && bounds.y <= collider.y + collider.height) 
	    	{
	    		return true;
	    	}
	    }
	    return false;	
  }
}
