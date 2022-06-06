package classes;

import java.awt.*;
import mainGame.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import mainGame.MainScreen;

public class AISnake extends Snake //Intercepting
{	
	public AISnake()
	{
        collider.x = 100;
        collider.y = 100;
	}
	
	public AISnake(int x, int y, MainScreen main)
	{
		super(main);
        collider.x = x;
        collider.y = y;
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
		pastPositions.put(new Point(collider.x, collider.y), 
	  			new Rectangle(collider.x, collider.y, collider.width, collider.height));
		mainScreen.getAllPositions().put(new Point(collider.x, collider.y), 
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

  public Rectangle getNextPos(boolean bool)
  {
    float[] arr = direction.directionValues;
    int x = (int)(collider.x + (arr[0]*speed));
    int y = (int)(collider.y + (arr[1]*speed));
    

	
    if (bool)
    {
		switch (direction.name())
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
	  if (window.isOutOfBounds(getNextPos(false)) || window.hasCollided(getNextPos(true)) || hasCollided(snakes, indexOfAI))
	  {
		  changeDirection();
	  }
	  
	  if (window.isOutOfBounds(getNextPos(false)) || window.hasCollided(getNextPos(true))|| hasCollided(snakes, indexOfAI))
	  {
		  flip();
	  }
   }
  
  public boolean hasCollided(ArrayList<Snake> snakes, int index)
  {
	  for (int i = 0; i < snakes.size(); i++)
	  {
		  if (index != i)
		  {
			  Snake s = snakes.get(i);
			  
			  if (s instanceof AISnake)
			  {
				  AISnake ai = (AISnake)s;
				  Rectangle aiNextPos = ai.getNextPos(true);
				  
				  if (aiNextPos.x == getNextPos(true).x && aiNextPos.y == getNextPos(true).y)
				  {
					  return true;
				  }
			  }
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