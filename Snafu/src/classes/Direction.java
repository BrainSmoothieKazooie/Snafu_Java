package classes;

/*
 * Direction.java
 * 
 * An enum that represents all possible directions that a Snake may travel
 * by giving its direction name values an integer array.
 * 
 * There are only eight possible directions a Snake may move:
 * 
 * North, South, East, West, NorthEast, NorthWest, 
 * SouthWest, and SouthEast. 
 *
 * Author: Andrew Tacoi
 */

public enum Direction 
{
	    NORTH(new int[] {0, -1}),
	    SOUTH(new int[] {0, 1}),
	    EAST(new int[] {1, 0}),
	    WEST(new int[] {-1, 0}),
	    NORTHEAST(new int[] {1, -1}),
	    NORTHWEST(new int[] {-1, -1}),
	    SOUTHWEST(new int[] {-1, 1}),
	    SOUTHEAST(new int[] {1, 1});
		
	    public final int[] directionValues;

	    private Direction(int[] directionValues) 
	    {
	        this.directionValues = directionValues;
	    }
	    
	    public static Direction getDirection(int[] values)
	    {
	        for (Direction direction : Direction.values())
	        {
	            if (values[0] == direction.directionValues[0] && values[1] == direction.directionValues[1])
	                return direction;
	        }
	        return null;
	    }
	    
	    public String toString()
	    {
	        return getDirection(directionValues).name();
	    }
}
