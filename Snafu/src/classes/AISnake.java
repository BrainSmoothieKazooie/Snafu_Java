package classes;

import java.awt.*;
import java.util.ArrayList;

import mainGame.MainScreen;

public class AISnake extends Snake //Intercepting
{
	private Point previousPosition;
	
	public AISnake()
	{
        collider.x = 100;
        collider.y = 100;
        previousPosition = new Point(collider.x, collider.y);
	}
	
	public AISnake(int x, int y)
	{
        collider.x = x;
        collider.y = y;
        previousPosition = new Point(collider.x, collider.y);
        setRandomDirection();
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
	public void move(float dx, float dy)
	{
		previousPosition = new Point(collider.x, collider.y); 
		pastPositions.put(new Point(collider.x, collider.y), 
	  			new Rectangle(collider.x, collider.y, collider.width, collider.height));
		collider.x += dx * speed;
		collider.y += dy * speed;
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

  public Rectangle getNextPos(boolean isCheckingWall)
  {
    float[] arr = direction.directionValues;
    int x = (int)(collider.x + (arr[0]*speed));
    int y = (int)(collider.y + (arr[1]*speed));
    
    if (!isCheckingWall)
    {
	    switch(direction.name())
	    {
	      case "NORTH":
	        y -= collider.height;
	        break;
	      case "SOUTH":
	        y += collider.height;
	        break;
	      case "WEST":
	        x -= collider.width;
	        break;
	      case "EAST":
	        x += collider.width;
	        break; 
	    }
    }
    
    return new Rectangle(x, y, collider.width, collider.height);
  }

  public void determineDirection(ArrayList<Snake> snakes, int indexOfAI, MainScreen window)
  {
	  Rectangle nextPos = getNextPos(false);
	  Rectangle initalPos = getNextPos(true);
	  
      for (int j = 0; j < snakes.size(); j++)
        {
          if (j != indexOfAI)
          {
            if (hasCollided(snakes.get(j), nextPos))
            {
               changeDirection();
            }

            nextPos = getNextPos(false);
            if (hasCollided(snakes.get(j), nextPos))
            {
               flip();
            }
          }
        }
    
      	if (snakes.get(indexOfAI).hasCollidedWithTail())
      	{
      		changeDirection();
            initalPos = getNextPos(false);
      	}
      	
      	if (snakes.get(indexOfAI).hasCollidedWithTail())
        	flip();
      	
        if (window.isOutOfBounds(initalPos))
        {
          changeDirection();
          initalPos = getNextPos(true);
        }

        if (window.isOutOfBounds(initalPos) || snakes.get(indexOfAI).hasCollidedWithTail())
        	flip();
   }
  
  private boolean hasCollided(Snake snake, Rectangle rect)
  {
	  Point point = new Point(rect.x, rect.y);
	    if (snake.pastPositions.containsKey(point))
	    {
	    	Rectangle bounds = snake.pastPositions.get(point);
	    	
		    if (bounds.x + bounds.width >= rect.x && bounds.x <= rect.x + rect.width)
		    {
		        return true;
		    }
		    if (bounds.y + bounds.height >= rect.y && bounds.y <= rect.y + rect.height) 
	    	{
	    		return true;
	    	}
	    }
	    return false;
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