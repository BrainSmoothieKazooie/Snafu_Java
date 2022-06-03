package classes;

import java.awt.Image;

import classes.Snake.Direction;

import java.awt.Graphics2D;

public abstract class Snake extends Sprite
{
  private int tailLength;
  private int health;
  protected Direction direction;
  protected double speed = 5;
  
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
    tailLength = 0;
    health = 1;
    direction = Direction.SOUTH;
  }

  public Snake(int tailLength)
  {
    this.tailLength = tailLength;
    health = 1;
  }
  
  public Snake(int tailLength, Image img)
  {
    this.tailLength = tailLength;
    health = 1;
    setImage(img);
  }

  public Snake(Image img)
  {
    this.tailLength = 0;
    health = 1;
    setImage(img);
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
	  collider.x += dx * speed;
  	  collider.y += dy * speed;
  }
}
