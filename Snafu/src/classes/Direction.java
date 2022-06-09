package classes;

public enum Direction 
{
	    NORTH(new int[] {0, -1}),
	    SOUTH(new int[] {0, 1}),
	    EAST(new int[] {1, 0}),
	    WEST(new int[] {-1, 0});
		
	    public final int[] directionValues;

	    private Direction(int[] directionValues) 
	    {
	        this.directionValues = directionValues;
	    }
}
