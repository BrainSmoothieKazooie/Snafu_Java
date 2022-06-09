package classes;

import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

public abstract class Snake 
{
  protected boolean isVisible = true;
  protected Direction direction;
  protected int speed;
  protected Rectangle collider;
  private Color snakeColor;
  protected HashMap<Point, Rectangle> positions;
  protected ArrayList<Point> points; 
  
  public Snake()
  {
    direction = Direction.EAST;
	snakeColor = Color.white;
  }
  
  public Snake(HashMap<Point, Rectangle> positions, Color color, int pixelSize)
  {
	  speed = 1;
	  direction = Direction.SOUTH;
	  points = new ArrayList<>();
	  collider = new Rectangle(pixelSize, pixelSize);
	  this.positions = positions;
	  snakeColor = color;
  }
  
  public Snake(int x, int y, HashMap<Point, Rectangle> positions, Color color, int pixelSize)
  {
	  speed = 1;
	  direction = Direction.SOUTH;
	  points = new ArrayList<>();
	  collider = new Rectangle(x, y, pixelSize, pixelSize);
	  this.positions = positions;
	  snakeColor = color;
  }
  
  public Snake(int x, int y, Color color, int pixelSize)
  {
	  speed = 1;
	  direction = Direction.SOUTH;
	  points = new ArrayList<>();
	  collider = new Rectangle(x, y, pixelSize, pixelSize);
	  snakeColor = color;
  }
 
  public Direction getDirection()
  { return direction; }
  
  public void setDirection(Direction dir)
  { direction = dir; }

  public void move(int dx, int dy)
  {
	  if (!points.contains(new Point(collider.x, collider.y)))
	  	  points.add(new Point(collider.x, collider.y));
	  positions.put(new Point(collider.x, collider.y),
				  	new Rectangle(collider.x, collider.y, collider.width, collider.height));
	  int temp = speed;
	  
	  while (temp > 0)
	  {
		  collider.x += dx * collider.width;
	  	  collider.y += dy * collider.height;
		  positions.put(new Point(collider.x, collider.y),
					  	new Rectangle(collider.x, collider.y, collider.width, collider.height));
		  if (!points.contains(new Point(collider.x, collider.y)))
		  	  points.add(new Point(collider.x, collider.y));
	  	  temp--;
	  }
  }

  public void draw(Graphics2D graphics)
    {
      graphics.setColor(snakeColor);
      for (int i = 0; i < points.size()-1; i++) 
      {
    	  Point point = points.get(i);
    	  graphics.fillRect(point.x, point.y, collider.width, collider.height);
      }
      Point headPosition = points.get(points.size()-1);
      graphics.fillRect(headPosition.x, headPosition.y, collider.width, collider.height);
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

  public ArrayList<Point> getPastPositions()
  { return points; }

  public boolean hasCollided(Snake snake)
  {
	    return hasCollided(snake.getCollider());	
  }
  
  public boolean hasCollided(Rectangle rect)
  {	
	  return positions.containsKey(new Point(rect.x, rect.y)) &&
			 points.size() > 0 && !points.get(points.size()-1).equals(new Point(rect.x, rect.y));
  }
  
  public boolean hasCollided(ArrayList<Point> p)
  {	
	  return p.contains(new Point(collider.x, collider.y));
  }

	public void removeAllPositions() 
	{
		for (Point point : points) 
	    {
			positions.remove(point);
	    }
	}
	
	public void setVisible(boolean visible)
	{ isVisible = visible; }
}

