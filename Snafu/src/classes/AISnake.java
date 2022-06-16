package classes;

import java.awt.*;
import java.util.ArrayList;

import mainGame.GameScreen;

/*
 * AISnake.java
 * 
 * Represents an ai snake that has all of the attributes of a Snake object.
 * 
 * How the ai works...
 * 
 * Before moving, the ai must do the following:
 * 
 * The ai will first determine the next position it 
 * will take by using the getNextPos() method. 
 * 
 * Then it will change its direction based on 
 * following rules:
 * 
 * -It will hit the edges of the screen
 * -It will hit a position already taken
 * up on the screen, such as its own body,
 * another snake's body, or an obstacle. 
 * 
 * When changing its direction, the Snake will look at every point
 * around it and when it finds an empty position,
 * it will change its direction so that its next move will land on that
 * empty position. 
 * 
 * Directions are randomized in order to allow more variation in movement. 
 * 
 * Author: Andrew Tacoi
 */

public class AISnake extends Snake 
{	
    // *********************  Fields  *********************
    
	private GameScreen gameScreen;
	
	// *********************  Constructors  *********************
	
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

	public AISnake(GameScreen game, Color color, int pixelSize)
	{
		super(color, pixelSize);
		
		gameScreen = game;
		positions = game.getPositions();
		Point pos = new Point(0, 0);
		collider = new Rectangle(pos.x, pos.y, pixelSize, pixelSize);
        setRandomDirection();
	}
	
	public AISnake(int x, int y, GameScreen game, Color color, int pixelSize, Direction direct)
	{
		super(x, y, color, pixelSize);
		
		gameScreen = game;
		positions = game.getPositions();
		direction = direct;
	}      
   
   // *********************  Public Methods  *********************
	
	@Override
	public void move(int dx, int dy) // Moves the AISnake, then determines if it should change its direction.
	{  
	    super.move(dx, dy);
	   
        determineDirection();
	}
	
	 // *********************  Private Methods  *********************
    
    private void setRandomDirection() // 1/8 chance of any of the eight possible directions.
    {
        double random = Math.random();
        
        if (random < .125)
            direction = Direction.NORTH;
        else if (random < .25)
            direction = Direction.SOUTH;
        else if (random < .375)
            direction = Direction.EAST;
        else if (random < .5)
            direction = Direction.WEST;
        else if (random < .625)
            direction = Direction.NORTHEAST;
        else if (random < .75)
            direction = Direction.NORTHWEST;
        else if (random < .875)
            direction = Direction.SOUTHEAST;
        else
            direction = Direction.SOUTHWEST;
    }
    public Point getNextPos() 
    {
    	int[] arr = direction.directionValues;
    
    	// same way a snake is moved in the move() method.
    	return new Point(collider.x + (collider.width * arr[0]), collider.y + (collider.height * arr[1])); 
    }
    
    private void determineDirection()
    {
        Point nextPosition = getNextPos();
      
        Rectangle col = new Rectangle (nextPosition.x, nextPosition.y, collider.width, collider.height);
      
        // will the snake be out of bounds or touch another object when it moves?  
        if (gameScreen.isOutOfBounds(col) || gameScreen.hasCollided(col))
        {
            changeDirection();
        }
    }

    // Changes its direction when it finds a point not already occupied.
    private void changeDirection() 
    {
        Point nextPosition = getNextPos();
        
        int pixelSize = GameScreen.PIXEL_SIZE;
        
        int countOutter = 0;
        
        ArrayList<Direction> directions = new ArrayList<>();
        
        for (int y = collider.y - pixelSize; countOutter < 3; y += pixelSize)
        {
            int count = 0;
            for (int x = collider.x - pixelSize; count < 3; x += pixelSize)
            {
                Point nextPoint = new Point(x, y);
                
                // skips the snakes current position (collider) and the position it will take next.
                if (!nextPoint.equals(nextPosition) && !nextPoint.equals(new Point(collider.x, collider.y))) 
                {
                    Rectangle nextPointRect = new Rectangle(nextPoint.x, nextPoint.y, collider.width, collider.height);
                    
                    if (!gameScreen.hasCollided(nextPoint) && !gameScreen.isOutOfBounds(nextPointRect)) // Adds only the directions that will not result in the snake dying.
                        directions.add(Direction.getDirection(new int[] {(nextPoint.x-collider.x)/pixelSize, (nextPoint.y-collider.y)/pixelSize}));
                    
                }
                count++;
            } 
            countOutter++;
        }
        
        setRandomDirection(directions);
    }

    // Sets the AISnake to a random Direction by getting a random index that ranges from (0, directions.size()-1)
    private void setRandomDirection(ArrayList<Direction> directions) 
    {
        int size = directions.size();
        
        if (size == 0)
            return;
        
        int random = (int)(Math.random()*size);
       
        direction = directions.get(random);
        
        
    }
}