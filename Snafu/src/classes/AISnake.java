package classes;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import mainGame.*;

import javax.swing.JPanel;

public class AISnake extends Snake //Intercepting
{	
	private GameScreen gameScreen;
	
	public AISnake()
	{
        collider.x = 100;
        collider.y = 100;
	}
	
	public AISnake(int x, int y, GameScreen game, Color color, int pixelSize)
	{
		super(x, y, color, pixelSize);
		gameScreen = game;
		positions = game.getPositions();
        setRandomDirection();
	}
	
	public AISnake(int x, int y, GameScreen game, Color color, int pixelSize, Direction direct)
	{
		super(x, y, color, pixelSize);
		gameScreen = game;
		positions = game.getPositions();
		direction = direct;
	}
	
	private void setRandomDirection()
	{
		double random = Math.random();
		
		if (random < .25)
			direction = Direction.NORTH;
		else if (random < .50)
			direction = Direction.SOUTH;
		else if (random < .75)
			direction = Direction.EAST;
		else
			direction = Direction.WEST;
	}
	

	@Override
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
	  	if (!points.contains(new Point(collider.x, collider.y)))
		  	  points.add(new Point(collider.x, collider.y));
		  positions.put(new Point(collider.x, collider.y),
					  	new Rectangle(collider.x, collider.y, collider.width, collider.height));
	  	  temp--;
	  }
		determineDirection();
	}
	
	public void changeDirection()
	{
		double random = Math.random();

		if (direction.equals(Direction.NORTH))
		{
			if (random < .4)
				direction = Direction.EAST;
			else
				direction = Direction.WEST;
		}
		
		else if (direction.equals(Direction.SOUTH))
		{
			if (random > .7)
				direction = Direction.EAST;
			else
				direction = Direction.WEST;
		}
		
		else if (direction.equals(Direction.EAST))
		{
			if (random <= .45)
				direction = Direction.NORTH;
			else
				direction = Direction.SOUTH;
		}
		
		else if (direction.equals(Direction.WEST))
		{
			if (random < .4 || random >= .9)
				direction = Direction.NORTH;
			else
				direction = Direction.SOUTH;
		}
	}

  public Point getNextPos()
  {
	  int[] arr = direction.directionValues;

	  return new Point(collider.x + (collider.width * arr[0]), collider.y + (collider.height * arr[1]));
  }

  public void determineDirection()
  {
	  Point nextPosition = getNextPos();
	  
	  Rectangle col = new Rectangle (nextPosition.x, nextPosition.y, collider.width, collider.height);
	  
	  if (gameScreen.isOutOfBounds(col) || 
			  collide(col))
	  {
		  changeDirection();
	  }
	  
	  nextPosition = getNextPos();
	  col.x = nextPosition.x;
	  col.y = nextPosition.y;
	  
	  if (gameScreen.isOutOfBounds(col) || 
		 collide(col))
	  {
		  flip();
	  }
   }
  
  public boolean collide(Rectangle rect)
  {
	  return positions.containsKey(new Point(rect.x, rect.y));
  }

public void flip()
  {
    switch (direction.name())
    {
      case "NORTH":
        direction = Direction.SOUTH;
        break;
      case "SOUTH":
        direction = Direction.NORTH;
        break;
      case "WEST":
        direction = Direction.EAST;
        break;
      case "EAST":
        direction = Direction.WEST;
        break;
    }
  }
}