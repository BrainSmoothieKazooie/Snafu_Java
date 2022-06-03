package classes;

import javax.swing.ImageIcon;

public class AISnake extends Snake //Intercepting
{
	
	public AISnake()
	{
		ImageIcon temp = new ImageIcon("src/Sprites/Snake/Snake.png");
        setImage(temp.getImage());
        resize(100, 100);
        collider.x = 500;
        collider.y = 500;
	}
	
	public void changeDirection()
	{
		double random = Math.random();
		float[] arr = getDirection().directionValues;
		move(-arr[0], -arr[1]);
		
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
}
