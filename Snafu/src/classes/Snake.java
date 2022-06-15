package classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Snake in the game that stores
 * each position the snake itself has taken and
 * all other positions currently taken up on the screen.
 *
 * A Snake includes its color, the current direction
 * it is facing, its current position (represented by a
 * Rectangle), and all of its past positions.
 *
 * When a Snake is considered dead (dead conditions
 * are in the readme file), it is no longer visible
 * and is therefore not drawn. All of its past positions
 * will then be removed through the removeAllPositions()
 * method.
 *
 * Author: Andrew Tacoi
 */

public abstract class Snake
{
    // *********************  Fields  *********************
    
    protected Color snakeColor;
    protected boolean isAlive = true;
    protected Direction direction;
    protected Rectangle collider;
    protected HashMap<Point, Rectangle> positions;
    protected ArrayList<Point> points;
    
    // *********************  Constructors  *********************

    public Snake()
    {
        direction = Direction.EAST;
        snakeColor = Color.white;
    }

    public Snake(HashMap<Point, Rectangle> positions, Color color, int pixelSize)
    {
        direction = Direction.SOUTH;
        points = new ArrayList<>();
        collider = new Rectangle(pixelSize, pixelSize);
        this.positions = positions;
        snakeColor = color;
    }

    public Snake(Color color, int pixelSize)
    {
        direction = Direction.SOUTH;
        points = new ArrayList<>();
        collider = new Rectangle(pixelSize, pixelSize);
        snakeColor = color;
    }

    public Snake(int x, int y, HashMap<Point, Rectangle> positions, Color color, int pixelSize)
    {
        direction = Direction.SOUTH;
        points = new ArrayList<>();
        collider = new Rectangle(x, y, pixelSize, pixelSize);
        this.positions = positions;
        snakeColor = color;
    }

    public Snake(int x, int y, Color color, int pixelSize)
    {
        direction = Direction.SOUTH;
        points = new ArrayList<>();
        collider = new Rectangle(x, y, pixelSize, pixelSize);
        snakeColor = color;
    }

    public Snake(Point point, Color color, int pixelSize)
    {
        direction = Direction.SOUTH;
        points = new ArrayList<>();
        collider = new Rectangle(point.x, point.y, pixelSize, pixelSize);
        snakeColor = color;
    }
    
    // *********************  Public Methods  *********************

    public void move(int dx, int dy) // Moves the snake based on the directions value
    {
        if (!points.contains(new Point(collider.x, collider.y)))
            points.add(new Point(collider.x, collider.y));
        
        positions.put(new Point(collider.x, collider.y),
                      new Rectangle(collider.x, collider.y, collider.width, collider.height));
        
        collider.x += dx * collider.width;
        collider.y += dy * collider.height; 
    }

    public void draw(Graphics2D graphics) // Draws the Snakes body, eye and head
    {
        graphics.setColor(snakeColor);
        
        for (Point point : points) 
            graphics.fillRect(point.x, point.y, collider.width, collider.height);
        
        graphics.fillRect(collider.x, collider.y, collider.width, collider.height);

        int half = collider.width/2;

        Point middlePosition = new Point(collider.x + half, collider.y + half);

        graphics.setColor(snakeColor.darker());
        graphics.fillRect(middlePosition.x - half/2, middlePosition.y - half/2, collider.width/3, collider.height/3);
    }
    
    public boolean hasCollided(Snake snake)
    {
        return hasCollided(snake.getCollider());
    }

    public boolean hasCollided(Rectangle rect)
    {
        return positions.containsKey(new Point(rect.x, rect.y));
    }
    
    public void removeAllPositions()
    {
        for (Point point : points)
            positions.remove(point);
    }
    
    // *********************  Getter & Setter Methods  *********************
    
    public Direction getDirection ()
    { return direction; }

    public void setDirection (Direction dir)
    { direction = dir; }
    
    public Rectangle getCollider ()
    { return collider; }

    public void setCollider (Rectangle rect)
    { collider = new Rectangle(rect.x, rect.y, rect.width, rect.height); }

    public boolean isAlive ()
    { return isAlive; }

    public void setIsAlive (boolean isAlive)
    { this.isAlive = isAlive; }

    public ArrayList<Point> getPastPositions ()
    { return points; }
}