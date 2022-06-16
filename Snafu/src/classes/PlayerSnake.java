package classes;

import java.awt.event.*;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

/*
 * PlayerSnake.java
 * 
 * Represents a snake that a player
 * can control, and will change its direction
 * based upon the input pressed.
 * 
 * There must be only four inputs given to 
 * the PlayerSnake to represent its four different
 * directions: Up, Down, Right, and Left.
 *
 * Author: Andrew Tacoi
 */

public class PlayerSnake extends Snake
{
    // *********************  Fields  *********************
    
    private int[] playerKeys; // Up, Down, Right, Left keys
	private boolean[] keysPressed = new boolean[4]; // True if the key is currently pressed.
	
	// *********************  Constructors  *********************
	
    public PlayerSnake(int x, int y)
    {
        collider.x = x;
        collider.y = y;
        direction = Direction.SOUTH;
    }
    
    public PlayerSnake(int x, int y, HashMap<Point, Rectangle> positions, Color color, int pixelSize, int[] keys, Direction direct)
    {
    	super(x, y, positions, color, pixelSize);
    	playerKeys = keys;
    	direction = direct;
    	
    	if (playerKeys.length != 4)
    		throw new IllegalArgumentException("Player Must Have 4 Input Keys");
    }
    
    // *********************  Public Methods  *********************
  	
    /*
    * Used to determine what key has been pressed, or released (Up, Down, Right, Left keys) 
    * and then updates its direction through the updateDirection() method.
    */
    
	public void input(KeyEvent e, boolean isPressed) 
	{
		if ((e.getKeyCode() == playerKeys[0])) // Up
		{
            keysPressed[0] = isPressed;
		}
      		
		else if ((e.getKeyCode() == playerKeys[1])) // Down
		{
		    keysPressed[1] = isPressed;
		}
      		
		else if ((e.getKeyCode() == playerKeys[2])) // Right
		{
		    keysPressed[2] = isPressed;
		}
      		
		else if ((e.getKeyCode() == playerKeys[3])) // Left
		{
		    keysPressed[3] = isPressed;
		}
		
		updateDirection();
	}
	
	// *********************  Private Methods  *********************
	
	// Changes the snakes direction based on the input given. The Snake will not respond to a direction that would make it travel backwards. 
	private void updateDirection() 
	{
	    if (keysPressed[0] && keysPressed[3] && direction != Direction.SOUTHWEST) // Turn North West
	        direction = Direction.NORTHWEST;
	    
	    else if (keysPressed[0] && keysPressed[2] && direction != Direction.SOUTHEAST) // Turn North East
            direction = Direction.NORTHEAST;
	    
	    else if (keysPressed[1] && keysPressed[2] && direction != Direction.NORTHEAST) // Turn South East
            direction = Direction.SOUTHEAST;
	    
	    else if (keysPressed[1] && keysPressed[3] && direction != Direction.NORTHWEST) // Turn South West
            direction = Direction.SOUTHWEST;
	    
	    else if (keysPressed[0] && direction != Direction.SOUTH) // Turn North 
            direction = Direction.NORTH;
	    
	    else if (keysPressed[1] && direction != Direction.NORTH) // Turn South 
            direction = Direction.SOUTH;
	    
	    else if (keysPressed[2] && direction != Direction.WEST) // Turn East 
            direction = Direction.EAST;
	    
	    else if (keysPressed[3] && direction != Direction.EAST) // Turn West 
            direction = Direction.WEST;
	}
}